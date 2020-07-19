package com.eomcs.lms.web;

import java.sql.Date;
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
public class LessonController {

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

  @RequestMapping("/lesson/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    if (lessonService.delete(no) > 0) { // 삭제했다면,
      return "redirect:list";
    } else {
      throw new Exception("삭제할 수업 번호가 유효하지 않습니다.");
    }
  }

  @RequestMapping("/lesson/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    Lesson lesson = lessonService.get(no);
    request.setAttribute("lesson", lesson);
    return "/lesson/detail.jsp";
  }

  @RequestMapping("/lesson/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setAttribute("list", lessonService.list());
    return "/lesson/list.jsp";
  }

  @RequestMapping("/lesson/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Lesson lesson = new Lesson();
    lesson.setNo(Integer.parseInt(request.getParameter("no")));
    lesson.setTitle(request.getParameter("title"));
    lesson.setDescription(request.getParameter("description"));
    lesson.setStartDate(Date.valueOf(request.getParameter("startDate")));
    lesson.setEndDate(Date.valueOf(request.getParameter("endDate")));
    lesson.setTotalHours(Integer.parseInt(request.getParameter("totalHours")));
    lesson.setDayHours(Integer.parseInt(request.getParameter("dayHours")));

    if (lessonService.update(lesson) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("변경할 수업 번호가 유효하지 않습니다.");
    }
  }

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
