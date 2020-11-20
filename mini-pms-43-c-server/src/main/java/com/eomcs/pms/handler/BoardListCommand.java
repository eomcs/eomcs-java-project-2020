package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@CommandAnno("/board/list")
public class BoardListCommand implements Command {

  BoardService boardService;

  public BoardListCommand(BoardService boardService) {
    this.boardService = boardService;
  }


  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();

    try {
      out.println("[게시물 목록]");

      List<Board> list = boardService.list();

      out.println("번호, 제목, 작성자, 등록일, 조회수");

      for (Board board : list) {
        out.printf("%d, %s, %s, %s, %d\n",
            board.getNo(),
            board.getTitle(),
            board.getWriter().getName(),
            board.getRegisteredDate(),
            board.getViewCount());
      }
    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }

}
