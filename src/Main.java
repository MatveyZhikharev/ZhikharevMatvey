package src;

public class Main {
  public static void main(String[] args) {
    CustomArrayList<Integer> array = new CustomArrayList<>(1, 2, 3);
    for (int i = 4; i < 52; i++) {
      array.add(i);
    }
    array.remove(5);
    array.add(0);

    for (int i = 0; i < array.size(); i++) {
      System.out.print(array.get(i) + " ");
    }
    System.out.println();

    CustomArrayList<String> newArray = new CustomArrayList<>();
    for (int i = 5; i < 26; i++) {
      newArray.add(String.valueOf((char) ("MTS".charAt(i % 3) + i)));
    }
    newArray.remove(1);
    newArray.remove(1);
    newArray.remove(2);

    for (int i = 0; i < newArray.size(); i++) {
      System.out.print(newArray.get(i) + " ");
    }
    System.out.println();
  }
}
