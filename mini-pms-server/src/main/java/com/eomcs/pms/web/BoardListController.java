package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@RequestMapping("/board/list")
public class BoardListController implements Controller {

  BoardService boardService;

  public BoardListController(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=UTF-8");

    String keyword = request.getParameter("keyword");
    List<Board> list = boardService.list(keyword);
    request.setAttribute("list", list);
    return "/board/list.jsp";
  }
}
