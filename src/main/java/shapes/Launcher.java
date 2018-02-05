package shapes;

import edu.stanford.nlp.process.WordShapeClassifier;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Launcher {

	private final static Logger logger = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) throws IOException {
		//String log4j = Launcher.class.getClassLoader().getResource("log4j.properties").getPath();
		// PropertyConfigurator.configure(log4j);

		File file = new File("C://Users//Ivan//eclipse-workspace//facr-extraction//project//listDialogNE.txt");
		processingFile(file);
		// logger.info("Processing file {} is complete", file.getName());
		// logger.debug("Processing file {} is complete", file.getName());
	}

	private static void processingFile(File file) throws IOException {
		List<String> list = ProcessingFile.readFile(file);
		Map<Pair<String, String>, Integer> map = processingList(list);
		map = sortByValue(map);
		ProcessingFile.writeToFile("res_shape_jenny.txt", map);
	}

	private static Map<Pair<String, String>, Integer> processingList(List<String> list) {
		Map<Pair<String, String>, Integer> map = new HashMap<Pair<String, String>, Integer>();
		for (String str : list) {
			String[] neAndType = str.split("\t\t\t");

			String shape = WordShapeClassifier.wordShape(neAndType[0], WordShapeClassifier.WORDSHAPEJENNY1);
			Pair<String, String> pair = new MutablePair<String, String>(shape.substring(shape.indexOf("WT-") + 3),
					neAndType[1]);
			if (map.containsKey(pair)) {
				map.replace(pair, map.get(pair) + 1);
			} else {
				map.put(pair, 1);
			}
			// token+","+WordShapeClassifier.wordShape(token,
			// WordShapeClassifier.WORDSHAPECHRIS2)+","+WordShapeClassifier.wordShape(token,
			// WordShapeClassifier.WORDSHAPEJENNY1) + "," + type);
		}
		return map;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

}
