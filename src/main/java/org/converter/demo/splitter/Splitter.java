package org.converter.demo.splitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.converter.demo.constants.FilePaths;

public interface Splitter {

  default List<String> readLines(String fileName) throws IOException {
    Path path = Paths.get(FilePaths.SOURCE_PATH + fileName);
    return Files.readAllLines(path, StandardCharsets.ISO_8859_1);
  }

  List<List<String>> split(String fileName) throws IOException;
}
