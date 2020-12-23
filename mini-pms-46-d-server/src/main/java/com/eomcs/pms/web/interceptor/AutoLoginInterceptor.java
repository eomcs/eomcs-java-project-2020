package com.eomcs.pms.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

// 로그인 여부를 검사하는 인터셉터
public class AutoLoginInterceptor implements HandlerInterceptor {

  MemberService memberService;

  public AutoLoginInterceptor(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("AutoLoginInterceptor 실행!");

    if (request.getSession().getAttribute("loginUser") == null) {
      // 개발하는 동안 로그인을 자동으로 처리하기 위해
      // 임의의 사용자로 로그인 한다.
      Member member = memberService.get("aaa@test.com", "1111");
      request.getSession().setAttribute("loginUser", member);
    }
    return true;
  }

}
