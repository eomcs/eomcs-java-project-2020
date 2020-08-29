package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Prompt;

public class BoardHandler {

  // Board 타입의 객체 목록을 저장할 ArrayList 객체를 준비한다.
  // 제네릭 문법으로 항목의 타입을 지정한다.
  ArrayList<Board> boardList = new ArrayList<>();

  public void add() {
    System.out.println("[게시물 등록]");

    Board board = new Board();
    board.setNo(Prompt.inputInt("번호? "));
    board.setTitle(Prompt.inputString("제목? "));
    board.setContent(Prompt.inputString("내용? "));
    board.setWriter(Prompt.inputString("작성자? "));
    board.setRegisteredDate(new Date(System.currentTimeMillis()));
    board.setViewCount(0);

    // 제네릭 문법에 따라 add()를 호출할 때 넘겨줄 수 있는 값은 
    // Board 또는 그 하위 타입의 인스턴스만 가능하다.
    // 다른 타입은 불가능하다.
    boardList.add(board);

    System.out.println("게시글을 등록하였습니다.");
  }

  public void list() {
    System.out.println("[게시물 목록]");

    // 제네릭 문법에 따라 리턴 타입이 'Board[]' 이기 때문에
    // 따로 형변환 할 필요가 없다.
    // 대신 Board[] 배열을 리턴해 달라는 의미로 배열의 타입 정보를 넘긴다.
    Board[] boards = boardList.toArray(Board[].class);

    for (Board board : boards) {
      System.out.printf("%d, %s, %s, %s, %d\n",
          board.getNo(),
          board.getTitle(),
          board.getWriter(),
          board.getRegisteredDate(),
          board.getViewCount());
    }
  }
}
