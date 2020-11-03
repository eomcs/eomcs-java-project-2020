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
            "select p.no, p.title, p.sdt, p.edt, m.name owner_name"
                + " from pms_project p inner join pms_member m on p.owner=m.no"
                + " order by p.no desc")) {

      try (ResultSet rs = stmt.executeQuery()) {
        System.out.println("번호, 프로젝트명, 시작일 ~ 종료일, 관리자, 팀원");

        while (rs.next()) {
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

          System.out.printf("%d, %s, %s ~ %s, %s, [%s]\n",
              rs.getInt("no"),
              rs.getString("title"),
              rs.getString("sdt"),
              rs.getString("edt"),
              rs.getString("owner_name"),
              members.toString());
        }
      }
    } catch (Exception e) {
      System.out.println("프로젝트 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
