package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;

public class PhotoBoardDaoImpl implements PhotoBoardDao {

  Connection con;

  public PhotoBoardDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public int insert(PhotoBoard photoBoard) throws Exception {
    try (Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate( //
          "insert into lms_photo(titl,lesson_id) values('" //
              + photoBoard.getTitle() + "', " + photoBoard.getLesson().getNo() //
              + ")");

      return result;
    }
  }

  @Override
  public List<PhotoBoard> findAllByLessonNo(int lessonNo) throws Exception {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select photo_id, titl, cdt, vw_cnt, lesson_id" //
                + " from lms_photo" //
                + " where lesson_id=" + lessonNo //
                + " order by photo_id desc")) {

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

  @Override
  public PhotoBoard findByNo(int no) throws Exception {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select" //
                + " p.photo_id," //
                + " p.titl," //
                + " p.cdt," //
                + " p.vw_cnt,"//
                + " l.lesson_id,"//
                + " l.titl lesson_title" //
                + " from lms_photo p" //
                + " inner join lms_lesson l on p.lesson_id=l.lesson_id" //
                + " where photo_id=" + no)) {

      if (rs.next()) {
        // 조인 결과 중에서 사진 게시글 결과를 PhotoBoard에 저장한다.
        PhotoBoard photoBoard = new PhotoBoard();
        photoBoard.setNo(rs.getInt("photo_id"));
        photoBoard.setTitle(rs.getString("titl"));
        photoBoard.setCreatedDate(rs.getDate("cdt"));
        photoBoard.setViewCount(rs.getInt("vw_cnt"));

        // 조인 결과 중에서 수업 데이터를 Lesson에 저장한다.
        Lesson lesson = new Lesson();
        lesson.setNo(rs.getInt("lesson_id"));
        lesson.setTitle(rs.getString("lesson_title"));

        // Lesson을 PhotoBoard에 저장한다.
        photoBoard.setLesson(lesson);

        return photoBoard;

      } else {
        return null;
      }
    }
  }

  @Override
  public int update(PhotoBoard photoBoard) throws Exception {
    try (Statement stmt = con.createStatement()) {
      int result = stmt.executeUpdate( //
          "update lms_photo set titl='" //
              + photoBoard.getTitle() //
              + "' where photo_id=" + photoBoard.getNo());
      return result;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Statement stmt = con.createStatement()) {
      int result = stmt.executeUpdate( //
          "delete from lms_photo" //
              + " where photo_id=" + no);
      return result;
    }
  }

}
