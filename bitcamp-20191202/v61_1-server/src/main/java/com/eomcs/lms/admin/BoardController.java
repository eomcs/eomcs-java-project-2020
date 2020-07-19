package com.eomcs.lms.admin;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

  static Logger logger = LogManager.getLogger(BoardController.class);

  @Autowired
  BoardService boardService;

  public BoardController() {
    logger.debug("BoardController 생성됨!");
  }

  @GetMapping("form")
  public void form() throws Exception {}

  @PostMapping("add")
  public String add(Board board) throws Exception {
    boardService.add(board);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    if (boardService.delete(no) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("삭제할 게시물 번호가 유효하지 않습니다.");
    }
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {
    Board board = boardService.get(no);
    model.addAttribute("board", board);
  }

  @GetMapping("list")
  public void list(Model model) throws Exception {
    List<Board> boards = boardService.list();
    model.addAttribute("list", boards);
  }

  @GetMapping("updateForm")
  public void updateForm(int no, Model model) throws Exception {
    model.addAttribute("board", boardService.get(no));
  }

  @PostMapping("update")
  public String update(Board board) throws Exception {
    if (boardService.update(board) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("변경할 게시물 번호가 유효하지 않습니다.");
    }
  }
}
