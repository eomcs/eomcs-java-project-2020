package com.eomcs.lms.handler;

import com.eomcs.lms.dao.BoardDao;
import com.eomcs.util.Prompt;

// "/board/delete" 명령 처리
public class BoardDeleteCommand implements Command {

  Prompt prompt;
  BoardDao boardDao;

  public BoardDeleteCommand(BoardDao boardDao, Prompt prompt) {
    this.boardDao = boardDao;
    this.prompt = prompt;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");
      boardDao.delete(no);
      System.out.println("게시글을 삭제했습니다.");

    } catch (Exception e) {
      System.out.println("삭제 실패!");
    }
  }
}


