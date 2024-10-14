package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
  @Test
  public void testSortSingleElementList() throws Exception {
    MergeSort mergeSort = new MergeSort(10);
    List<Integer> list = Arrays.asList(52);
    List<Integer> sortedList = mergeSort.sort(list);
    assertEquals(Arrays.asList(52), sortedList);
  }
  @Test
  public void testSortMultipleElementList() throws Exception {
    MergeSort mergeSort = new MergeSort(10);
    List<Integer> list = Arrays.asList(7, 8, 1, 2, 5, 2);
    List<Integer> sortedList = mergeSort.sort(list);
    assertEquals(Arrays.asList(1, 2, 2, 5, 7, 8), sortedList);
  }

  @Test
  public void testSortEmptyList() throws Exception {
    MergeSort mergeSort = new MergeSort(10);
    List<Integer> list = Arrays.asList();
    List<Integer> sortedList = mergeSort.sort(list);
    assertEquals(Arrays.asList(), sortedList);
  }

  @Test
  public void testLimitExceed() throws Exception {
    MergeSort mergeSort = new MergeSort(5);
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    Exception thrown = assertThrows(Exception.class, () -> {mergeSort.sort(list);});
    assertEquals("Size limit exceeded for Merge Sort", thrown.getMessage());
  }
}