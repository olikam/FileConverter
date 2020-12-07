package org.converter.demo.splitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvSplitter implements Splitter {

  // This regex is for splitting lines by comma excluding commas which are between double-quotes.
  private static final String REGEX_COLUMN_SPLITTER = "(?<!(\".)),(?!(.+\"))";

  @Override
  public List<List<String>> split(String fileName) throws IOException {
    List<List<String>> result = new ArrayList<>();
    List<String> lines = readLines(fileName);
    for (String line : lines) {
      List<String> lineSplitted = Arrays.asList(line.split(REGEX_COLUMN_SPLITTER));
      List<String> cleanedFromDoubleQuotesInTheName =
          lineSplitted.stream().map(s -> s.replace("\"", "")).collect(Collectors.toList());
      result.add(cleanedFromDoubleQuotesInTheName);
    }

    return result;
  }
}
