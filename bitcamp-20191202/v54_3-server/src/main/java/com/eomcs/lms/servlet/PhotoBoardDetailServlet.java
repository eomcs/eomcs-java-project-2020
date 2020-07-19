package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardDetailServlet {

  PhotoBoardService photoBoardService;

  public PhotoBoardDetailServlet( //
      PhotoBoardService photoBoardService) {
    this.photoBoardService = photoBoardService;
  }

  @RequestMapping("/photoboard/detail")
  public void service(Map<String, String> params, PrintWriter out) throws Exception {
    int no = Integer.parseInt(params.get("no"));

    PhotoBoard photoBoard = photoBoardService.get(no);

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>사진 상세정보</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>사진 상세정보</h1>");

    if (photoBoard != null) {
      out.println("<form action='/photoboard/update'>");
      out.printf("번호: <input name='no' type='text' readonly value='%d'><br>\n", //
          photoBoard.getNo());
      out.println("내용:<br>");
      out.printf("<textarea name='title' rows='5' cols='60'>%s</textarea><br>\n", //
          photoBoard.getTitle());
      out.printf("등록일: %s<br>\n", photoBoard.getCreatedDate());
      out.printf("조회수: %d<br>\n", photoBoard.getViewCount());
      out.printf("수업: %s<br>\n", photoBoard.getLesson().getTitle());
      out.println("<hr>");
      out.println("사진 파일:<br>");
      out.println("<ul>\n");
      for (PhotoFile photoFile : photoBoard.getFiles()) {
        out.printf("  <li>%s</li>\n", photoFile.getFilepath());
      }
      out.println("</ul>");

      out.println("사진: <input name='photo1' type='file'><br>");
      out.println("사진: <input name='photo2' type='file'><br>");
      out.println("사진: <input name='photo3' type='file'><br>");
      out.println("사진: <input name='photo4' type='file'><br>");
      out.println("사진: <input name='photo5' type='file'><br>");

      out.println("<p><button>변경</button>");
      out.printf("<a href='/photoboard/delete?no=%d&lessonNo=%d'>삭제</a></p>\n", //
          photoBoard.getNo(), //
          photoBoard.getLesson().getNo());
      out.println("</form>");

    } else {
      out.println("<p>해당 번호의 사진 게시글이 없습니다.</p>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}
