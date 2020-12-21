package com.eomcs.pms.web;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public void detail(int no, Model model) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }
    model.addAttribute("board", board);
  }

  @RequestMapping("list")
  public void list(String keyword, Model model) throws Exception {
    model.addAttribute("list", boardService.list(keyword));
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
