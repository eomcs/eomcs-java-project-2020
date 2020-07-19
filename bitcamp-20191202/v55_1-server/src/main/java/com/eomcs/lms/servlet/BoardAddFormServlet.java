package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

// @Component // Spring IoC 가 관리
@WebServlet("/board/addForm") // 서블릿 컨테이너가 이 객체를 관리한다.
// => @WebServlet(서블릿경로)
// => 서블릿 컨테이너는 이 애노테이션이 붙은 객체를 생성하여 보관한다.
// => 클라이언트가 '서블릿경로'에 해당하는 URL로 요청하면,
// 서블릿 컨테이너는 그 서블릿경로로 저장된 객체를 실행한다.
//
// JavaEE Servlet 기술에 따라 클라이언트 요청을 처리하는 객체를 만드는 방법:
// => javax.servlet.Servlet 인터페이스를 구현하라!
// => 또는 이 인터페이스를 미리 구현한 javax.servlet.GenericServlet을 상속 받아라!
//
public class BoardAddFormServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html;charset=UTF-8");
    PrintWriter out = res.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>게시글 입력</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 입력</h1>");
    out.println("<form action='add'>");
    out.println("내용:<br>");
    out.println("<textarea name='title' rows='5' cols='60'></textarea><br>");
    out.println("<button>제출</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }
}
