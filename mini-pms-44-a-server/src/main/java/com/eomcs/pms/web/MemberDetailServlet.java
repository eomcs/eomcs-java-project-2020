package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@WebServlet("/member/detail")
public class MemberDetailServlet extends HttpServlet {
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
    out.println("<head><title>회원상세정보</title></head>");
    out.println("<body>");
    try {
      out.println("<h1>회원 상세 정보</h1>");

      int no = Integer.parseInt(request.getParameter("no"));

      Member member = memberService.get(no);

      if (member == null) {
        out.println("<p>해당 번호의 회원이 없습니다.</p>");
        return;
      }

      out.println("<form action='updatePhoto' method='post' enctype='multipart/form-data'>");
      out.printf("<input type='hidden' name='no' value='%d'><br>\n",
          member.getNo());
      out.printf("<a href='../upload/%s'>\n<img src='../upload/%1$s_120x120.jpg'></a><br>\n",
          member.getPhoto());
      out.println("<input type='file' name='photo'>");
      out.println("<button>변경</button>");
      out.println("</form>");
      out.println("<br>");

      out.println("<form action='update' method='post'>");
      out.printf("번호: <input type='text' name='no' value='%d' readonly><br>\n",
          member.getNo());
      out.printf("이름: <input type='text' name='name' value='%s'><br>\n",
          member.getName());
      out.printf("이메일: <input type='email' name='email' value='%s'><br>\n",
          member.getEmail());
      out.println("암호: <input type='password' name='password'><br>");
      out.printf("전화: <input type='tel' name='tel' value='%s'><br>\n",
          member.getTel());
      out.printf("등록일: %s<br>\n", member.getRegisteredDate());
      out.println("<button>변경</button>");
      out.printf("<a href='delete?no=%d'>[삭제]</a>\n ",
          member.getNo());
      out.println("<a href='list'>[목록]</a> ");
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
