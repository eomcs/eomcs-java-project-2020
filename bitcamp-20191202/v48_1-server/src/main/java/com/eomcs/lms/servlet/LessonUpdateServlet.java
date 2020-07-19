package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;
import com.eomcs.util.Component;
import com.eomcs.util.Prompt;
import com.eomcs.util.RequestMapping;

@Component
public class LessonUpdateServlet {

  LessonService lessonService;

  public LessonUpdateServlet(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @RequestMapping("/lesson/update")
  public void service(Scanner in, PrintStream out) throws Exception {

    int no = Prompt.getInt(in, out, "번호? ");

    Lesson old = lessonService.get(no);
    if (old == null) {
      out.println("해당 번호의 강의가 없습니다.");
      return;
    }

    Lesson lesson = new Lesson();

    lesson.setNo(no);
    lesson.setTitle(Prompt.getString(in, out, //
        String.format("강의명(%s)? ", old.getTitle())));
    lesson.setDescription(Prompt.getString(in, out, //
        String.format("내용(%s)? ", old.getDescription())));
    lesson.setStartDate(Prompt.getDate(in, out, //
        String.format("강의 시작일(%s)? ", old.getStartDate())));
    lesson.setEndDate(Prompt.getDate(in, out, //
        String.format("강의 종료일(%s)? ", old.getEndDate())));
    lesson.setTotalHours(Prompt.getInt(in, out, //
        String.format("총 강의시간(%d)? ", old.getTotalHours())));
    lesson.setDayHours(Prompt.getInt(in, out, //
        String.format("일 강의시간(%d)? ", old.getDayHours())));

    if (lessonService.update(lesson) > 0) {
      out.println("강의를 변경했습니다.");

    } else {
      out.println("변경에 실패했습니다.");
    }
  }
}
