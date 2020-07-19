package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;
import com.eomcs.util.Component;
import com.eomcs.util.Prompt;
import com.eomcs.util.RequestMapping;

@Component
public class BoardAddServlet {

  BoardService boardService;

  public BoardAddServlet(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/add")
  public void service(Scanner in, PrintStream out) throws Exception {
    Board board = new Board();
    board.setTitle(Prompt.getString(in, out, "제목? "));
    boardService.add(board);
    out.println("새 게시글을 등록했습니다.");
  }
}
