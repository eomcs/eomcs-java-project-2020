package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class TaskDetailCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[작업 상세보기]");
    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select t.content, t.deadline, t.owner, t.status, m.name owner_name"
                + " from pms_task t inner join pms_member m on t.owner=m.no"
                + " where t.no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          System.out.printf("내용: %s\n", rs.getString("content"));
          System.out.printf("마감일: %s\n", rs.getDate("deadline"));
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
          System.out.printf("상태: %s\n", stateLabel);
          System.out.printf("담당자: %s\n", rs.getString("owner_name"));

        } else {
          System.out.println("해당 번호의 작업이 존재하지 않습니다.");
        }
      }
    } catch (Exception e) {
      System.out.println("작업 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
