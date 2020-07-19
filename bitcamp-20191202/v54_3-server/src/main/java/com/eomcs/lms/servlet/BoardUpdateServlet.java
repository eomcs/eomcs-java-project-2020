package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;
import com.eomcs.util.RequestMapping;

@Component
public class BoardUpdateServlet {

  BoardService boardService;

  public BoardUpdateServlet(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/update")
  public void service(Map<String, String> params, PrintWriter out) throws Exception {
    Board board = new Board();
    board.setNo(Integer.parseInt(params.get("no")));
    board.setTitle(params.get("title"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='2;url=/board/list'>");
    out.println("<title>게시글 변경</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 변경 결과</h1>");

    if (boardService.update(board) > 0) { // 변경했다면,
      out.println("<p>게시글을 변경했습니다.</p>");

    } else {
      out.println("<p>해당 번호의 게시글이 없습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
