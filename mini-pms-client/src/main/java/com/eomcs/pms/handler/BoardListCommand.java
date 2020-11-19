package com.eomcs.pms.handler;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

public class BoardListCommand implements Command {

  BoardService boardService;

  public BoardListCommand(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[게시물 목록]");
    try {
      System.out.println("번호, 제목, 작성자, 등록일, 조회수");
      List<Board> list = boardService.list();
      for (Board board : list) {
        System.out.printf("%d, %s, %s, %s, %d\n",
            board.getNo(),
            board.getTitle(),
            board.getWriter().getName(),
            board.getRegisteredDate(),
            board.getViewCount());
      }
    } catch (Exception e) {
      System.out.println("게시글 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }

}
