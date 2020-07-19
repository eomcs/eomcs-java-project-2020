package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.service.LessonService;
import com.eomcs.util.Component;
import com.eomcs.util.Prompt;

@Component("/lesson/delete")
public class LessonDeleteServlet implements Servlet {

  LessonService lessonService;

  public LessonDeleteServlet(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    int no = Prompt.getInt(in, out, "번호? ");

    if (lessonService.delete(no) > 0) { // 삭제했다면,
      out.println("강의를 삭제했습니다.");

    } else {
      out.println("해당 번호의 강의가 없습니다.");
    }
  }
}
