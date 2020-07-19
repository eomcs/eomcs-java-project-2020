package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;

@WebServlet("/lesson/search")
public class LessonSearchServlet extends GenericServlet {
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

      HashMap<String, Object> map = new HashMap<>();
      String value = req.getParameter("title");
      if (value.length() > 0) {
        map.put("title", value);
      }

      value = req.getParameter("startDate");
      if (value.length() > 0) {
        map.put("startDate", value);
      }

      value = req.getParameter("endDate");
      if (value.length() > 0) {
        map.put("endDate", value);
      }

      value = req.getParameter("totalHours");
      if (value.length() > 0) {
        map.put("totalHours", Integer.parseInt(value));
      }

      value = req.getParameter("dayHours");
      if (value.length() > 0) {
        map.put("dayHours", Integer.parseInt(value));
      }

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("  <meta charset='UTF-8'>");
      out.println("  <title>강의 검색</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("  <h1>강의 검색 결과</h1>");
      out.println("  <table border='1'>");
      out.println("  <tr>");
      out.println("    <th>번호</th>");
      out.println("    <th>강의</th>");
      out.println("    <th>기간</th>");
      out.println("    <th>총강의시간</th>");
      out.println("  </tr>");

      List<Lesson> lessons = lessonService.search(map);
      for (Lesson l : lessons) {
        out.printf("  <tr>"//
            + "<td>%d</td> "//
            + "<td><a href='detail?no=%d'>%s</a></td> "//
            + "<td>%s ~ %s</td> "//
            + "<td>%d</td>"//
            + "</tr>\n", //
            l.getNo(), //
            l.getNo(), //
            l.getTitle(), //
            l.getStartDate(), //
            l.getEndDate(), //
            l.getTotalHours() //
        );
      }
      out.println("</table>");
      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}


