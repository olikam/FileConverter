package org.converter.demo.converter;

import java.util.List;
import org.converter.demo.exception.UnsupportedFileFormatException;

class XmlConverter implements Converter {

  @Override
  public String convert(List<List<String>> lines) throws UnsupportedFileFormatException {
    // No need to implement. I wanted to just prove that the software design is appropriate for
    // Single Responsibility and Open-Closed principles.
    // You can add any converter (or splitter) like this way. You just need to add new format into
    // FileFormat enum classes.
    throw new UnsupportedFileFormatException(DestinationFileFormat.XML.getValue());
  }
}
