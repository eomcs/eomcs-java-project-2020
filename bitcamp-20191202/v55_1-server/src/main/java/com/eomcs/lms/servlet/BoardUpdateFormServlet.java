package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

@WebServlet("/board/updateForm")
public class BoardUpdateFormServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    try {
      res.setContentType("text/html;charset=UTF-8");
      PrintWriter out = res.getWriter();

      ServletContext servletContext = req.getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      BoardService boardService = iocContainer.getBean(BoardService.class);

      int no = Integer.parseInt(req.getParameter("no"));

      Board board = boardService.get(no);

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>게시글 변경</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>게시물 변경</h1>");

      if (board == null) {
        out.println("<p>해당 번호의 게시글이 없습니다.</p>");
      } else {
        out.println("<form action='update'>");
        out.printf("번호: <input name='no' readonly type='text' value='%d'><br>\n", //
            board.getNo());
        out.println("내용:<br>");
        out.printf("<textarea name='title' rows='5' cols='60'>%s</textarea><br>\n", //
            board.getTitle());
        out.printf("등록일: %s<br>\n", //
            board.getDate());
        out.printf("조회수: %d<br>\n", //
            board.getViewCount());
        out.println("<button>변경</button>");
        out.println("</form>");
      }
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
