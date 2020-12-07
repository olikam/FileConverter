package org.converter.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.converter.demo.constants.FilePaths;
import org.converter.demo.converter.DestinationFileFormat;
import org.converter.demo.exception.UnsupportedFileFormatException;
import org.converter.demo.splitter.SourceFileFormat;

public class App {

  private static final String ENTRANCE_SENTENCE_SOURCE_FORMAT =
      "Please write the extension of the source file: ";
  private static final String ENTRANCE_SENTENCE_DESTINATION_FORMAT =
      "Please write the file format that you want to convert to: ";
  private static final String ENTRANCE_SENTENCE_FILE_NAME =
      "Please write the name of the source file: ";
  private static final String CONVERSION_COMPLETED_MESSAGE =
      "Conversion is completed.\nYou can find the converted file in this path in the project root: ";
  private static final String NO_SUCH_FILE_MESSAGE = "No such file found in the path: ";
  private static final String UNEXPECTED_ERROR_MESSAGE =
      "An unexpected error occured. See details below: ";

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      String fileName = getFileNameToConvert(in);
      SourceFileFormat sourceFileFormat = getSourceFileFormat(in);
      DestinationFileFormat destinationFileFormat = getDestinationFileFormat(in);
      String data = ConversionManager.convert(fileName, sourceFileFormat, destinationFileFormat);
      writeFile(fileName + "." + destinationFileFormat.getValue(), data);
    } catch (UnsupportedFileFormatException e) {
      System.out.println(e.getMessage());
    } catch (NoSuchFileException e) {
      System.out.println(NO_SUCH_FILE_MESSAGE + e.getMessage());
    } catch (Exception e) {
      System.out.println(UNEXPECTED_ERROR_MESSAGE);
      e.printStackTrace();
    }
  }

  private static String getFileNameToConvert(Scanner in) {
    System.out.println(ENTRANCE_SENTENCE_FILE_NAME);
    return in.nextLine();
  }

  private static SourceFileFormat getSourceFileFormat(Scanner in)
      throws UnsupportedFileFormatException {
    String message = getSourceFormatMessage();
    System.out.println(message);
    String sourceFileFormatStr = in.nextLine();
    Optional<SourceFileFormat> fileFormat = SourceFileFormat.fromString(sourceFileFormatStr);
    if (fileFormat.isPresent()) {
      return fileFormat.get();
    }

    throw new UnsupportedFileFormatException(sourceFileFormatStr);
  }

  private static DestinationFileFormat getDestinationFileFormat(Scanner in)
      throws UnsupportedFileFormatException {
    String message = getDestinationFormatMessage();
    System.out.println(message);
    String destFileFormatStr = in.nextLine();
    Optional<DestinationFileFormat> fileFormat =
        DestinationFileFormat.fromString(destFileFormatStr);
    if (fileFormat.isPresent()) {
      return fileFormat.get();
    }

    throw new UnsupportedFileFormatException(destFileFormatStr);
  }

  private static String getSourceFormatMessage() {
    String sourceFormats =
        Arrays.stream(SourceFileFormat.values())
            .map(SourceFileFormat::getValue)
            .collect(Collectors.joining(","));
    return String.format(ENTRANCE_SENTENCE_SOURCE_FORMAT + "(%s)", sourceFormats);
  }

  private static String getDestinationFormatMessage() {
    String sourceFormats =
        Arrays.stream(DestinationFileFormat.values())
            .map(DestinationFileFormat::getValue)
            .collect(Collectors.joining(","));
    return String.format(ENTRANCE_SENTENCE_DESTINATION_FORMAT + "(%s)", sourceFormats);
  }

  private static void writeFile(String fileName, String data) throws IOException {
    Path directoryPath = Paths.get(FilePaths.DESTINATION_PATH);
    if (!Files.exists(directoryPath)) {
      Files.createDirectory(directoryPath);
    }

    Path filePath = Paths.get(FilePaths.DESTINATION_PATH + fileName);
    if (!Files.exists(filePath)) {
      Files.createFile(filePath);
    }

    Files.write(
        filePath, data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    System.out.println(CONVERSION_COMPLETED_MESSAGE + filePath);
  }
}
