package com.eomcs.lms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;

@WebServlet("/lesson/search")
public class LessonSearchServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      ServletContext servletContext = getServletContext();
      ApplicationContext iocContainer =
          (ApplicationContext) servletContext.getAttribute("iocContainer");
      LessonService lessonService = iocContainer.getBean(LessonService.class);

      HashMap<String, Object> map = new HashMap<>();
      String value = request.getParameter("title");
      if (value.length() > 0) {
        map.put("title", value);
      }

      value = request.getParameter("startDate");
      if (value.length() > 0) {
        map.put("startDate", value);
      }

      value = request.getParameter("endDate");
      if (value.length() > 0) {
        map.put("endDate", value);
      }

      value = request.getParameter("totalHours");
      if (value.length() > 0) {
        map.put("totalHours", Integer.parseInt(value));
      }

      value = request.getParameter("dayHours");
      if (value.length() > 0) {
        map.put("dayHours", Integer.parseInt(value));
      }

      List<Lesson> lessons = lessonService.search(map);
      request.setAttribute("list", lessons);
      
      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/lesson/search.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("error", e);
      request.setAttribute("url", "list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}


