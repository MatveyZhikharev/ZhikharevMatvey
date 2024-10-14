package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sorter {
  final List<SortingAlgorithm> algorithms;

  public Sorter() {
    this.algorithms = new ArrayList<>();
  }

  public void addAlgorithm(SortingAlgorithm algorithm) {
    this.algorithms.add(algorithm);
  }
  public List<Integer> sort(List<Integer> list) throws Exception {
    for (SortingAlgorithm algorithm : algorithms) {
      try {
        return algorithm.sort(list);
      } catch (Exception e) {
        System.out.println("Algorithm " + algorithm.getClass().getSimpleName() + " failed to sort it");
      }
    }
    throw new Exception("No algorithm was able to sort it");
  }
}
