package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardListServlet implements Servlet {

  PhotoBoardDao photoBoardDao;
  LessonDao lessonDao;

  public PhotoBoardListServlet(PhotoBoardDao photoBoardDao, LessonDao lessonDao) {
    this.photoBoardDao = photoBoardDao;
    this.lessonDao = lessonDao;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    out.println("수업번호? ");
    out.println("!{}!");
    out.flush();

    int lessonNo = Integer.parseInt(in.nextLine());

    // 1) 수업 번호로 수업 정보를 가져온다.
    Lesson lesson = lessonDao.findByNo(lessonNo);

    // 2) 수업 번호에 해당하는 수업을 못찾았다면,
    // 안내 문구를 출력하고 응답을 종료한다.
    if (lesson == null) {
      out.println("수업 번호가 유효하지 않습니다.");
      return;
    }

    // 3) 수업 번호에 해당하는 수업을 찾았다면, 수업명을 출력한다.
    out.printf("수업명: %s\n", lesson.getTitle());
    out.println("----------------------------------------------------------");

    // 4) 해당 수업의 사진 게시글을 가져온다.
    List<PhotoBoard> photoBoards = photoBoardDao.findAllByLessonNo(lessonNo);

    // 5) 클라이언트에게 게시글을 출력한다.
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
