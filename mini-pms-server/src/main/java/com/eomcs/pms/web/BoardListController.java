package com.eomcs.pms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.eomcs.pms.service.BoardService;

@Controller
public class BoardListController {

  BoardService boardService;

  public BoardListController(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/list")
  public ModelAndView execute(String keyword) throws Exception {

    ModelAndView mv = new ModelAndView();
    mv.addObject("list", boardService.list(keyword));
    mv.setViewName("/board/list.jsp");

    return mv;
  }
}
