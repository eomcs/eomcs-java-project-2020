package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@WebServlet("/project/form")
public class ProjectAddFormServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    MemberService memberService =
        (MemberService) ctx.getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트생성</title></head>");
    out.println("<body>");

    try {
      out.println("<h1>프로젝트 생성</h1>");

      out.println("<form action='add' method='post'>");
      out.println("프로젝트명: <input type='text' name='title'><br>");
      out.println("내용: <textarea name='content' rows='10' cols='60'></textarea><br>");
      out.println("기간: <input type='date' name='startDate'> ~ ");
      out.println("      <input type='date' name='endDate'><br>");
      out.println("팀원: <br>");
      out.println("<ul>");

      List<Member> members = memberService.list();
      for (Member m : members) {
        out.printf("  <li><input type='checkbox' name='members' value='%d'>%s</li>\n",
            m.getNo(),
            m.getName());
      }

      out.println("</ul><br>");
      out.println("<button>생성</button>");
      out.println("</form>");

    } catch (Exception e) {
      out.println("<h2>작업 처리 중 오류 발생!</h2>");
      out.printf("<pre>%s</pre>\n", e.getMessage());

      StringWriter errOut = new StringWriter();
      e.printStackTrace(new PrintWriter(errOut));
      out.println("<h3>상세 오류 내용</h3>");
      out.printf("<pre>%s</pre>\n", errOut.toString());
    }

    out.println("</body>");
    out.println("</html>");
  }
}
