package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
  @Test
  public void testSortSingleElementList() {
    MergeSort mergeSort = new MergeSort();
    List<Integer> list = Arrays.asList(52);
    List<Integer> sortedList = mergeSort.sort(list);
    assertEquals(Arrays.asList(52), sortedList);
  }
  @Test
  public void testSortMultipleElementList() {
    MergeSort mergeSort = new MergeSort();
    List<Integer> list = Arrays.asList(7, 8, 1, 2, 5, 2);
    List<Integer> sortedList = mergeSort.sort(list);
    assertEquals(Arrays.asList(1, 2, 2, 5, 7, 8), sortedList);
  }

  @Test
  public void testSortEmptyList() {
    MergeSort mergeSort = new MergeSort();
    List<Integer> list = Arrays.asList();
    List<Integer> sortedList = mergeSort.sort(list);
    assertEquals(Arrays.asList(), sortedList);
  }
}