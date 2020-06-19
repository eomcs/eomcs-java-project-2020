package com.eomcs.pms.handler;

import com.eomcs.util.Prompt;

public class BoardHandler {

  static class Board {
    int no;
    String title;
    String content;
  }

  static final int LENGTH = 100;
  static Board[] list = new Board[LENGTH];
  static int size = 0;

  public static void add() {
    System.out.println("회원 등록!");

    Board board = new Board();
    board.no = Prompt.inputInt("번호? ");
    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");

    list[size++] = board;
  }
}
