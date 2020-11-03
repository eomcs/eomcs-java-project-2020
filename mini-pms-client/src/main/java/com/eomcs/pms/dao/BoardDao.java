package com.eomcs.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.eomcs.pms.domain.Board;

// 역할
// - 게시글 데이터를 등록,조회,목록조회,변경,삭제 처리하는 일을 한다.
//
public class BoardDao {

  public int insert(Board board) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into pms_board(title,content,writer) values(?,?,?)")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getWriter().getNo());
      return stmt.executeUpdate();
    }
  }

  public int delete(int no) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement("delete from pms_board where no=?")) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }
}








