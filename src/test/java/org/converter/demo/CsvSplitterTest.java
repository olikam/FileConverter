package org.converter.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.converter.demo.splitter.CsvSplitter;
import org.converter.demo.splitter.Splitter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CsvSplitterTest {

  private Splitter spySplitter;

  @Before
  public void init() {
    spySplitter = Mockito.spy(new CsvSplitter());
  }

  @Test
  public void successTest() throws IOException {
    Mockito.doReturn(getMockReadLines()).when(spySplitter).readLines("filename");
    List<List<String>> actual = spySplitter.split("filename");
    List<List<String>> expected = getExpected();
    Assert.assertTrue(isEqual(actual, expected));
  }

  @Test
  public void emptyListTest() throws IOException {
    Mockito.doReturn(Collections.emptyList()).when(spySplitter).readLines("filename");
    List<List<String>> actual = spySplitter.split("filename");
    Assert.assertEquals(0, actual.size());
  }

  public List<String> getMockReadLines() {
    List<String> value = new ArrayList<>();
    value.add("column1,column2,column3");
    value.add("\"xx,yy\",12,13");
    value.add("\"zz,qq\",22,23");
    return value;
  }

  private List<List<String>> getExpected() {
    List<List<String>> value = new ArrayList<>();
    value.add(Arrays.asList("column1", "column2", "column3"));
    value.add(Arrays.asList("xx,yy", "12", "13"));
    value.add(Arrays.asList("zz,qq", "22", "23"));
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
