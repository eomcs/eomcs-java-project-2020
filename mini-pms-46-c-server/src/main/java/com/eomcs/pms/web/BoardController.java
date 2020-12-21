package com.eomcs.pms.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Autowired BoardService boardService;

  @RequestMapping("add")
  public String add(Board board, HttpSession session) throws Exception {
    Member loginUser = (Member) session.getAttribute("loginUser");
    board.setWriter(loginUser);
    boardService.add(board);
    return "redirect:list";
  }

  @RequestMapping("delete")
  public String delete(int no) throws Exception {
    if (boardService.delete(no) == 0) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }
    return "redirect:list";
  }

  @RequestMapping("detail")
  public ModelAndView detail(int no) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("board", board);
    mv.setViewName("/board/detail.jsp");

    return mv;
  }

  @RequestMapping("list")
  public ModelAndView list(String keyword) throws Exception {

    ModelAndView mv = new ModelAndView();
    mv.addObject("list", boardService.list(keyword));
    mv.setViewName("/board/list.jsp");

    return mv;
  }

  @RequestMapping("update")
  public String update(Board board) throws Exception {
    int count = boardService.update(board);
    if (count == 0) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }
    return "redirect:list";
  }
}
