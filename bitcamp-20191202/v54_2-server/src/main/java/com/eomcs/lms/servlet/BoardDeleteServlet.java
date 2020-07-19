package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.service.BoardService;
import com.eomcs.util.RequestMapping;

@Component
public class BoardDeleteServlet {

  BoardService boardService;

  public BoardDeleteServlet(BoardService boardService) {
    this.boardService = boardService;
  }

  @RequestMapping("/board/delete")
  public void service(Map<String, String> params, PrintStream out) throws Exception {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='2;url=/board/list'>");
    out.println("<title>게시글 삭제</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 삭제 결과</h1>");

    int no = Integer.parseInt(params.get("no"));
    if (boardService.delete(no) > 0) { // 삭제했다면,
      out.println("<p>게시글을 삭제했습니다.</p>");
    } else {
      out.println("<p>해당 번호의 게시물이 없습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
