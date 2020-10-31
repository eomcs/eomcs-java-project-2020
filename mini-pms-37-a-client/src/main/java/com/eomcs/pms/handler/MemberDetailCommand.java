package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.eomcs.util.Prompt;

public class MemberDetailCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[회원 상세보기]");
    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "select name, email, photo, tel, cdt"
              + " from pms_member"
              + " where no = %d", no);

      try (ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
          System.out.printf("이름: %s\n", rs.getString("name"));
          System.out.printf("이메일: %s\n", rs.getString("email"));
          System.out.printf("사진: %s\n", rs.getString("photo"));
          System.out.printf("전화: %s\n", rs.getString("tel"));
          System.out.printf("등록일: %s\n", rs.getDate("cdt"));

        } else {
          System.out.println("해당 번호의 회원이 존재하지 않습니다.");
        }
      }
    } catch (Exception e) {
      System.out.println("회원 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
