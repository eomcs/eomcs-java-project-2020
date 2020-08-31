package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Project;

public class ProjectList {

  static final int DEFAULT_CAPACITY = 100;
  Project[] list;
  int size = 0;

  public ProjectList() {
    list = new Project[DEFAULT_CAPACITY];
  }

  public ProjectList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      list = new Project[DEFAULT_CAPACITY];
    } else {
      list = new Project[initialCapacity];
    }
  }

  public void add(Project project) {
    if (size == list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
      System.out.printf("==> 새 배열을 %d 개 생성하였음!\n", newCapacity);
    }
    list[size++] = project;
  }

  public Project[] toArray() {
    return Arrays.copyOf(list, size);
  }
}
