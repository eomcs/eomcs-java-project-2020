package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

// "/board/list" 명령어 처리
public class BoardListCommand implements Command {

  BoardDao boardDao;

  public BoardListCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void execute() {
    try {
      List<Board> boards = boardDao.findAll();
      for (Board board : boards) {
        System.out.printf("%d, %s, %s, %d\n", //
            board.getNo(), //
            board.getTitle(), //
            board.getDate(), //
            board.getViewCount() //
        );
      }
    } catch (Exception e) {
      System.out.println("목록 조회 실패!");
    }
  }


}


