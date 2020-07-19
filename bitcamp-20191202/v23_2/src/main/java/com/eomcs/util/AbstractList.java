package com.eomcs.util;

// Generalization 2단계:
// => 서브 클래스에 공통 분모(멤버)를 물려주는 용도의 클래스는 직접 인스턴스를 생성하지 못하도록
//    추상 클래스로 선언한다.
// => 개발자가 추상 클래스 여부를 즉각적으로 확인할 수 있도록 보통 클래스 이름을 
//    "AbstractXxxx" 형식으로 짓는다.
// => 서브 클래스에서 구현할 메서드를 그냥 일반 메서드로 정의하면,
//    서브 클래스를 정의하는 개발자가 해당 메서드를 오버라이딩 한다는 보장을 하지 못한다.
// => 상속 받는 메서드 중에서 반드시 서브 클래스에서 오버라이딩 해야 할 메서드라면 
//    문법으로 표시를 하는 것이 좋다.
//    이런 용도로 만든 문법이 "추상 메서드" 이다.
// => 추상 메서드의 오버라이딩은 선택 사항이 아니라 필수 사항이다.
// => 따라서 서브 클래스를 만드는 개발자는 반드시 이 추상 메서드를 구현해야 한다.
// 
// 추상 메서드
// => 서브 클래스가 반드시 재정의 해야 할 메서드라면 수퍼 클래스에서 정의하지 않는다.
// => 또한 서브 클래스가 반드시 해당 메서드를 오버라이딩 하도록 강제하고 싶을 때 사용하는 문법이다.
//
public abstract class AbstractList<E> {
  protected int size;
  
  // size() 메서드처럼 수퍼 클래스에서 구현해도 상관없는 것은 
  // 수퍼 클래스에서 일반 메서드로 구현한다.
  public int size() {
    return size;
  }
  
  // 서브 클래스에서 정의할 메서드라면 수퍼 클래스에서 정의하지 않는다.
  // 대신 서브 클래스가 반드시 구현하도록 강제한다.
  public abstract void add(E e);
  
  public abstract void add(int index, E value);
  
  public abstract E get(int index);
  
  public abstract E set(int index, E e);
  
  public abstract E remove(int index);
  
  public abstract Object[] toArray() ;
  
  public abstract E[] toArray(E[] arr);
}






