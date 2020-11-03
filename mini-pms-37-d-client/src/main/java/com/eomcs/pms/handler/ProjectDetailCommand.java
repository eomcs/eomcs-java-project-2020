package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class ProjectDetailCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select p.no, p.title, p.content, p.sdt, p.edt, m.name owner_name"
                + " from pms_project p inner join pms_member m on p.owner=m.no"
                + " where p.no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          StringBuilder members = new StringBuilder();
          try (PreparedStatement stmt2 = con.prepareStatement(
              "select mp.member_no, m.name"
                  + " from pms_member_project mp"
                  + " inner join pms_member m on mp.member_no=m.no"
                  + " where mp.project_no=" + rs.getInt("no"));
              ResultSet memberRs = stmt2.executeQuery()) {

            while (memberRs.next()) {
              if (members.length() > 0) {
                members.append(",");
              }
              members.append(memberRs.getString("name"));
            }
          }

          System.out.printf("프로젝트명: %s\n", rs.getString("title"));
          System.out.printf("내용: %s\n", rs.getString("content"));
          System.out.printf("기간: %s ~ %s\n", rs.getDate("sdt"), rs.getDate("edt"));
          System.out.printf("관리자: %s\n", rs.getString("owner_name"));
          System.out.printf("팀원: %s\n", members.toString());

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
