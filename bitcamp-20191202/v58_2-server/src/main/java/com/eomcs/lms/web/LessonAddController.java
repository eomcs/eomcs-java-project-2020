package com.eomcs.lms.web;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;
import com.eomcs.util.RequestMapping;

@Component
public class LessonAddController {

  @Autowired
  LessonService lessonService;

  @RequestMapping("/lesson/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/lesson/form.jsp";
    }

    Lesson lesson = new Lesson();
    lesson.setTitle(request.getParameter("title"));
    lesson.setDescription(request.getParameter("description"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));

    if (lessonService.add(lesson) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("수업을 추가할 수 없습니다.");
    }
  }
}
