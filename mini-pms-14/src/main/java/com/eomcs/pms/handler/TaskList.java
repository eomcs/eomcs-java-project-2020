package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Task;

public class TaskList {

  static final int DEFAULT_CAPACITY = 100;
  Task[] list;
  int size = 0;

  public TaskList() {
    list = new Task[DEFAULT_CAPACITY];
  }

  public TaskList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      list = new Task[DEFAULT_CAPACITY];
    } else {
      list = new Task[initialCapacity];
    }
  }

  public void add(Task task) {
    if (size == list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
      System.out.printf("==> 새 배열을 %d 개 생성하였음!\n", newCapacity);
    }
    list[size++] = task;
  }

  public Task[] toArray() {
    return Arrays.copyOf(list, size);
  }
}
