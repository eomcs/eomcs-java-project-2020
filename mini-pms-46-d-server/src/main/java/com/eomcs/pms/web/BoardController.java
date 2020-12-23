package com.eomcs.pms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;

@Controller
@RequestMapping("/board")
@SessionAttributes("loginUser")
public class BoardController {

  @Autowired BoardService boardService;

  @GetMapping("form")
  public void form() {
    // 리턴 값이 void 이면,
    // JSP URL은 요청 URL과 같다.
    // 즉 "/board/form" 이다.
    // InternalResourceViewResolver는 이 URL 앞뒤에 접두사, 접미사를 붙여
    // 최종 JSP URL을 결정한다.
    // "/WEB-INF/jsp/" + "/board/form" + ".jsp" = /WEB-INF/jsp/board/form.jsp
    // => jsp 다음에 /가 두 개인 경우 한 개는 제거된다.
  }

  @PostMapping("add")
  public String add(
      Board board,
      @ModelAttribute("loginUser") Member loginUser) throws Exception {
    board.setWriter(loginUser);
    boardService.add(board);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    if (boardService.delete(no) == 0) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }
    return "redirect:list";
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {
    Board board = boardService.get(no);
    if (board == null) {
      throw new Exception("해당 번호의 게시글이 없습니다!");
    }
    model.addAttribute("board", board);
    // 최종 JSP URL: /WEB-INF/jsp/ + /board/detail + .jsp = /WEB-INF/jsp/board/detail.jsp
  }

  @GetMapping("list")
  public void list(String keyword, Model model) throws Exception {
    model.addAttribute("list", boardService.list(keyword));
    // 최종 JSP URL: /WEB-INF/jsp/ + /board/list + .jsp = /WEB-INF/jsp/board/list.jsp
  }

  @PostMapping("update")
  public String update(Board board) throws Exception {
    int count = boardService.update(board);
    if (count == 0) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }
    return "redirect:list";
  }
}
