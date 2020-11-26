package com.eomcs.pms.web;

// 사용자의 명령을 처리하는 객체에 대해 호출할 메서드 규칙을 정의 한다.
public interface Command {
  // 향후 커맨드 객체에게 전달할 파라미터가 추가될 가능성을 위해
  // 파라미터 값들을 단일 Request 객체에 담아서 넘긴다.
  // 나중에 전달할 값이 늘어나더라도 execute() 메서드는 변경할 필요가 없다.
  // Request 클래스만 변경하면 된다.
  void execute(Request request);
}
