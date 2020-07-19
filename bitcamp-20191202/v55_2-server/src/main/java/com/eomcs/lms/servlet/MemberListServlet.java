package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
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

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("  <meta charset='UTF-8'>");
      out.println("  <title>회원 목록</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("  <h1>회원</h1>");
      out.println("  <a href='addForm'>새 회원</a><br>");
      out.println("  <table border='1'>");
      out.println("  <tr>");
      out.println("    <th>번호</th>");
      out.println("    <th>이름</th>");
      out.println("    <th>이메일</th>");
      out.println("    <th>전화</th>");
      out.println("    <th>등록일</th>");
      out.println("  </tr>");

      List<Member> members = memberService.list();
      for (Member m : members) {
        out.printf("  <tr>"//
            + "<td>%d</td> "//
            + "<td><a href='detail?no=%d'>%s</a></td> "//
            + "<td>%s</td> "//
            + "<td>%s</td>"//
            + "<td>%s</td>"//
            + "</tr>\n", //
            m.getNo(), //
            m.getNo(), //
            m.getName(), //
            m.getEmail(), //
            m.getTel(), //
            m.getRegisteredDate() //
        );
      }
      out.println("</table>");

      out.println("<hr>");

      out.println("<form action='search'>");
      out.println("검색어: <input name='keyword' type='text'>");
      out.println("<button>검색</button>");
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
