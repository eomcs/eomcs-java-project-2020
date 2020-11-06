package com.eomcs.pms.filter;

import com.eomcs.pms.handler.Request;

// 역할:
// - 필터를 실행할 때 호출하는 메서드 규칙을 정의한다.
//
public interface CommandFilter {
  void doFilter(Request request, FilterChain next) throws Exception;
}
