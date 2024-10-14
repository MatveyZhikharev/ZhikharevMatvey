package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSort implements SortingAlgorithm {
  private int sizeLimit;

  public MergeSort (int sizeL) {
    sizeLimit = sizeL;
  }
  @Override
  public List<Integer> sort(List<Integer> list) throws Exception {
    if (list.size() > sizeLimit) {
      throw new Exception("Size limit exceeded for Merge Sort");
    }
    List<Integer> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);
    return sortedList;
  }
}