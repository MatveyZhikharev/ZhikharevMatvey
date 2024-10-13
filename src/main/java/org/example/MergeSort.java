package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSort implements SortingAlgorithm {
  @Override
  public List<Integer> sort(List<Integer> list) {
    List<Integer> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);
    return sortedList;
  }
}