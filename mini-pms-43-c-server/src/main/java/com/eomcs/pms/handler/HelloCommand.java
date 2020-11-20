package com.eomcs.pms.handler;

import java.io.PrintWriter;

@CommandAnno("/hello")
public class HelloCommand implements Command {

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();

    out.println("안녕하세요!");
  }
}
