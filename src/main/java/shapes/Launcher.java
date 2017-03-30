package shapes;

import edu.stanford.nlp.process.WordShapeClassifier;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

  public static void main(String[] args) throws IOException {

    // System.out.println(WordShapeClassifier.wordShape("������ ��� ���� �����",
    // WordShapeClassifier.WORDSHAPECHRIS2USELC));
    // System.out.println(WordShapeClassifier.wordShape("������ ��� ���� �����",
    // WordShapeClassifier.WORDSHAPECHRIS2));
    // System.out.println(WordShapeClassifier.wordShapeChris4("������ ��� ���� �����"));
    // System.out.println(WordShapeClassifier.wordShape("������ ��� ���� �����",
    // WordShapeClassifier.WORDSHAPEJENNY1)); // ����� ������

    File folder = new File("C://Users//Ivan//workspace//dialogue-21//factRuEval-2016//testset");
    File[] files = folder.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(".objects");
      }
    });

    for (File file : files) {
      processingFile(file);
      System.out.println("Processing file " + file.getName() + " is complete");
    }
  }

  private static void processingFile(File file) throws IOException {
    List<String> list = ProcessingFile.readFile(file);
    List<String> res = processingList(list);
    ProcessingFile.writeToEndFile("res_shape_jenny", res);
  }

  private static List<String> processingList(List<String> list) {
    List<String> res = new ArrayList<String>();
    for (String str : list) {
      String token = str.substring(str.indexOf("#") + 2);

      int startType = str.indexOf(" ") + 1;
      String type = str.substring(startType, str.indexOf(" ", startType));
      String shape = WordShapeClassifier.wordShape(token, WordShapeClassifier.WORDSHAPEJENNY1);
      res.add(
          shape.substring(shape.indexOf("WT-")+3) + "," + type);
          // token+","+WordShapeClassifier.wordShape(token, WordShapeClassifier.WORDSHAPECHRIS2)+","+WordShapeClassifier.wordShape(token, WordShapeClassifier.WORDSHAPEJENNY1) + "," + type);
    }
    return res;
  }

}
