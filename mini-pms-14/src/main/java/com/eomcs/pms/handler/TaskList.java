package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Task;

public class TaskList {

  static final int DEFAULT_CAPACITY = 100;
  Task[] list = new Task[DEFAULT_CAPACITY];
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
    list[size++] = task;
  }

  public Task[] toArray() {
    Task[] arr = new Task[size];
    for (int i = 0; i < size; i++) {
      arr[i] = list[i];
    }
    return arr;
  }
}
