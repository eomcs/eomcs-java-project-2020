package com.eomcs.lms.web;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

@Controller
public class BoardController {

  static Logger logger = LogManager.getLogger(BoardController.class);

  @Autowired
  BoardService boardService;

  public BoardController() {
    logger.debug("BoardController 생성됨!");
  }

  @RequestMapping("/board/form")
  public String form() throws Exception {
    return "/board/form.jsp";
  }

  @RequestMapping("/board/add")
  public String add(Board board) throws Exception {
    boardService.add(board);
    return "redirect:list";
  }

  @RequestMapping("/board/delete")
  public String delete(int no) throws Exception {
    if (boardService.delete(no) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("삭제할 게시물 번호가 유효하지 않습니다.");
    }
  }

  @RequestMapping("/board/detail")
  public String detail(int no, Map<String, Object> model) throws Exception {
    Board board = boardService.get(no);
    model.put("board", board);
    return "/board/detail.jsp";
  }

  @RequestMapping("/board/list")
  public String list(Map<String, Object> model) throws Exception {
    List<Board> boards = boardService.list();
    model.put("list", boards);
    return "/board/list.jsp";
  }

  @RequestMapping("/board/updateForm")
  public String updateForm(int no, Map<String, Object> model) throws Exception {
    model.put("board", boardService.get(no));
    return "/board/updateform.jsp";
  }

  @RequestMapping("/board/update")
  public String update(Board board) throws Exception {
    if (boardService.update(board) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("변경할 게시물 번호가 유효하지 않습니다.");
    }
  }
}
