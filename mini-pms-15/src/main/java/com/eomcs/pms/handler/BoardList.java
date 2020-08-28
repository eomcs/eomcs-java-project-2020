package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Board;

public class BoardList {

  static final int DEFAULT_CAPACITY = 3;
  Board[] list;
  int size = 0;

  public BoardList() {
    list = new Board[DEFAULT_CAPACITY];
  }

  public BoardList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      list = new Board[DEFAULT_CAPACITY];
    } else {
      list = new Board[initialCapacity];
    }
  }

  public void add(Board board) {
    if (size == list.length) {
      // 현재 배열에 게시글 객체가 꽉 찼으면, 배열을 늘린다.
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);

      //      Board[] arr = new Board[newCapacity];
      //      for (int i = 0; i < list.length; i++) {
      //        arr[i] = list[i];
      //      }
      //      list = arr;

      list = Arrays.copyOf(list, newCapacity);
      System.out.printf("==> 새 배열을 %d 개 생성하였음!\n", newCapacity);
    }
    list[size++] = board;
  }

  public Board[] toArray() {
    //    Board[] arr = new Board[size];
    //    for (int i = 0; i < size; i++) {
    //      arr[i] = list[i];
    //    }
    //    return arr;

    return Arrays.copyOf(list, size);
  }
}




