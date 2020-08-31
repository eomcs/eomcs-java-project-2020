package com.eomcs.pms.handler;

import java.util.Arrays;
import com.eomcs.pms.domain.Member;

public class MemberList {

  static final int DEFAULT_CAPACITY = 100;
  Member[] list;
  int size = 0;

  public MemberList() {
    list = new Member[DEFAULT_CAPACITY];
  }

  public MemberList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      list = new Member[DEFAULT_CAPACITY];
    } else {
      list = new Member[initialCapacity];
    }
  }

  public void add(Member member) {
    if (size == list.length) {
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
      System.out.printf("==> 새 배열을 %d 개 생성하였음!\n", newCapacity);
    }
    list[size++] = member;
  }

  public Member[] toArray() {
    return Arrays.copyOf(list, size);
  }
}
