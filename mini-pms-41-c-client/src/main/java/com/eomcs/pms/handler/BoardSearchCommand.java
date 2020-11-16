package com.eomcs.pms.handler;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardSearchCommand implements Command {

  BoardDao boardDao;

  public BoardSearchCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[게시물 검색]");

    try {
      String keyword = Prompt.inputString("검색어? ");

      System.out.println("번호, 제목, 작성자, 등록일, 조회수");
      List<Board> list = boardDao.findAll("%" + keyword + "%");
      for (Board board : list) {
        System.out.printf("%d, %s, %s, %s, %d\n",
            board.getNo(),
            board.getTitle(),
            board.getWriter().getName(),
            board.getRegisteredDate(),
            board.getViewCount());
      }


    } catch (Exception e) {
      System.out.println("게시글 검색 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
