package org.converter.demo;

import java.util.List;
import org.converter.demo.converter.DestinationFileFormat;
import org.converter.demo.splitter.SourceFileFormat;

public class ConversionManager {

  public static String convert(
      String fileName,
      SourceFileFormat sourceFileFormat,
      DestinationFileFormat destinationFileFormat)
      throws Exception {
    List<List<String>> splittedData = splitData(fileName, sourceFileFormat);
    return convertData(destinationFileFormat, splittedData);
  }

  private static String convertData(DestinationFileFormat destFileFormat, List<List<String>> data)
      throws Exception {
    return destFileFormat.getConverter().convert(data);
  }

  private static List<List<String>> splitData(String fileName, SourceFileFormat sourceFileFormat)
      throws Exception {
    return sourceFileFormat.getSplitter().split(fileName + "." + sourceFileFormat.getValue());
  }
}
