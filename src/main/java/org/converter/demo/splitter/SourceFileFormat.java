package org.converter.demo.splitter;

import java.util.Optional;

public enum SourceFileFormat {
  CSV("csv", CsvSplitter.class),
  PRN("prn", PrnSplitter.class);

  private final String value;

  private final Class<? extends Splitter> clazz;

  SourceFileFormat(String value, Class<? extends Splitter> clazz) {
    this.value = value;
    this.clazz = clazz;
  }

  public static Optional<SourceFileFormat> fromString(String value) {
    if (value != null) {
      for (SourceFileFormat type : SourceFileFormat.values()) {
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

  public Splitter getSplitter() throws Exception {
    return clazz.getDeclaredConstructor().newInstance();
  }
}
