package shapes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessingFile {

	private final static Logger logger = LoggerFactory.getLogger(ProcessingFile.class);

	public static void writeToFile(String path, Map<Pair<String, String>, Integer> map) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), "utf-8"));
			for (Entry<Pair<String, String>, Integer> pair : map.entrySet()) {
				writer.write(pair.getKey().getLeft() + "\t" + pair.getKey().getRight() + "\t" + pair.getValue() + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public static List<String> readFile(File file) throws IOException {
		List<String> res = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				res.add(line);
			}

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return res;
	}

}
