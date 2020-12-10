package com.eomcs.pms.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    BoardService boardService =
        (BoardService) ctx.getAttribute("boardService");

    response.setContentType("text/html;charset=UTF-8");

    try {
      String keyword = request.getParameter("keyword");
      List<Board> list = boardService.list(keyword);

      // 서비스 객체를 통해 가져온 게시물 목록을 JSP가 사용할 수 있도록
      // ServletRequest 보관소에 저장한다.
      request.setAttribute("list", list);

      // 어떤 JSP를 실행해야 하는 지 프론트 컨트롤러에게 알려준다.
      request.setAttribute("viewName", "/board/list.jsp");

    } catch (Exception e) {
      request.setAttribute("exception", e);
    }
  }
}
