package com.eomcs.lms.context;

// 애플리케이션의 상태가 변경되었을 때
// 호출할 메서드 규칙을 정의한다.
// 즉 애플리케이션 상태 변경에 대해 보고를 받을 "Observer" 규칙을 정의한다.
// 보통 옵저버를 "리스너(listener)"라 부른다.
//
public interface ApplicationContextListener {
  // 애플리케이션이 시작될 때 호출된다.
  void contextInitialized();

  // 애플리케이션이 종료될 떄 호출된다.
  void contextDestroyed();
}
