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

public class Launcher {

	public static void main(String[] args) throws IOException {

		// System.out.println(WordShapeClassifier.wordShape("������
		// ��� ���� �����",
		// WordShapeClassifier.WORDSHAPECHRIS2USELC));
		// System.out.println(WordShapeClassifier.wordShape("������
		// ��� ���� �����",
		// WordShapeClassifier.WORDSHAPECHRIS2));
		// System.out.println(WordShapeClassifier.wordShapeChris4("������
		// ��� ���� �����"));
		// System.out.println(WordShapeClassifier.wordShape("������
		// ��� ���� �����",
		// WordShapeClassifier.WORDSHAPEJENNY1)); // ����� ������

		/*
		 * File folder = new
		 * File("C://Users//Ivan//workspace//dialogue-21//factRuEval-2016//testset");
		 * File[] files = folder.listFiles(new FilenameFilter() { public boolean
		 * accept(File dir, String name) { return
		 * name.toLowerCase().endsWith(".objects"); } });
		 * 
		 * for (File file : files) { processingFile(file);
		 * System.out.println("Processing file " + file.getName() + " is complete"); }
		 */

		File file = new File("C://Users//Ivan//eclipse-workspace//facr-extraction//project//listDialogNE.txt");
		processingFile(file);
		System.out.println("Processing file " + file.getName() + " is complete");
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
			Pair<String, String> pair = new MutablePair(shape.substring(shape.indexOf("WT-") + 3), neAndType[1]);
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
