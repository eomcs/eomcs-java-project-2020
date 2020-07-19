package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.lms.domain.Member;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>Bitcamp-LMS</title>");
    out.println(
        "<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css' integrity='sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh' crossorigin='anonymous'>");
    out.println("<style>");
    out.println("body {");
    out.println("  background-color: LightGray;");
    out.println("}");

    out.println("div.container {");
    out.println("  background: white;");
    out.println("  border: 1px solid gray;");
    out.println("  width: 600px;");
    out.println("}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-dark'>");
    out.println("<a class='navbar-brand' href='#'>Navbar</a>");
    out.println(
        "<button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarNav' aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'>");
    out.println("  <span class='navbar-toggler-icon'></span>");
    out.println("</button>");
    out.println("<div class='collapse navbar-collapse' id='navbarNav'>");
    out.println("  <ul class='navbar-nav mr-auto'>");
    out.println("    <li class='nav-item'>");
    out.println("      <a class='nav-link' href='../board/list'>게시글</span></a>");
    out.println("    </li>");
    out.println("    <li class='nav-item'>");
    out.println("      <a class='nav-link' href='../lesson/list'>수업</a>");
    out.println("    </li>");
    out.println("    <li class='nav-item'>");
    out.println("      <a class='nav-link' href='../member/list'>회원</a>");
    out.println("    </li>");
    out.println("    <li class='nav-item'>");
    out.println("      <a class='nav-link' href='../auth/login'>로그인</a>");
    out.println("    </li>");
    out.println("  </ul>");
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser != null) {
      out.printf("  <span class='navbar-text'>%s</span>\n", //
          loginUser.getName());
      out.println("  <a href='../auth/logout' class='btn btn-success btn-sm'>로그아웃</a>");
    } else {
      out.println("  <a href='../auth/login' class='btn btn-success btn-sm'>로그인</a>");
    }
    out.println("</div>");
    out.println("</nav>");
    out.println("<div class='container'>");
  }
}
