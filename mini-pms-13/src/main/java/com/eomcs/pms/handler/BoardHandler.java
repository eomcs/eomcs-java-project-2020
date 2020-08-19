package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class BoardHandler {

  // 게시물 데이터
  static class Board {
    int no;
    String title;
    String content;
    String writer;
    Date registeredDate;
    int viewCount;
  }
  // 공통으로 사용할 값을 보관하는 변수는 스태틱 멤버(클래스 멤버)로 만든다.
  static final int LENGTH = 100;

  // 개별적으로 값을 보관해야 하는 변수는 인스턴스 멤버(non-static 멤버)로 만든다.
  Board[] list = new Board[LENGTH];
  int size = 0;

  public void add() {
    System.out.println("[게시물 등록]");

    Board board = new Board();
    board.no = Prompt.inputInt("번호? ");
    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");
    board.writer = Prompt.inputString("작성자? ");
    board.registeredDate = new Date(System.currentTimeMillis());
    board.viewCount = 0;

    this.list[this.size++] = board;

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시물 목록]");

    for (int i = 0; i < this.size; i++) {
      Board board = this.list[i];
      System.out.printf("%d, %s, %s, %s, %d\n",
          board.no,
          board.title,
          board.writer,
          board.registeredDate,
          board.viewCount);
    }
  }
}
