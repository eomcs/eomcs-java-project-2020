package com.eomcs.context;

// 애플리케이션의 상태가 변경되었을 때
// 호출할 메서드 규칙을 정의한다.
// 즉 애플리케이션 상태 변경에 대해 보고를 받을 "Observer" 규칙을 정의한다.
// 보통 옵저버를 "리스너(listener)/구독자(subscriber)"라 부른다.
//
public interface ApplicationContextListener {
  // 발행자(애플리케이션)가 애플리케이션 시작을 알리기 위해 호출하는 메서드
  void contextInitialized();

  // 발행자(애플리케이션)가 애플리케이션 종료를 알리기 위해 호출하는 메서드
  void contextDestroyed();
}
