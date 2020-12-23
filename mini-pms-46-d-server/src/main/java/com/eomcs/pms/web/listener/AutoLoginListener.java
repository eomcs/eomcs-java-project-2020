package com.eomcs.pms.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

//@WebListener
public class AutoLoginListener implements ServletRequestListener {
  @Override
  public void requestInitialized(ServletRequestEvent sre) {
    System.out.println("ㅋㅋ 자동 로그인!");
    HttpSession session = ((HttpServletRequest)sre.getServletRequest()).getSession();

    if (session.getAttribute("loginUser") == null) {
      try {
        // 서블릿 컨테이너의 컴포넌트가 스프링 IoC 컨테이너의 객체를 사용하려면
        // 먼저 스프링 IoC 컨테이너를 알아야 한다.
        WebApplicationContext iocContainer =
            WebApplicationContextUtils.getWebApplicationContext(sre.getServletContext());
        MemberService memberService = iocContainer.getBean(MemberService.class);
        Member member = memberService.get("bbb@test.com", "1111");
        session.setAttribute("loginUser", member);
      } catch (Exception e) {
        System.out.println("자동 로그인 처리 중 오류 발생!");
        e.printStackTrace();
      }
    }
  }
}
