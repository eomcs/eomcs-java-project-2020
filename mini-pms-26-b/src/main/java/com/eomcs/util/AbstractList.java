package com.eomcs.util;

import java.util.NoSuchElementException;

public abstract class AbstractList<E> implements List<E>{

  protected int size;

  @Override
  public int size() {
    return size;
  }

  // 인터페이스 새로 추가된 규칙, `Iterator` 구현체를 리턴하는 메서드를 정의한다.
  @Override
  public Iterator<E> iterator() {
    return new ListIterator<E>();
  }

  // 논스태틱 중첩 클래스로 정의한 Iterator 구현체 
  // => 바깥 클래스의 인스턴스 멤버를 직접 접근할 수 있다.
  // => 생성자에서 바깥 클래스의 인스턴스를 받을 필요가 없다.
  // => 중첩 클래스가 인스턴스 멤버가 되는 순간 
  //    바깥 클래스의 영향을 받기 때문에 
  //    바깥 클래스의 타입 파라미터와 중첩 클래스의 타입 파라미터와 이름이 같으면 
  //    충돌이 발생하게 된다.
  //    따라서 중첩 클래스의 타입 파라미터 이름을 변경하여 
  //    바깥 클래스의 타입 파라미터와 구분하도록 한다.
  private class ListIterator<T> implements Iterator<T> {
    int cursor;

    @Override
    public boolean hasNext() {
      // 바깥 클래스의 인스턴스 멤버에 접근하려면 다음과 같이 바깥 클래스의 이름으로
      // this 변수를 사용해야 한다.
      return cursor < AbstractList.this.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
      //
      // 호출하는 메서드가 중첩 클래스에 없으면 바깥 클래스에서 찾기 때문에 
      // 바깥 클래스의 인스턴스를 주소를 생략할 수 있다.
      // 논스태틱 중첩 클래스도 바깥 클래스의 인스턴스 멤버이기 때문에
      // 중첩 클래스의 멤버에서 바깥 클래스의 인스턴스 멤버에 바로 접근할 수 있다.
      // 마치 인스턴스 메서드 안에서 다른 인스턴스 멤버를 사용하는 것과 같다.
      if (cursor ==  /*AbstractList.this.*/size())
      throw new NoSuchElementException();
      
      // 바깥 클래스의 타입 파라미터 E와 중첩 클래스의 타입 파라미터 T가 결국 같은 타입을 가리키지만 
      // 컴파일러 입장에서는 서로 다른 값이 될 수 있으므로 
      // 다음과 같이 명시적으로 형변화해야 한다.
      return (T) /*AbstractList.this.*/get(cursor++);
    }
  }
}
