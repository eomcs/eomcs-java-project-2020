package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;
import com.eomcs.util.Component;
import com.eomcs.util.Prompt;

@Component("/board/update")
public class BoardUpdateServlet implements Servlet {

  BoardService boardService;

  public BoardUpdateServlet(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {

    int no = Prompt.getInt(in, out, "번호? ");

    Board old = boardService.get(no);
    if (old == null) {
      out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    Board board = new Board();

    board.setTitle(Prompt.getString(//
        in, //
        out, //
        String.format("제목(%s)? ", old.getTitle()), //
        old.getTitle()));

    board.setNo(no);

    if (boardService.update(board) > 0) { // 변경했다면,
      out.println("게시글을 변경했습니다.");

    } else {
      out.println("게시글 변경에 실패했습니다.");
    }
  }
}
