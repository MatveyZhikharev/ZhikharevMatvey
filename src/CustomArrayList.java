package src;

/**
 * Реализация своего ArrayList
 *
 * @param <T> тип элемента
*/
public class CustomArrayList<T> implements CustomArrayListInterface<T> {
  private Object[] array;
  private int capacity;
  private int currentSize;

  /**
   * Инициализация списка, добавлением переданных объектов items
   *
   * @param items массив объектов типа T
  */
  @SafeVarargs
  public CustomArrayList(T... items) {
    this.array = items;
    this.currentSize = this.capacity = array.length;
    allocateMemory(items.length);
  }

  /**
   * Аллокация памяти для массива
   *
   * @param length длина, необходимая для расширения
   */
  private void allocateMemory(int length) {
    while (capacity <= length) {
      this.capacity = Math.max(1, 2 * this.capacity);
    }
    Object[] newArray = new Object[this.capacity];
    assert this.array != null;
    System.arraycopy(this.array, 0, newArray, 0, this.currentSize);
    this.array = newArray.clone();
  }

  /**
   * Добавление объекта в конец массива
   *
   * @param item объект
   */
  @Override
  public void add(Object item) {
    if (this.currentSize == this.capacity) {
      allocateMemory(this.capacity);
    }
    array[this.currentSize++] = item;
  }

  /**
   * Получение элемента по индексу
   *
   * @param index индекс нужного элемента
   */
  @Override
  public T get(int index) {
    if (index < 0 || index >= this.currentSize) {
      throw new IndexOutOfBoundsException(index);
    }
    return (T) array[index];
  }

  /**
   * Удаление элемента по индексу
   *
   * @param index индекс нужного элемента
   */
  @Override
  public void remove(int index) {
    if (index < 0 || index >= this.currentSize) {
      throw new IndexOutOfBoundsException(index);
    }
    for (int i = index; i + 1 < this.currentSize; i++) {
      this.array[i] = array[i + 1];
    }
    --this.currentSize;
  }

  /**
   * Возвращение длинны списка
   */
  public int size() {
    return this.currentSize;
  }
}
