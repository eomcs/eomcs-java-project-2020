package com.eomcs.lms.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;

@Controller
public class LessonController {

  @Autowired
  LessonService lessonService;

  @RequestMapping("/lesson/form")
  public String form() {
    return "/lesson/form.jsp";
  }

  @RequestMapping("/lesson/add")
  public String add(Lesson lesson) throws Exception {
    if (lessonService.add(lesson) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("수업을 추가할 수 없습니다.");
    }
  }

  @RequestMapping("/lesson/delete")
  public String delete(int no) throws Exception {
    if (lessonService.delete(no) > 0) { // 삭제했다면,
      return "redirect:list";
    } else {
      throw new Exception("삭제할 수업 번호가 유효하지 않습니다.");
    }
  }

  @RequestMapping("/lesson/detail")
  public String detail(int no, Map<String, Object> model) throws Exception {
    model.put("lesson", lessonService.get(no));
    return "/lesson/detail.jsp";
  }

  @RequestMapping("/lesson/list")
  public String list(Map<String, Object> model) throws Exception {
    model.put("list", lessonService.list());
    return "/lesson/list.jsp";
  }

  @RequestMapping("/lesson/update")
  public String update(Lesson lesson) throws Exception {
    if (lessonService.update(lesson) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("변경할 수업 번호가 유효하지 않습니다.");
    }
  }

  @RequestMapping("/lesson/search")
  public String search(Lesson lesson, Map<String, Object> model) throws Exception {
    HashMap<String, Object> map = new HashMap<>();

    if (lesson.getTitle().length() > 0) {
      map.put("title", lesson.getTitle());
    }

    if (lesson.getStartDate() != null) {
      map.put("startDate", lesson.getStartDate().toString());
    }

    if (lesson.getEndDate() != null) {
      map.put("endDate", lesson.getEndDate().toString());
    }

    if (lesson.getTotalHours() > 0) {
      map.put("totalHours", lesson.getTotalHours());
    }

    if (lesson.getDayHours() > 0) {
      map.put("dayHours", lesson.getDayHours());
    }

    model.put("list", lessonService.search(map));
    return "/lesson/search.jsp";
  }
}
