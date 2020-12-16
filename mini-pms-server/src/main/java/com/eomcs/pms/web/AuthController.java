package com.eomcs.pms.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired MemberService memberService;

  @RequestMapping(value = "login", method = RequestMethod.GET)
  public ModelAndView loginForm(HttpServletRequest request) throws Exception {

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

    ModelAndView mv = new ModelAndView();
    mv.addObject("email", email);
    mv.setViewName("/auth/form.jsp");
    return mv;
  }

  @RequestMapping(value="login", method = RequestMethod.POST)
  public String login(
      String email,
      String password,
      String saveEmail,
      HttpServletResponse response,
      HttpSession session) throws Exception {

    Cookie emailCookie = new Cookie("email", email);

    if (saveEmail != null) {
      emailCookie.setMaxAge(60 * 60 * 24 * 7);
    } else {
      emailCookie.setMaxAge(0); // 유효기간이 0이면 삭제하라는 의미다.
    }
    response.addCookie(emailCookie);

    Member member = memberService.get(email, password);
    if (member == null) {
      return "/auth/loginError.jsp";
    }
    session.setAttribute("loginUser", member);
    return "redirect:../../index.html";
  }

  @RequestMapping("loginUser")
  public String loginUser() throws Exception {
    return "/auth/loginUser.jsp";
  }

  @RequestMapping("logout")
  public ModelAndView logout(HttpSession session, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      session.invalidate();
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("loginUser", loginUser);
    mv.setViewName("/auth/logout.jsp");
    return mv;
  }
}
