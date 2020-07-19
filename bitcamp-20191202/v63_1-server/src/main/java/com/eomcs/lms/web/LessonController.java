package com.eomcs.lms.web;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;

@Controller
@RequestMapping("/lesson")
public class LessonController {

  @Autowired
  LessonService lessonService;

  @GetMapping("form")
  public void form() {}

  @PostMapping("add")
  public String add(Lesson lesson) throws Exception {
    if (lessonService.add(lesson) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("수업을 추가할 수 없습니다.");
    }
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    if (lessonService.delete(no) > 0) { // 삭제했다면,
      return "redirect:list";
    } else {
      throw new Exception("삭제할 수업 번호가 유효하지 않습니다.");
    }
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {
    model.addAttribute("lesson", lessonService.get(no));
  }

  @GetMapping("list")
  public void list(Model model) throws Exception {
    model.addAttribute("list", lessonService.list());
  }

  @PostMapping("update")
  public String update(Lesson lesson) throws Exception {
    if (lessonService.update(lesson) > 0) {
      return "redirect:list";
    } else {
      throw new Exception("변경할 수업 번호가 유효하지 않습니다.");
    }
  }

  @GetMapping("search")
  public void search(Lesson lesson, Model model) throws Exception {
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

    model.addAttribute("list", lessonService.search(map));
  }
}
