package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;

@WebServlet("/member/detail")
public class MemberDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();

      ServletContext servletContext = getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      MemberService memberService = iocContainer.getBean(MemberService.class);

      int no = Integer.parseInt(request.getParameter("no"));

      Member member = memberService.get(no);

      request.getRequestDispatcher("/header").include(request, response);

      out.println("<h1>회원 상세정보</h1>");

      if (member != null) {
        out.println("<form action='update' method='post'>");
        out.printf("번호: <input name='no' type='text' readonly value='%d'><br>\n", //
            member.getNo());
        out.printf("이름: <input name='name' type='text' value='%s'><br>\n", //
            member.getName());
        out.printf("이메일: <input name='email' type='email' value='%s'><br>\n", //
            member.getEmail());
        out.println("암호: <input name='password' type='password'><br>");
        out.printf("사진: <input name='photo' type='text' value='%s'><br>\n", //
            member.getPhoto());
        out.printf("전화: <input name='tel' type='tel' value='%s'><br>\n", //
            member.getTel());
        out.println("<p><button>변경</button>");
        out.printf("<a href='delete?no=%d'>삭제</a></p>\n", //
            member.getNo());
        out.println("</form>");
      } else {
        out.println("<p>해당 번호의 회원이 없습니다.</p>");
      }

      request.getRequestDispatcher("/footer").include(request, response);

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
