package org.converter.demo.splitter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.converter.demo.constants.DateFormatConstants;
import org.converter.demo.constants.DecimalFormatConstants;

public class PrnSplitter implements Splitter {

  private static final int[] COLUMN_SIZES = {16, 22, 9, 14, 13, 8};
  private static final int BIRTHDAY_INDEX = 5;
  private static final int CREDIT_LIMIT_INDEX = 4;

  @Override
  public List<List<String>> split(String fileName) throws IOException {
    List<List<String>> result = new ArrayList<>();

    List<String> lines = readLines(fileName);
    int i = 0;
    for (String line : lines) {
      List<String> row = getRow(line);
      if (i > 0) {
        formatBirthday(row);
        formatCreditLimit(row);
      }
      result.add(row);
      i++;
    }

    return result;
  }

  private void formatBirthday(List<String> row) {
    String birthday = row.get(BIRTHDAY_INDEX);
    String formattedBirthday = formatDate(birthday);
    row.set(BIRTHDAY_INDEX, formattedBirthday);
  }

  private String formatDate(String time) {
    DateTimeFormatter sourceFormatter =
        DateTimeFormatter.ofPattern(DateFormatConstants.DATE_FORMAT_YYYYMMDD);
    LocalDate date = LocalDate.parse(time, sourceFormatter);

    DateTimeFormatter destinationFormatter =
        DateTimeFormatter.ofPattern(DateFormatConstants.DATE_FORMAT_WITHSLASH_DDMMYYYY);
    return date.format(destinationFormatter);
  }

  private void formatCreditLimit(List<String> row) {
    String creditLimitStr = row.get(CREDIT_LIMIT_INDEX);
    String formattedCreditLimit = formatPrice(creditLimitStr);
    row.set(CREDIT_LIMIT_INDEX, formattedCreditLimit);
  }

  private String formatPrice(String priceStr) {
    String result = priceStr;
    long priceLong = Long.parseLong(priceStr.trim());
    if (priceLong % 10 == 0) {
      Double priceDouble = priceLong / 100.0;
      DecimalFormat format = new DecimalFormat(DecimalFormatConstants.PRICE_DECIMAL_FORMAT);
      result = format.format(priceDouble);
    }

    return result;
  }

  private List<String> getRow(String line) {
    List<String> row = new ArrayList<>();

    int beginningIndex;
    int endingIndex = 0;
    for (int size : COLUMN_SIZES) {
      beginningIndex = endingIndex;
      endingIndex = beginningIndex + size;
      String value = line.substring(beginningIndex, endingIndex);
      row.add(value.trim());
    }

    return row;
  }
}
