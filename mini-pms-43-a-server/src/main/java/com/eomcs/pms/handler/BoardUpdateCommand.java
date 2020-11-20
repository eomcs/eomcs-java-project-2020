package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;
import com.eomcs.util.Prompt;

public class BoardUpdateCommand implements Command {

  BoardService boardService;

  public BoardUpdateCommand(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    try {
      out.println("[게시물 변경]");
      int no = Prompt.inputInt("번호? ", out, in);

      Board board = boardService.get(no);

      if (board == null) {
        out.println("해당 번호의 게시글이 없습니다.");
        return;
      }

      board.setTitle(Prompt.inputString(
          String.format("제목(%s)? ", board.getTitle()), out, in));
      board.setContent(Prompt.inputString(
          String.format("내용(%s)? ", board.getContent()), out, in));

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("게시글 변경을 취소하였습니다.");
        return;
      }

      boardService.update(board);

      out.println("게시글을 변경하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
