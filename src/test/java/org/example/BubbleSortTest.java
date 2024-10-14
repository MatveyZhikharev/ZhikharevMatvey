package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {
  @Test
  public void testSortSingleElementList() throws Exception {
    BubbleSort bubbleSort = new BubbleSort(10);
    List<Integer> list = Arrays.asList(52);
    List<Integer> sortedList = bubbleSort.sort(list);
    assertEquals(Arrays.asList(52), sortedList);
  }
  @Test
  public void testSortMultipleElementList() throws Exception {
    BubbleSort bubbleSort = new BubbleSort(10);
    List<Integer> list = Arrays.asList(7, 8, 1, 2, 5, 2);
    List<Integer> sortedList = bubbleSort.sort(list);
    assertEquals(Arrays.asList(1, 2, 2, 5, 7, 8), sortedList);
  }

  @Test
  public void testSortEmptyList() throws Exception {
    BubbleSort bubbleSort = new BubbleSort(10);
    List<Integer> list = Arrays.asList();
    List<Integer> sortedList = bubbleSort.sort(list);
    assertEquals(Arrays.asList(), sortedList);
  }

  @Test
  public void testLimitExceed() throws Exception {
    BubbleSort bubbleSort = new BubbleSort(5);
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    Exception thrown = assertThrows(Exception.class, () -> {bubbleSort.sort(list);});
    assertEquals("Size limit exceeded for Bubble Sort", thrown.getMessage());
  }
}