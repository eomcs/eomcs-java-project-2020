package com.eomcs.lms.web;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;

@Controller
public class AuthController {

  @Autowired
  MemberService memberService;

  @RequestMapping("/auth/form")
  public String form(HttpServletRequest request, Map<String, Object> model) {
    String email = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("email")) {
          email = cookie.getValue();
          break;
        }
      }
    }
    model.put("email", email);
    return "/auth/form.jsp";
  }

  @RequestMapping("/auth/login")
  public String login(HttpServletRequest request, //
      String email, String password, String saveEmail) throws Exception {

    Cookie cookie = new Cookie("email", email);
    if (saveEmail != null) {
      cookie.setMaxAge(60 * 60 * 24 * 30);
    } else {
      cookie.setMaxAge(0);
    }

    // 프론트 컨트롤러가 쿠키를 응답헤더에 담을 수 있도록
    // 쿠키 바구니에 저장한다.
    @SuppressWarnings("unchecked")
    ArrayList<Cookie> cookies = (ArrayList<Cookie>) request.getAttribute("cookies");
    cookies.add(cookie);

    Member member = memberService.get(email, password);
    if (member != null) {
      request.getSession().setAttribute("loginUser", member);
      request.setAttribute("refreshUrl", "2;url=../../index.html");
      // 인클루딩 되는 서블릿은 응답 헤더를 추가할 수 없다.
      // 따라서 프론트 컨트롤러에게 추가해달라고 요청해야 한다.
    } else {
      request.getSession().invalidate();
      request.setAttribute("refreshUrl", "2;url=login");
    }

    return "/auth/login.jsp";
  }

  @RequestMapping("/auth/logout")
  public String logout(HttpServletRequest request) {
    request.getSession().invalidate();
    return "redirect:../../index.html";
  }
}
