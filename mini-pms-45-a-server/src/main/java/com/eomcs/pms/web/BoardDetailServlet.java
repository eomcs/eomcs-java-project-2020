package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    BoardService boardService =
        (BoardService) ctx.getAttribute("boardService");

    response.setContentType("text/html;charset=UTF-8");

    try {
      int no = Integer.parseInt(request.getParameter("no"));
      Board board = boardService.get(no);
      request.setAttribute("board", board);
      request.getRequestDispatcher("/board/detail.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
