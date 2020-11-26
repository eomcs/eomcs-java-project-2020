package com.eomcs.pms.filter;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.web.Request;

public class LogCommandFilter implements CommandFilter {
  PrintWriter logOut;

  @Override
  public void init(Map<String, Object> context) throws Exception {
    File file = (File) context.get("logFile");
    logOut = new PrintWriter(file);
    System.out.println("로그 파일을 열었습니다.");
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
