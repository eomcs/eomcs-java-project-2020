package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.eomcs.util.Prompt;

public class BoardDetailCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[게시물 상세보기]");
    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "select no, title, content, writer, cdt, vw_cnt"
              + " from pms_board"
              + " where no = %d", no);

      try (ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
          System.out.printf("제목: %s\n", rs.getString("title"));
          System.out.printf("내용: %s\n", rs.getString("content"));
          System.out.printf("작성자: %s\n", rs.getString("writer"));
          System.out.printf("등록일: %s\n", rs.getDate("cdt"));
          System.out.printf("조회수: %d\n", rs.getInt("vw_cnt") + 1);

          sql = String.format(
              "update pms_board set vw_cnt = vw_cnt + 1"
                  + " where no = %d", no);
          stmt.executeUpdate(sql); // 조회수 증가

        } else {
          System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
        }
      }
    } catch (Exception e) {
      System.out.println("게시글 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
