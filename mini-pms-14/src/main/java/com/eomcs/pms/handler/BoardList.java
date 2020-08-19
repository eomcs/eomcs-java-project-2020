package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Board;

public class BoardList {

  static final int DEFAULT_CAPACITY = 100;
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
    list[size++] = board;
  }

  public Board[] toArray() {
    Board[] arr = new Board[size];
    for (int i = 0; i < size; i++) {
      arr[i] = list[i];
    }
    return arr;
  }
}




