package com.eomcs.pms.handler;

import java.util.Map;

// Command 규칙에 따라 클래스를 정의한다.
public class HelloCommand implements Command {

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("안녕하세요!");
  }
}
