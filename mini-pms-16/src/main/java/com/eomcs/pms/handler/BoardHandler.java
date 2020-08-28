package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Prompt;

public class BoardHandler {

  // Board 객체 목록을 저장할 ArrayList 객체를 준비한다.
  ArrayList boardList = new ArrayList();

  public void add() {
    System.out.println("[게시물 등록]");

    Board board = new Board();
    board.setNo(Prompt.inputInt("번호? "));
    board.setTitle(Prompt.inputString("제목? "));
    board.setContent(Prompt.inputString("내용? "));
    board.setWriter(Prompt.inputString("작성자? "));
    board.setRegisteredDate(new Date(System.currentTimeMillis()));
    board.setViewCount(0);

    boardList.add(board);

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시물 목록]");

    Object[] boards = boardList.toArray();

    for (Object obj : boards) {
      Board board = (Board) obj;
      System.out.printf("%d, %s, %s, %s, %d\n",
          board.getNo(),
          board.getTitle(),
          board.getWriter(),
          board.getRegisteredDate(),
          board.getViewCount());
    }
  }
}
