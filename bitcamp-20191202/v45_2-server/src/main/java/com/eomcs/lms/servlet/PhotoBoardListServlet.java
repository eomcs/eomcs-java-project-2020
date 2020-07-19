package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.Prompt;

public class PhotoBoardListServlet implements Servlet {

  PhotoBoardService photoBoardService;
  LessonService lessonService;

  public PhotoBoardListServlet(//
      PhotoBoardService photoBoardService, //
      LessonService lessonService) {
    this.photoBoardService = photoBoardService;
    this.lessonService = lessonService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {

    int lessonNo = Prompt.getInt(in, out, "수업번호? ");

    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      out.println("수업 번호가 유효하지 않습니다.");
      return;
    }

    out.printf("수업명: %s\n", lesson.getTitle());
    out.println("----------------------------------------------------------");

    List<PhotoBoard> photoBoards = photoBoardService.listLessonPhoto(lessonNo);

    for (PhotoBoard photoBoard : photoBoards) {
      out.printf("%d, %s, %s, %d\n", //
          photoBoard.getNo(), //
          photoBoard.getTitle(), //
          photoBoard.getCreatedDate(), //
          photoBoard.getViewCount() //
      );
    }
  }
}
