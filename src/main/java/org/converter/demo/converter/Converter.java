package org.converter.demo.converter;

import java.io.IOException;
import java.util.List;
import org.converter.demo.exception.UnsupportedFileFormatException;

public interface Converter {

  String convert(List<List<String>> lines) throws IOException, UnsupportedFileFormatException;
}
