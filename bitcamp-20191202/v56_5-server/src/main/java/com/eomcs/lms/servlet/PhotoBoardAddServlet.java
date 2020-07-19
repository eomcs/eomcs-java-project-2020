package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;

@WebServlet("/photoboard/add")
public class PhotoBoardAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
    try {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();

      ServletContext servletContext = getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      LessonService lessonService = iocContainer.getBean(LessonService.class);

      Lesson lesson = lessonService.get(lessonNo);

      request.getRequestDispatcher("/header").include(request, response);

      out.println("<h1>사진 입력</h1>");
      out.println("<form action='add' method='post'>");
      out.printf("강의번호: <input name='lessonNo' type='text' value='%d' readonly><br>\n", //
          lesson.getNo());
      out.printf("강의명: %s<br>\n", lesson.getTitle());
      out.println("내용:<br>");
      out.println("<textarea name='title' rows='5' cols='60'></textarea><br>");
      out.println("<hr>");
      out.println("사진: <input name='photo1' type='file'><br>");
      out.println("사진: <input name='photo2' type='file'><br>");
      out.println("사진: <input name='photo3' type='file'><br>");
      out.println("사진: <input name='photo4' type='file'><br>");
      out.println("사진: <input name='photo5' type='file'><br>");
      out.println("<button>제출</button>");
      out.println("</form>");

      request.getRequestDispatcher("/footer").include(request, response);

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list?lessonNo=" + lessonNo);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));

    try {

      ServletContext servletContext = getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      LessonService lessonService = iocContainer.getBean(LessonService.class);
      PhotoBoardService photoBoardService = iocContainer.getBean(PhotoBoardService.class);

      Lesson lesson = lessonService.get(lessonNo);
      if (lesson == null) {
        throw new Exception("수업 번호가 유효하지 않습니다.");
      }

      PhotoBoard photoBoard = new PhotoBoard();
      photoBoard.setTitle(request.getParameter("title"));
      photoBoard.setLesson(lesson);

      ArrayList<PhotoFile> photoFiles = new ArrayList<>();
      for (int i = 1; i <= 5; i++) {
        String filepath = request.getParameter("photo" + i);
        if (filepath.length() > 0) {
          photoFiles.add(new PhotoFile().setFilepath(filepath));
        }
      }

      if (photoFiles.size() == 0) {
        throw new Exception("최소 한 개의 사진 파일을 등록해야 합니다.");
      }

      photoBoard.setFiles(photoFiles);
      photoBoardService.add(photoBoard);

      response.sendRedirect("list?lessonNo=" + lessonNo);

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list?lessonNo=" + lessonNo);
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
