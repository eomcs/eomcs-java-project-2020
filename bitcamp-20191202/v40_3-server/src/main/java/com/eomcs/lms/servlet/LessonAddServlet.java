package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.Prompt;

public class LessonAddServlet implements Servlet {

  LessonDao lessonDao;

  public LessonAddServlet(LessonDao lessonDao) {
    this.lessonDao = lessonDao;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    Lesson lesson = new Lesson();

    lesson.setTitle(Prompt.getString(in, out, "강의명? "));
    lesson.setDescription(Prompt.getString(in, out, "내용? "));
    lesson.setStartDate(Prompt.getDate(in, out, "강의 시작일? "));
    lesson.setEndDate(Prompt.getDate(in, out, "강의 종료일? "));
    lesson.setTotalHours(Prompt.getInt(in, out, "총 강의시간? "));
    lesson.setDayHours(Prompt.getInt(in, out, "일 강의시간? "));

    if (lessonDao.insert(lesson) > 0) {
      out.println("강의를 저장했습니다.");

    } else {
      out.println("저장에 실패했습니다.");
    }
  }
}
