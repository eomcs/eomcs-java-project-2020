package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaskListCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[작업 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no, content, deadline, owner, status"
                + " from pms_task"
                + " order by deadline asc")) {

      try (ResultSet rs = stmt.executeQuery()) {
        System.out.println("번호, 작업내용, 마감일, 작업자, 상태");

        while (rs.next()) {
          String stateLabel = null;
          switch (rs.getInt("status")) {
            case 1:
              stateLabel = "진행중";
              break;
            case 2:
              stateLabel = "완료";
              break;
            default:
              stateLabel = "신규";
          }
          System.out.printf("%d, %s, %s, %s, %s\n",
              rs.getInt("no"),
              rs.getString("content"),
              rs.getDate("deadline"),
              rs.getString("owner"),
              stateLabel);
        }
      }
    } catch (Exception e) {
      System.out.println("작업 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
