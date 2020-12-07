package org.converter.demo.converter;

import java.util.Optional;

public enum DestinationFileFormat {
  HTML("html", HtmlConverter.class),
  XML("xml", XmlConverter.class);

  private final String value;
  private final Class<? extends Converter> clazz;

  DestinationFileFormat(String value, Class<? extends Converter> clazz) {
    this.value = value;
    this.clazz = clazz;
  }

  public static Optional<DestinationFileFormat> fromString(String value) {
    if (value != null) {
      for (DestinationFileFormat type : DestinationFileFormat.values()) {
        if (type.getValue().equals(value.trim().toLowerCase())) {
          return Optional.of(type);
        }
      }
    }

    return Optional.empty();
  }

  public String getValue() {
    return value;
  }

  public Converter getConverter() throws Exception {
    return clazz.getDeclaredConstructor().newInstance();
  }
}
