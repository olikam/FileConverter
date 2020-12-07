package org.converter.demo.exception;

public class UnsupportedFileFormatException extends Exception {

  private static final long serialVersionUID = -3334645969939585532L;

  public UnsupportedFileFormatException(String fileFormat) {
    super("Unsupported file format: " + fileFormat);
  }
}
