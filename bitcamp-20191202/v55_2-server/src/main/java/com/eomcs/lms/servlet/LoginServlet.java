package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;

@WebServlet("/auth/login")
public class LoginServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    try {
      res.setContentType("text/html;charset=UTF-8");
      PrintWriter out = res.getWriter();

      ServletContext servletContext = req.getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      MemberService memberService = iocContainer.getBean(MemberService.class);

      String email = req.getParameter("email");
      String password = req.getParameter("password");

      Member member = memberService.get(email, password);

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      if (member != null) {
        out.println("<meta http-equiv='refresh' content='2;url=../board/list'>");
      } else {
        out.println("<meta http-equiv='refresh' content='2;url=loginForm'>");
      }
      out.println("<title>로그인</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>로그인 결과</h1>");

      if (member != null) {
        out.printf("<p>'%s'님 환영합니다.</p>\n", member.getName());
      } else {
        out.println("<p>사용자 정보가 유효하지 않습니다.</p>");
      }

      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
