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
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
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
      BoardService boardService = iocContainer.getBean(BoardService.class);

      int no = Integer.parseInt(request.getParameter("no"));
      Board board = boardService.get(no);

      request.getRequestDispatcher("/header").include(request, response);

      out.println("<h1>게시물 상세정보</h1>");
      if (board != null) {
        out.printf("번호: %d<br>\n", board.getNo());
        out.printf("제목: %s<br>\n", board.getTitle());
        out.printf("등록일: %s<br>\n", board.getDate());
        out.printf("조회수: %d<br>\n", board.getViewCount());
        out.printf("<p><a href='delete?no=%d'>삭제</a> \n", //
            board.getNo());
        out.printf("<a href='update?no=%d'>변경</a></p>\n", //
            board.getNo());
      } else {
        out.println("<p>해당 번호의 게시물이 없습니다.</p>");
      }

      request.getRequestDispatcher("/footer").include(request, response);

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
