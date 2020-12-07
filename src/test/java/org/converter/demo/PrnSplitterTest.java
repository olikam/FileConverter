package org.converter.demo;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.converter.demo.splitter.PrnSplitter;
import org.converter.demo.splitter.Splitter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrnSplitterTest {

  private Splitter spySplitter;

  @Before
  public void init() {
    spySplitter = Mockito.spy(new PrnSplitter());
  }

  @Test
  public void successTest() throws IOException {
    Mockito.doReturn(getMockReadLines()).when(spySplitter).readLines("filename");
    List<List<String>> actual = spySplitter.split("filename");
    List<List<String>> expected = getExpected();
    Assert.assertTrue(isEqual(actual, expected));
  }

  @Test(expected = DateTimeParseException.class)
  public void dateFormatTest() throws IOException {
    Mockito.doReturn(getCorruptedDateFormatMockReadLines()).when(spySplitter).readLines("filename");
    spySplitter.split("filename");
  }

  public List<String> getMockReadLines() {
    List<String> value = new ArrayList<>();
    value.add("Name            Address               Postcode Phone         Credit Limit Birthday");
    value.add("Johnson, John   Voorstraat 32         3122gg   020 3849381        1000000 19870101");
    value.add("Anderson, Paul  Dorpsplein 3A         4532 AA  030 3458986       10909300 19651203");
    return value;
  }

  public List<String> getCorruptedDateFormatMockReadLines() {
    List<String> value = new ArrayList<>();
    value.add("Name            Address               Postcode Phone         Credit Limit Birthday");
    value.add("Johnson, John   Voorstraat 32         3122gg   020 3849381        1000000 01011987");
    value.add("Anderson, Paul  Dorpsplein 3A         4532 AA  030 3458986       10909300 03121965");
    return value;
  }

  private List<List<String>> getExpected() {
    List<List<String>> value = new ArrayList<>();
    value.add(Arrays.asList("Name", "Address", "Postcode", "Phone", "Credit Limit", "Birthday"));
    value.add(
        Arrays.asList(
            "Johnson, John", "Voorstraat 32", "3122gg", "020 3849381", "10000", "01/01/1987"));
    value.add(
        Arrays.asList(
            "Anderson, Paul", "Dorpsplein 3A", "4532 AA", "030 3458986", "109093", "03/12/1965"));
    return value;
  }

  private boolean isEqual(List<List<String>> actual, List<List<String>> expected) {
    if (actual == null && expected == null) {
      return true;
    }

    if (actual == null || expected == null) {
      return false;
    }

    if (actual.size() != expected.size()) {
      return false;
    }

    for (int i = 0; i < actual.size(); i++) {
      List<String> actualSubList = actual.get(i);
      List<String> expectedSubList = expected.get(i);
      if (!actualSubList.equals(expectedSubList)) {
        return false;
      }
    }

    return true;
  }
}
