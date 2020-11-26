package com.eomcs.pms.filter;

import com.eomcs.pms.web.Request;

// 필터 역할:
// - 로그인 하지 않은 경우 커맨드를 실행시키지 않는다.
//
public class AuthCommandFilter implements CommandFilter {

  @Override
  public void doFilter(Request request, FilterChain next) throws Exception {
    if (request.getCommandPath().equalsIgnoreCase("/login")
        || request.getContext().get("loginUser") != null) {
      next.doFilter(request);
    } else {
      System.out.println("로그인이 필요합니다.");
    }
  }

}
