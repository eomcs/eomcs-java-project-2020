package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.LinkedList;
import com.eomcs.util.Prompt;

public class BoardHandler {

  LinkedList<Board> boardList = new LinkedList<>();

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

    for (int i = 0; i < boardList.size(); i++) {
      Board board = boardList.get(i);
      System.out.printf("%d, %s, %s, %s, %d\n",
          board.getNo(),
          board.getTitle(),
          board.getWriter(),
          board.getRegisteredDate(),
          board.getViewCount());
    }
  }

  public void detail() {
    System.out.println("[게시물 상세보기]");
    int no = Prompt.inputInt("번호? ");
    Board board = findByNo(no);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    board.setViewCount(board.getViewCount() + 1);

    System.out.printf("제목: %s\n", board.getTitle());
    System.out.printf("내용: %s\n", board.getContent());
    System.out.printf("작성자: %s\n", board.getWriter());
    System.out.printf("등록일: %s\n", board.getRegisteredDate());
    System.out.printf("조회수: %d\n", board.getViewCount());
  }

  public void update() {
    System.out.println("[게시물 변경]");
    int no = Prompt.inputInt("번호? ");
    Board board = findByNo(no);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String title = Prompt.inputString(
        String.format("제목(%s)? ", board.getTitle()));
    String content = Prompt.inputString(
        String.format("내용(%s)? ", board.getContent()));
    String writer = Prompt.inputString(
        String.format("작성자(%s)? ", board.getWriter()));

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    board.setTitle(title);
    board.setContent(content);
    board.setWriter(writer);
    System.out.println("게시글을 변경하였습니다.");
  }

  public void delete() {
    System.out.println("[게시물 삭제]");
    int no = Prompt.inputInt("번호? ");
    int index = indexOf(no);

    if (index == -1) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("게시글 삭제를 취소하였습니다.");
      return;
    }

    boardList.remove(index);
    System.out.println("게시글을 삭제하였습니다.");
  }

  private Board findByNo(int no) {
    for (int i = 0; i < boardList.size(); i++) {
      Board board = boardList.get(i);
      if (board.getNo() == no) {
        return board;
      }
    }
    return null;
  }

  private int indexOf(int no) {
    for (int i = 0; i < boardList.size(); i++) {
      Board board = boardList.get(i);
      if (board.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
