package org.example;

import java.util.Arrays;
import java.util.List;

public class BubbleSort implements SortingAlgorithm{
  private int sizeLimit;

  public BubbleSort(int sizeL) {
    sizeLimit = sizeL;
  }
  @Override
  public List<Integer> sort(List<Integer> list) throws Exception {
    if (list.size() > sizeLimit) {
      throw new Exception("Size limit exceeded for Bubble Sort");
    }
    Integer[] arr = new Integer[list.size()];
    list.toArray(arr);
    for (int i = 0; i < arr.length; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[i] > arr[j]) {
          int temp = arr[i];
          arr[i] = arr[j];
          arr[j] = temp;
        }
      }
    }
    return Arrays.asList(arr);
  }
}
