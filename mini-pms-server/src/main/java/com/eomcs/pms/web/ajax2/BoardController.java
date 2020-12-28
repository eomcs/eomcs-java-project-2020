package com.eomcs.pms.web.ajax2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.google.gson.Gson;

@Controller("ajax2.boardController")
@RequestMapping("/ajax2/board")
@SessionAttributes("loginUser")
public class BoardController {

  @Autowired BoardService boardService;

  @GetMapping(value = "detail", produces = "text/html;charset=UTF-8")
  @ResponseBody
  public String detail(int no, Model model) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }
    return new Gson().toJson(board);
  }

}
