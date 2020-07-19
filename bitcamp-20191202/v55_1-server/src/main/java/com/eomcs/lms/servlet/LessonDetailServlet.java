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
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;

@WebServlet("/lesson/detail")
public class LessonDetailServlet extends GenericServlet {
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

      int no = Integer.parseInt(req.getParameter("no"));
      Lesson lesson = lessonService.get(no);

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>수업 상세정보</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>수업 상세정보</h1>");

      if (lesson != null) {
        out.println("<form action='update'>");
        out.printf("번호: <input name='no' readonly type='text' value='%d'><br>\n", //
            lesson.getNo());
        out.printf("강의명: <input name='title' type='text' value='%s'><br>\n", //
            lesson.getTitle());
        out.println("내용:<br>");
        out.printf("<textarea name='description' rows='5' cols='60'>%s</textarea><br>\n", //
            lesson.getDescription());
        out.printf("강의 시작일: <input name='startDate' type='date' value='%s'><br>\n", //
            lesson.getStartDate());
        out.printf("강의 종료일: <input name='endDate' type='date' value='%s'><br>\n", //
            lesson.getEndDate());
        out.printf("총 강의시간: <input name='totalHours' type='number' value='%d'><br>\n", //
            lesson.getTotalHours());
        out.printf("일 강의시간: <input name='dayHours' type='number' value='%d'><br>\n", //
            lesson.getDayHours());
        out.println("<p>");
        out.println("<button>변경</button>");
        out.printf("<a href='delete?no=%d'>삭제</a>\n", //
            lesson.getNo());
        out.printf("<a href='../photoboard/list?lessonNo=%d'>사진게시판</a>\n", //
            lesson.getNo());
        out.println("</p>");
        out.println("</form>");
      } else {
        out.println("<p>해당 번호의 강의가 없습니다.</p>");
      }
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
