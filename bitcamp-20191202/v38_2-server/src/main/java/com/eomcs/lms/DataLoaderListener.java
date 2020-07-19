package com.eomcs.lms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.lms.dao.mariadb.BoardDaoImpl;
import com.eomcs.lms.dao.mariadb.LessonDaoImpl;
import com.eomcs.lms.dao.mariadb.MemberDaoImpl;
import com.eomcs.lms.dao.mariadb.PhotoBoardDaoImpl;
import com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl;

// 애플리케이션이 시작되거나 종료될 때
// 데이터를 로딩하고 저장하는 일을 한다.
//
public class DataLoaderListener implements ApplicationContextListener {

  Connection con;

  @Override
  public void contextInitialized(Map<String, Object> context) {

    try {
      // DB 연결 객체 준비
      Class.forName("org.mariadb.jdbc.Driver");
      con = DriverManager.getConnection( //
          "jdbc:mariadb://localhost:3306/studydb", "study", "1111");

      // 이 메서드를 호출한 쪽(App)에서 DAO 객체를 사용할 수 있도록 Map 객체에 담아둔다.
      context.put("boardDao", new BoardDaoImpl(con));
      context.put("lessonDao", new LessonDaoImpl(con));
      context.put("memberDao", new MemberDaoImpl(con));
      context.put("photoBoardDao", new PhotoBoardDaoImpl(con));
      context.put("photoFileDao", new PhotoFileDaoImpl(con));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String, Object> context) {
    try {
      con.close();
    } catch (Exception e) {
      // DB 커넥션을 닫다가 예외 발생하면 무시한다.
      // 왜? 클라이언트 쪽에서 달리 해야 할 일이 없다.
    }
  }
}
