package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.eomcs.util.Prompt;

public class ProjectDetailCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "select title, content, sdt, edt, owner, members"
              + " from pms_project"
              + " where no = %d", no);

      try (ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
          System.out.printf("프로젝트명: %s\n", rs.getString("title"));
          System.out.printf("내용: %s\n", rs.getString("content"));
          System.out.printf("기간: %s ~ %s\n", rs.getDate("sdt"), rs.getDate("edt"));
          System.out.printf("만든이: %s\n", rs.getString("owner"));
          System.out.printf("팀원: %s\n", rs.getString("members"));

        } else {
          System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
        }
      }
    } catch (Exception e) {
      System.out.println("프로젝트 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
