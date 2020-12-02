package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.pms.domain.Member;

@WebServlet("/auth/loginUser")
public class LoginUserServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    HttpSession session = request.getSession();

    // 클라이언트로 데이터를 출력할 때 사용할 스트림 준비
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head><title>로그인사용자</title></head>");
    out.println("<body>");

    out.println("<h1>로그인 사용자</h1>");

    Member member = (Member) session.getAttribute("loginUser");
    if (member == null) {
      out.println("<p>로그인 하지 않았습니다!</p>");
      return;
    }
    out.printf("사용자 번호: %d<br>\n", member.getNo());
    out.printf("이름: %s<br>\n", member.getName());
    out.printf("이메일: %s<br>\n", member.getEmail());
    out.printf("사진: %s<br>\n", member.getPhoto());
    out.printf("전화: %s<br>\n", member.getTel());
    out.printf("등록일: %s<br>\n", member.getRegisteredDate());

    out.println("</body></html>");
  }
}
