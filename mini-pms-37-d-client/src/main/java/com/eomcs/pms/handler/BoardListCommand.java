package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardListCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[게시물 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select b.no, b.title, b.cdt, b.vw_cnt, m.name"
                + " from pms_board b inner join pms_member m on b.writer=m.no"
                + " order by b.no desc")) {

      try (ResultSet rs = stmt.executeQuery()) {
        System.out.println("번호, 제목, 작성자, 등록일, 조회수");
        while (rs.next()) {
          System.out.printf("%d, %s, %s, %s, %d\n",
              rs.getInt("no"),
              rs.getString("title"),
              rs.getString("name"),
              rs.getDate("cdt"),
              rs.getInt("vw_cnt"));
        }
      }
    } catch (Exception e) {
      System.out.println("게시글 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }

}
