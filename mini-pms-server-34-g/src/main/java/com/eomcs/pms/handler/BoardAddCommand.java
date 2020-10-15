package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

// Command 규칙에 따라 클래스를 정의한다.
public class BoardAddCommand implements Command {

  List<Board> boardList;

  public BoardAddCommand(List<Board> list) {
    this.boardList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[게시물 등록]");

      Board board = new Board();
      board.setNo(Prompt.inputInt("번호? ", out, in));
      board.setTitle(Prompt.inputString("제목? ", out, in));
      board.setContent(Prompt.inputString("내용? ", out, in));
      board.setWriter(Prompt.inputString("작성자? ", out, in));
      board.setRegisteredDate(new Date(System.currentTimeMillis()));
      board.setViewCount(0);

      boardList.add(board);

      out.println("게시글을 등록하였습니다.");

    } catch(Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
