package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.sql.Connection;
import java.util.Scanner;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.util.ConnectionFactory;
import com.eomcs.util.Prompt;

public class PhotoBoardDeleteServlet implements Servlet {

  ConnectionFactory conFactory;
  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;

  public PhotoBoardDeleteServlet( //
      ConnectionFactory conFactory, //
      PhotoBoardDao photoBoardDao, //
      PhotoFileDao photoFileDao) {
    this.conFactory = conFactory;
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {

    int no = Prompt.getInt(in, out, "번호? ");

    // 트랜잭션 시작
    Connection con = conFactory.getConnection();
    // => ConnectionFactory는 스레드에 보관된 Connection 객체를 찾을 것이다.
    // => 있으면 스레드에 보관된 Connection 객체를 리턴해 줄 것이고,
    // => 없으면 새로 만들어 리턴해 줄 것이다.
    // => 물론 새로 만든 Connection 객체는 스레드에도 보관될 것이다.

    con.setAutoCommit(false);

    try {
      photoFileDao.deleteAll(no);

      if (photoBoardDao.delete(no) == 0) {
        throw new Exception("해당 번호의 사진 게시글이 없습니다.");
      }
      con.commit();
      out.println("사진 게시글을 삭제했습니다.");

    } catch (Exception e) {
      con.rollback();
      out.println(e.getMessage());

    } finally {
      con.setAutoCommit(true);
    }
  }
}
