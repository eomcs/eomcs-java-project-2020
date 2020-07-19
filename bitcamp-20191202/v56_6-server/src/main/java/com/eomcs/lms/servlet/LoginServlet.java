package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();

      request.getRequestDispatcher("/header").include(request, response);

      out.println("<h1>로그인</h1>");
      out.println("<form action='login' method='post'>");
      out.printf("이메일: <input name='email' type='email' value='%s'>\n", email);
      out.println("<input type='checkbox' name='saveEmail'> 이메일 저장해두기<br>");
      out.println("암호: <input name='password' type='password'><br>");
      out.println("<button>로그인</button>");
      out.println("</form>");

      request.getRequestDispatcher("/footer").include(request, response);

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();

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
      response.addCookie(cookie);

      Member member = memberService.get(email, password);

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      if (member != null) {
        out.println("<meta http-equiv='refresh' content='2;url=../index.html'>");
      } else {
        out.println("<meta http-equiv='refresh' content='2;url=login'>");
      }
      out.println("<title>로그인</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>로그인 결과</h1>");

      if (member != null) {
        out.printf("<p>'%s'님 환영합니다.</p>\n", member.getName());
        request.getSession().setAttribute("loginUser", member);
      } else {
        out.println("<p>사용자 정보가 유효하지 않습니다.</p>");
      }

      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "login");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
