package org.converter.demo.constants;

import java.io.File;

public class FilePaths {

  private static final String ROOT_PATH = "files";
  public static final String DESTINATION_PATH =
      ROOT_PATH + File.separator + "destination" + File.separator;
  public static final String SOURCE_PATH = ROOT_PATH + File.separator + "source" + File.separator;

  private FilePaths() {}
}
