package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {
  @Test
  public void testSortSingleElementList() {
    BubbleSort bubbleSort = new BubbleSort();
    List<Integer> list = Arrays.asList(52);
    List<Integer> sortedList = bubbleSort.sort(list);
    assertEquals(Arrays.asList(52), sortedList);
  }
  @Test
  public void testSortMultipleElementList() {
    BubbleSort bubbleSort = new BubbleSort();
    List<Integer> list = Arrays.asList(7, 8, 1, 2, 5, 2);
    List<Integer> sortedList = bubbleSort.sort(list);
    assertEquals(Arrays.asList(1, 2, 2, 5, 7, 8), sortedList);
  }

  @Test
  public void testSortEmptyList() {
    BubbleSort bubbleSort = new BubbleSort();
    List<Integer> list = Arrays.asList();
    List<Integer> sortedList = bubbleSort.sort(list);
    assertEquals(Arrays.asList(), sortedList);
  }
}