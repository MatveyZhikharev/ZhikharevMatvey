package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SorterTest {
  @Test
  public void testSort() throws Exception {
    Sorter sorter = new Sorter();
    sorter.addAlgorithm(new MergeSort());
    sorter.addAlgorithm(new BubbleSort());

    List<Integer> list = Arrays.asList(24, 8, 2007, 3000, 50);
    List<Integer> sortedList = sorter.sort(list);
    assertEquals(Arrays.asList(8, 24, 50, 2007, 3000), sortedList);
  }
}