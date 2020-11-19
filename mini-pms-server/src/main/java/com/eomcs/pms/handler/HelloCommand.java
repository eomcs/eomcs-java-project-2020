package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

// Command 규칙에 따라 클래스를 정의한다.
public class HelloCommand implements Command {

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    out.println("안녕하세요!");
  }
}
