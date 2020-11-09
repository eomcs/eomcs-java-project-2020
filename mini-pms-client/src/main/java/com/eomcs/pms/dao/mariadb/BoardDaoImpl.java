package com.eomcs.pms.dao.mariadb;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;

// 역할
// - 게시글 데이터를 등록,조회,목록조회,변경,삭제 처리하는 일을 한다.
//
public class BoardDaoImpl implements com.eomcs.pms.dao.BoardDao{

  Connection con;

  public BoardDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public int insert(Board board) throws Exception {
    InputStream inputStream = Resources.getResourceAsStream(
        "com/eomcs/pms/conf/mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory =
        new SqlSessionFactoryBuilder().build(inputStream);

    // mybatis 의 커밋 상태
    // - sqlSession.openSession() : 수동 커밋
    // - sqlSession.openSession(true) : 오토 커밋
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {

      // => SqlSession 객체에 insert SQL 문을 실행하라고 명령한다.
      return sqlSession.insert("BoardDao.insert", board);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_board where no=?")) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  @Override
  public Board findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " b.no,"
            + " b.title,"
            + " b.content,"
            + " b.cdt,"
            + " b.vw_cnt,"
            + " m.no writer_no,"
            + " m.name"
            + " from pms_board b inner join pms_member m on b.writer=m.no"
            + " where b.no = ?")) {

      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Board board = new Board();
          board.setNo(rs.getInt("no"));
          board.setTitle(rs.getString("title"));
          board.setContent(rs.getString("content"));

          Member member = new Member();
          member.setNo(rs.getInt("writer_no"));
          member.setName(rs.getString("name"));
          board.setWriter(member);

          board.setRegisteredDate(rs.getDate("cdt"));
          board.setViewCount(rs.getInt("vw_cnt") + 1);

          try (PreparedStatement stmt2 = con.prepareStatement(
              "update pms_board set vw_cnt = vw_cnt + 1"
                  + " where no = ?")) {
            stmt2.setInt(1, no);
            stmt2.executeUpdate(); // 조회수 증가
          }
          return board;
        } else {
          return null;
        }
      }
    }
  }

  @Override
  public List<Board> findAll() throws Exception {
    // mybatis 객체 준비

    // => mybatis 설정 파일을 읽어 들일 입력 스트림을 준비한다.
    InputStream inputStream = Resources.getResourceAsStream(
        "com/eomcs/pms/conf/mybatis-config.xml");

    // => 입력 스트림에서 mybatis 설정 값을 읽어 SqlSessionFactory를 만든다.
    SqlSessionFactory sqlSessionFactory =
        new SqlSessionFactoryBuilder().build(inputStream);

    // => SqlSessionFactory에서 SqlSession 객체를 얻는다.
    //    SqlSession 객체는 SQL 문을 실행하는 객체다.
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

      // => SqlSession 객체에게 별도 파일에 분리한 SQL을 찾아 실행하라고 명령한다.
      return sqlSession.selectList("BoardDao.findAll");
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update pms_board set title = ?, content = ? where no = ?")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getNo());
      return stmt.executeUpdate();
    }
  }
}








