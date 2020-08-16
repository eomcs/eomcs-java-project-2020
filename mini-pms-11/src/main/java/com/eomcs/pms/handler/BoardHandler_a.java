package com.eomcs.pms.handler;

import com.eomcs.util.Prompt;

public class BoardHandler_a {

  // 게시물 데이터
  static class Board {
    int no;
    String title;
    String content;
    String writer;
  }
  static final int LENGTH = 100;
  static Board[] list = new Board[LENGTH]; 
  static int size = 0;

  public static void add() {
    System.out.println("[게시물 등록]");
    
    Board board = new Board();
    board.no = Prompt.inputInt("번호? ");
    board.title = Prompt.inputString("제목? ");
    board.content = Prompt.inputString("내용? ");
    board.writer = Prompt.inputString("작성자? ");
    
    list[size++] = board;
    
    System.out.println("게시글을 등록하였습니다.");
  }
}
