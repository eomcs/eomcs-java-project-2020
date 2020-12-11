package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@Controller
public class BoardListController {

  BoardService boardService;

  public BoardListController(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/list")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=UTF-8");

    String keyword = request.getParameter("keyword");
    List<Board> list = boardService.list(keyword);
    request.setAttribute("list", list);
    return "/board/list.jsp";
  }
}
