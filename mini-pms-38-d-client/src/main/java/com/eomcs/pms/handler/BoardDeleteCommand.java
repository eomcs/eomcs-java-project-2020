package com.eomcs.pms.handler;

import com.eomcs.pms.dao.BoardDao;
import com.eomcs.util.Prompt;

public class BoardDeleteCommand implements Command {

  BoardDao boardDao;

  public BoardDeleteCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void execute() {
    System.out.println("[게시물 삭제]");
    int no = Prompt.inputInt("번호? ");

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("게시글 삭제를 취소하였습니다.");
      return;
    }

    try {
      int count = boardDao.delete(no);
      if (count == 0) {
        System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
      } else {
        System.out.println("게시글을 삭제하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("게시글 삭제 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
