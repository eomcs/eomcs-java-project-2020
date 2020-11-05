package com.eomcs.pms.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;

public class AppInitListener implements ApplicationContextListener {
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)에 오신 걸 환영합니다!");

    // 시스템에서 사용할 객체를 준비한다.
    try {
      Connection con = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
      context.put("con", con);

    } catch (Exception e) {
      System.out.println("DB 커넥션을 준비하는 중에 오류 발생");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)을 종료합니다!");

    try {
      Connection con = (Connection) context.get("con");
      con.close();
    } catch (Exception e) {
      // 커넥션을 닫다가 오류가 발생하더라도 무시한다.
    }
  }
}
