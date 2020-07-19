package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;

public class PhotoBoardDeleteServlet implements Servlet {

  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;

  public PhotoBoardDeleteServlet( //
      PhotoBoardDao photoBoardDao, //
      PhotoFileDao photoFileDao) {
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }


  @Override
  public void service(Scanner in, PrintStream out) throws Exception {

    out.println("번호? ");
    out.println("!{}!");
    out.flush();

    int no = Integer.parseInt(in.nextLine());

    // 사진 게시글을 삭제하기 전에 먼저 자식 테이블에 있는
    // 첨부 파일 데이터를 삭제한다.
    photoFileDao.deleteAll(no);

    if (photoBoardDao.delete(no) > 0) {
      out.println("사진 게시글을 삭제했습니다.");

    } else {
      out.println("해당 번호의 사진 게시글이 없습니다.");
    }
  }
}
