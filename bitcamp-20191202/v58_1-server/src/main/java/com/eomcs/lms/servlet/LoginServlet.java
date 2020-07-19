package com.eomcs.lms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
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
      request.setAttribute("email", email);
      request.setAttribute("viewUrl", "/auth/form.jsp");

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      ServletContext servletContext = getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      MemberService memberService = iocContainer.getBean(MemberService.class);

      String email = request.getParameter("email");
      String password = request.getParameter("password");
      String saveEmail = request.getParameter("saveEmail");

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

      request.setAttribute("viewUrl", "/auth/login.jsp");

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "login");
    }
  }
}
