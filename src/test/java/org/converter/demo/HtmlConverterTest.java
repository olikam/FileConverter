package org.converter.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.converter.demo.converter.Converter;
import org.converter.demo.converter.HtmlConverter;
import org.converter.demo.exception.UnsupportedFileFormatException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class HtmlConverterTest {

  private final Converter htmlConverter = new HtmlConverter();

  @Test(expected = RuntimeException.class)
  public void testNull() throws IOException, UnsupportedFileFormatException {
    htmlConverter.convert(null);
  }

  @Test(expected = RuntimeException.class)
  public void testEmptyList() throws IOException, UnsupportedFileFormatException {
    List<List<String>> lines = new ArrayList<>();
    htmlConverter.convert(lines);
  }

  @Test(expected = RuntimeException.class)
  public void testPartiallyEmptyList() throws IOException, UnsupportedFileFormatException {
    List<List<String>> lines = new ArrayList<>();
    List<String> columnsTitles = Arrays.asList("column1", "column2", "column3");
    lines.add(columnsTitles);
    htmlConverter.convert(lines);
  }

  @Test
  public void successTest() throws IOException, UnsupportedFileFormatException {
    List<List<String>> table = getTableValues();

    String convertedData = htmlConverter.convert(table);
    Document html = Jsoup.parse(convertedData);
    Elements actualTds = html.getElementsByTag("td");

    List<String> expectedTds = new ArrayList<>();
    for (String[] per : getRows()) {
      expectedTds.addAll(Arrays.asList(per));
    }

    for (int i = 0; i < 9; i++) {
      String expected = expectedTds.get(i);
      String actual = actualTds.get(i).text();
      Assert.assertEquals(expected, actual);
    }
  }

  private List<List<String>> getTableValues() {
    List<List<String>> table = new ArrayList<>();

    String[] columns = new String[3];
    columns[0] = "column1";
    columns[1] = "column2";
    columns[2] = "column3";

    String[][] rows = getRows();

    table.add(Arrays.asList(columns));
    table.add(Arrays.asList(rows[0]));
    table.add(Arrays.asList(rows[1]));
    table.add(Arrays.asList(rows[2]));

    return table;
  }

  private String[][] getRows() {
    String[][] rows = new String[3][3];
    rows[0][0] = "11";
    rows[0][1] = "12";
    rows[0][2] = "13";
    rows[1][0] = "21";
    rows[1][1] = "22";
    rows[1][2] = "23";
    rows[2][0] = "31";
    rows[2][1] = "32";
    rows[2][2] = "33";

    return rows;
  }
}
