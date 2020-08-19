package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;

public class ProjectList {

  static final int DEFAULT_CAPACITY = 100;
  Project[] list = new Project[DEFAULT_CAPACITY];
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
    list[size++] = project;
  }

  public Project[] toArray() {
    Project[] arr = new Project[size];
    for (int i = 0; i < size; i++) {
      arr[i] = list[i];
    }
    return arr;
  }
}
