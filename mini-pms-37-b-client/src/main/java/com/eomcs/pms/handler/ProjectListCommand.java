package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectListCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[프로젝트 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no, title, sdt, edt, owner, members"
                + " from pms_project"
                + " order by no desc")) {

      try (ResultSet rs = stmt.executeQuery()) {
        System.out.println("번호, 프로젝트명, 시작일 ~ 종료일, 관리자, 팀원");
        while (rs.next()) {
          System.out.printf("%d, %s, %s ~ %s, %s, [%s]\n",
              rs.getInt("no"),
              rs.getString("title"),
              rs.getString("sdt"),
              rs.getString("edt"),
              rs.getString("owner"),
              rs.getString("members"));
        }
      }
    } catch (Exception e) {
      System.out.println("프로젝트 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
