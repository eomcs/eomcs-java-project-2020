package com.eomcs.util;

import java.util.Arrays;

// ArrayList 가 다룰 객체의 타입을 파라미터로 받을 수 있도록 '타입 파라미터'를 선언한다. 
public class ArrayList<E> {

  static final int DEFAULT_CAPACITY = 3;
  Object[] list;
  int size = 0;

  public ArrayList() {
    // 배열을 만들 때는 타입 파라미터를 사용할 수 없다.
    //list = new E[DEFAULT_CAPACITY]; // 컴파일 오류!
    list = new Object[DEFAULT_CAPACITY];
  }

  public ArrayList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      list = new Object[DEFAULT_CAPACITY];
    } else {
      list = new Object[initialCapacity];
    }
  }

  // 메서드의 파라미터를 선언할 때 클래스 선언부에 지정한 타입 파라미터의 이름을 사용한다.  
  public void add(E obj) {
    if (size == list.length) {
      // 현재 배열에 객체가 꽉 찼으면, 배열을 늘린다.
      int oldCapacity = list.length;
      int newCapacity = oldCapacity + (oldCapacity >> 1);
      list = Arrays.copyOf(list, newCapacity);
    }
    list[size++] = obj;
  }

  //메서드의 리턴 타입을 선언할 때 클래스 선언부의 타입 파라미터의 이름을 사용한다.
  public E[] toArray(Class<E[]> arrayType) {
    // Object[] 배열이 아닌 임의 타입 배열을 만들어 복사하려면 
    // Arrays.copyOf() 를 호출할 때 반드시 배열의 타입 정보를 넘겨야 한다.
    // 이를 위해 toArray() 메서드에서 배열의 타입 정보를 받을 수 있도록 파라미터를 선언한다.
    return Arrays.copyOf(list, size, arrayType);
  }
}




