package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.util.Prompt;

public class BoardUpdateCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[게시물 변경]");
    int no = Prompt.inputInt("번호? ");

    String title = null;
    String content = null;

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select title, content"
                + " from pms_board"
                + " where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          title = rs.getString("title");
          content = rs.getString("content");

        } else {
          System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
          return;
        }
      }
    } catch (Exception e) {
      System.out.println("게시글 조회 중 오류 발생!");
      e.printStackTrace();
      return;
    }

    title = Prompt.inputString(String.format("제목(%s)? ", title));
    content = Prompt.inputString(String.format("내용(%s)? ", content));

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "update pms_board set title = ?, content = ? where no = ?")) {

      stmt.setString(1, title);
      stmt.setString(2, content);
      stmt.setInt(3, no);
      int count = stmt.executeUpdate();

      if (count == 0) {
        System.out.println("해당 번호의 게시물이 존재하지 않습니다.");
      } else {
        System.out.println("게시글을 변경하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("게시글 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
