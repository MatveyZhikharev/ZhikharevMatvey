package org.example;

import java.util.Arrays;
import java.util.List;

public class BubbleSort implements SortingAlgorithm{
  @Override
  public List<Integer> sort(List<Integer> list) {
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
