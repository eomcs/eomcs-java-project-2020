package com.eomcs.util;

import java.util.Arrays;

public class ArrayList {

  static final int DEFAULT_CAPACITY = 3;
  Object[] list;
  int size = 0;

  public ArrayList() {
    list = new Object[DEFAULT_CAPACITY];
  }

  public ArrayList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      list = new Object[DEFAULT_CAPACITY];
    } else {
      list = new Object[initialCapacity];
    }
  }

  public void add(Object obj) {
    if (size == list.length) {
      // 현재 배열에 객체가 꽉 찼으면, 배열을 늘린다.
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);

      //      Object[] arr = new Object[newCapacity];
      //      for (int i = 0; i < list.length; i++) {
      //        arr[i] = list[i];
      //      }
      //      list = arr;

      list = Arrays.copyOf(list, newCapacity);
      System.out.printf("==> 새 배열을 %d 개 생성하였음!\n", newCapacity);
    }
    list[size++] = obj;
  }

  public Object[] toArray() {
    //    Object[] arr = new Object[size];
    //    for (int i = 0; i < size; i++) {
    //      arr[i] = list[i];
    //    }
    //    return arr;

    return Arrays.copyOf(list, size);
  }
}




