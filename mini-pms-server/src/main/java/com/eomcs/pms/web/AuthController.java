package com.eomcs.pms.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired MemberService memberService;

  @GetMapping("login")
  public void loginForm() throws Exception {
  }

  @PostMapping("login")
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

  @GetMapping("loginUser")
  public void loginUser() {
  }

  @GetMapping("logout")
  public void logout(HttpSession session, Model model) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      session.invalidate();
    }
    model.addAttribute("loginUser", loginUser);
  }
}
