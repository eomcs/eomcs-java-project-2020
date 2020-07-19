package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.sql.DataSource;

public class BoardDaoImpl implements BoardDao {

  DataSource dataSource;

  public BoardDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public int insert(Board board) throws Exception {

    try (Connection con = dataSource.getConnection(); //
        Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("insert into lms_board(conts) values('" //
          + board.getTitle() + "')");

      return result;
    }
  }

  @Override
  public List<Board> findAll() throws Exception {
    try (Connection con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select board_id, conts, cdt, vw_cnt from lms_board order by board_id desc")) {

      ArrayList<Board> list = new ArrayList<>();

      while (rs.next()) {
        Board board = new Board();

        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("conts"));
        board.setDate(rs.getDate("cdt"));
        board.setViewCount(rs.getInt("vw_cnt"));

        list.add(board);
      }

      return list;
    }
  }

  @Override
  public Board findByNo(int no) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select board_id, conts, cdt, vw_cnt from lms_board where board_id=" + no)) {

      if (rs.next()) { // 데이터를 한 개 가져왔으면 true를 리턴한다.
        Board board = new Board();
        board.setNo(rs.getInt("board_id"));
        board.setTitle(rs.getString("conts"));
        board.setDate(rs.getDate("cdt"));
        board.setViewCount(rs.getInt("vw_cnt"));
        return board;

      } else {
        return null;
      }
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("update lms_board set conts = '" + //
          board.getTitle() + "' where board_id=" + board.getNo());

      return result;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("delete from lms_board where board_id=" + no);

      return result;
    }
  }

}
