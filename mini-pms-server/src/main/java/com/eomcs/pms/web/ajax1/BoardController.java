package com.eomcs.pms.web.ajax1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@Controller("ajax1.boardController")
@RequestMapping("/ajax1/board")
@SessionAttributes("loginUser")
public class BoardController {

  @Autowired BoardService boardService;

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }
    model.addAttribute("board", board);
    // 최종 JSP URL: ajax1/board/detail
  }

}
