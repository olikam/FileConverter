package org.converter.demo.converter;

import java.util.List;

public class HtmlConverter implements Converter {

  @Override
  public String convert(List<List<String>> lines) {
    return "<!DOCTYPE html><html>" + getHead() + getBody(lines) + "</html>";
  }

  private String getHead() {
    return "<head>" + getMeta() + getTitle() + "</head>";
  }

  private String getMeta() {
    return "<meta charset='UTF-8'>";
  }

  private String getTitle() {
    return "<title>HTML converter for Marktplaats</title>";
  }

  private String getBody(List<List<String>> lines) {
    if (lines == null || lines.isEmpty()) {
      throw new RuntimeException("No data to convert.");
    }

    StringBuilder bodyBuilder = new StringBuilder("<body><table border=1>");
    List<String> columnTitles = lines.get(0);
    bodyBuilder.append(getThead(columnTitles));

    List<List<String>> rows = lines.subList(1, lines.size());
    bodyBuilder.append(getTBody(rows));
    bodyBuilder.append(getTBody(rows));
    bodyBuilder.append("</table></body>");

    return bodyBuilder.toString();
  }

  private String getThead(List<String> columnTitles) {
    if (columnTitles == null || columnTitles.isEmpty()) {
      throw new RuntimeException("There are no column titles in the file.");
    }

    StringBuilder theadBuilder = new StringBuilder("<thead><tr>");
    for (String title : columnTitles) {
      String th = "<th>" + title + "</th>";
      theadBuilder.append(th);
    }
    theadBuilder.append("</tr></thead>");

    return theadBuilder.toString();
  }

  private String getTBody(List<List<String>> rows) {
    if (rows == null || rows.isEmpty()) {
      throw new RuntimeException("There are no rows in the file.");
    }

    StringBuilder tBodyBuilder = new StringBuilder("<tbody>");
    for (List<String> row : rows) {
      String tr = getTr(row);
      tBodyBuilder.append(tr);
    }
    tBodyBuilder.append("</tbody>");

    return tBodyBuilder.toString();
  }

  private String getTr(List<String> row) {
    if (row == null || row.isEmpty()) {
      throw new RuntimeException("One of the rows is empty.");
    }

    StringBuilder trBuilder = new StringBuilder("<tr>");
    for (String column : row) {
      String td = getTd(column);
      trBuilder.append(td);
    }
    trBuilder.append("</tr>");

    return trBuilder.toString();
  }

  private String getTd(String column) {
    if (column == null) {
      throw new RuntimeException("There is no table value.");
    }

    return "<td>" + column + "</td>";
  }
}
