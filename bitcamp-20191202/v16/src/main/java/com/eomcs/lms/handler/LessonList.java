package com.eomcs.lms.handler;

import java.util.Arrays;
import com.eomcs.lms.domain.Lesson;

public class LessonList {
  
  static final int DEFAULT_CAPACITY = 100;
  
  Lesson[] list;
  int size = 0;
  
  public LessonList() {
    this.list = new Lesson[DEFAULT_CAPACITY];
  }
  
  public LessonList(int capacity) {
    if (capacity < DEFAULT_CAPACITY || capacity > 10000)
      this.list = new Lesson[DEFAULT_CAPACITY];
    else 
      this.list = new Lesson[capacity];
  }

  public Lesson[] toArray() {
    /*
    Lesson[] arr = new Lesson[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.list[i];
    }
    return arr;
    */
    return Arrays.copyOf(this.list, this.size);
  }

  public void add(Lesson lesson) {
    if (this.size == this.list.length) {
      // 현재 배열에 게시글 객체가 꽉 찼으면, 배열을 늘린다.
      int oldCapacity = this.list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      this.list = Arrays.copyOf(this.list, newCapacity);
    }
    this.list[this.size++] = lesson;
  }
  
  public Lesson get(int no) {
    for (int i = 0; i < this.size; i++) {
      if (this.list[i].getNo() == no) {
        return this.list[i];
      }
    }
    return null;
  }
}










