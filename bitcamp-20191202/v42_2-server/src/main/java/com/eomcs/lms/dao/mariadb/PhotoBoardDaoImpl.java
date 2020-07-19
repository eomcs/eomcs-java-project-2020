package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.sql.DataSource;

public class PhotoBoardDaoImpl implements PhotoBoardDao {

  DataSource dataSource;

  public PhotoBoardDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public int insert(PhotoBoard photoBoard) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "insert into lms_photo(titl,lesson_id) values(?,?)", //
            Statement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, photoBoard.getTitle());
      stmt.setInt(2, photoBoard.getLesson().getNo());

      int result = stmt.executeUpdate();
      try (ResultSet generatedKeySet = stmt.getGeneratedKeys()) {
        generatedKeySet.next();
        photoBoard.setNo(generatedKeySet.getInt(1));
      }
      return result;
    }
  }

  @Override
  public List<PhotoBoard> findAllByLessonNo(int lessonNo) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "select photo_id, titl, cdt, vw_cnt, lesson_id" //
                + " from lms_photo" //
                + " where lesson_id=?" //
                + " order by photo_id desc")) {
      stmt.setInt(1, lessonNo);
      try (ResultSet rs = stmt.executeQuery()) {
        ArrayList<PhotoBoard> list = new ArrayList<>();
        while (rs.next()) {
          PhotoBoard photoBoard = new PhotoBoard();
          photoBoard.setNo(rs.getInt("photo_id"));
          photoBoard.setTitle(rs.getString("titl"));
          photoBoard.setCreatedDate(rs.getDate("cdt"));
          photoBoard.setViewCount(rs.getInt("vw_cnt"));
          list.add(photoBoard);
        }
        return list;
      }
    }
  }

  @Override
  public PhotoBoard findByNo(int no) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "select" //
                + " p.photo_id," //
                + " p.titl," //
                + " p.cdt," //
                + " p.vw_cnt,"//
                + " l.lesson_id,"//
                + " l.titl lesson_title" //
                + " from lms_photo p" //
                + " inner join lms_lesson l on p.lesson_id=l.lesson_id" //
                + " where photo_id=?")) {
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          PhotoBoard photoBoard = new PhotoBoard();
          photoBoard.setNo(rs.getInt("photo_id"));
          photoBoard.setTitle(rs.getString("titl"));
          photoBoard.setCreatedDate(rs.getDate("cdt"));
          photoBoard.setViewCount(rs.getInt("vw_cnt"));

          Lesson lesson = new Lesson();
          lesson.setNo(rs.getInt("lesson_id"));
          lesson.setTitle(rs.getString("lesson_title"));

          photoBoard.setLesson(lesson);

          return photoBoard;
        } else {
          return null;
        }
      }
    }
  }

  @Override
  public int update(PhotoBoard photoBoard) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "update lms_photo set" //
                + " titl=?" //
                + " where photo_id=?")) {
      stmt.setString(1, photoBoard.getTitle());
      stmt.setInt(2, photoBoard.getNo());
      return stmt.executeUpdate();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "delete from lms_photo" //
                + " where photo_id=?")) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

}
