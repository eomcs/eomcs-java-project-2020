package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;
import com.eomcs.util.Prompt;

// Command 규칙에 따라 클래스를 정의한다.
public class BoardAddCommand implements Command {

  BoardService boardService;

  public BoardAddCommand(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    try {
      out.println("[게시물 등록]");

      Board board = new Board();
      board.setTitle(Prompt.inputString("제목? ", out, in));
      board.setContent(Prompt.inputString("내용? ", out, in));

      Member loginUser = (Member) context.get("loginUser");
      board.setWriter(loginUser);

      boardService.add(board);

      out.println("게시글을 등록하였습니다.");

    } catch(Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
