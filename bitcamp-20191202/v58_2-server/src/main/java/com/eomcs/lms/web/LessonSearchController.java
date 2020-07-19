package com.eomcs.lms.web;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;
import com.eomcs.util.RequestMapping;

@Component
public class LessonSearchController {

  @Autowired
  LessonService lessonService;

  @RequestMapping("/lesson/search")
  public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    return "/lesson/search.jsp";
  }
}


