package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main( String[] args ) throws Exception {
    Sorter sorter = new Sorter();
    sorter.addAlgorithm(new MergeSort(10));



    List<Integer> list = Arrays.asList(24, 8, 2007, 3000, 50);
    List<Integer> sortedList = sorter.sort(list);

    for (int i = 0; i < sortedList.size(); i++) {
      System.out.print(sortedList.get(i) + " ");
    }
  }
}
