package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;

@WebServlet("/photoboard/add")
public class PhotoBoardAddServlet extends GenericServlet {
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
      LessonService lessonService = iocContainer.getBean(LessonService.class);
      PhotoBoardService photoBoardService = iocContainer.getBean(PhotoBoardService.class);

      int lessonNo = Integer.parseInt(req.getParameter("lessonNo"));
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='refresh'" //
          + " content='2;url=list?lessonNo=" + lessonNo + "'>");
      out.println("<title>사진 입력</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>사진 입력 결과</h1>");

      try {
        Lesson lesson = lessonService.get(lessonNo);
        if (lesson == null) {
          throw new Exception("수업 번호가 유효하지 않습니다.");
        }

        PhotoBoard photoBoard = new PhotoBoard();
        photoBoard.setTitle(req.getParameter("title"));
        photoBoard.setLesson(lesson);

        ArrayList<PhotoFile> photoFiles = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
          String filepath = req.getParameter("photo" + i);
          if (filepath.length() > 0) {
            photoFiles.add(new PhotoFile().setFilepath(filepath));
          }
        }

        if (photoFiles.size() == 0) {
          throw new Exception("최소 한 개의 사진 파일을 등록해야 합니다.");
        }

        photoBoard.setFiles(photoFiles);
        photoBoardService.add(photoBoard);

        out.println("<p>새 사진 게시글을 등록했습니다.</p>");

      } catch (Exception e) {
        out.printf("<p>%s</p>\n", e.getMessage());
      }
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
