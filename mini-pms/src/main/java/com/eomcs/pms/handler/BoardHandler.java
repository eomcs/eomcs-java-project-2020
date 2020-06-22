package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class BoardHandler {

  static class Board {
    int no;
    String title;
    String content;
    String writer;
    Date createdDate;
    int viewCount;
  }

  static final int LENGTH = 100;
  static Board[] list = new Board[LENGTH];
  static int size = 0;

  public static void add() {
    System.out.println("[새 게시글]");

    Board board = new Board();
    board.no = Prompt.inputInt("번호? ");
    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");
    board.writer = Prompt.inputString("작성자? ");
    board.createdDate = new Date(System.currentTimeMillis());

    list[size++] = board;
  }

  public static void list() {
    System.out.println("[게시글 목록]");

    for (int i = 0; i < size; i++) {
      // 번호, 제목, 등록일, 작성자, 조회수
      System.out.printf("%d, %s, %s, %s, %d\n", // 출력 형식 지정
          list[i].no, // 게시물 번호
          list[i].title, // 제목
          list[i].createdDate, // 등록일
          list[i].writer, // 작성자
          list[i].viewCount // 조회수
          );
    }
  }
}
