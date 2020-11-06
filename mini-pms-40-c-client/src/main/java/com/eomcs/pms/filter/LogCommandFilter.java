package com.eomcs.pms.filter;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import com.eomcs.pms.handler.Request;

public class LogCommandFilter implements CommandFilter {
  PrintWriter logOut;

  public LogCommandFilter(File file) throws Exception {
    logOut = new PrintWriter(new FileWriter(file));
  }

  @Override
  public void doFilter(Request request, FilterChain next) throws Exception {
    //System.out.println("로그 남겼다!");
    logOut.println(request.getCommandPath());
    logOut.flush();

    next.doFilter(request);

  }

  @Override
  public void destroy() {
    logOut.close();
    System.out.println("로그 파일을 닫았습니다.");
  }
}
