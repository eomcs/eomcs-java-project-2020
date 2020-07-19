package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.sql.DataSource;

public class LessonDaoImpl implements LessonDao {

  DataSource dataSource;

  public LessonDaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public int insert(Lesson lesson) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "insert into lms_lesson(sdt, edt, tot_hr, day_hr, titl, conts)"
                + " values(?,?,?,?,?,?)")) {
      stmt.setDate(1, lesson.getStartDate());
      stmt.setDate(2, lesson.getEndDate());
      stmt.setInt(3, lesson.getTotalHours());
      stmt.setInt(4, lesson.getDayHours());
      stmt.setString(5, lesson.getTitle());
      stmt.setString(6, lesson.getDescription());
      return stmt.executeUpdate();
    }
  }

  @Override
  public List<Lesson> findAll() throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "select lesson_id, titl, sdt, edt, tot_hr" + " from lms_lesson");
        ResultSet rs = stmt.executeQuery()) {
      ArrayList<Lesson> list = new ArrayList<>();
      while (rs.next()) {
        Lesson lesson = new Lesson();
        lesson.setNo(rs.getInt("lesson_id"));
        lesson.setTitle(rs.getString("titl"));
        lesson.setStartDate(rs.getDate("sdt"));
        lesson.setEndDate(rs.getDate("edt"));
        lesson.setTotalHours(rs.getInt("tot_hr"));
        list.add(lesson);
      }
      return list;
    }
  }

  @Override
  public Lesson findByNo(int no) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "select lesson_id, titl, conts, sdt, edt, tot_hr, day_hr" //
                + " from lms_lesson"//
                + " where lesson_id=?")) {
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Lesson lesson = new Lesson();
          lesson.setNo(rs.getInt("lesson_id"));
          lesson.setTitle(rs.getString("titl"));
          lesson.setDescription(rs.getString("conts"));
          lesson.setStartDate(rs.getDate("sdt"));
          lesson.setEndDate(rs.getDate("edt"));
          lesson.setTotalHours(rs.getInt("tot_hr"));
          lesson.setDayHours(rs.getInt("day_hr"));
          return lesson;

        } else {
          return null;
        }
      }
    }
  }

  @Override
  public int update(Lesson lesson) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "update lms_lesson set" //
                + " titl=?, conts=?, sdt=?, edt=?, tot_hr=?, day_hr=?"//
                + " where lesson_id=?")) {
      stmt.setString(1, lesson.getTitle());
      stmt.setString(2, lesson.getDescription());
      stmt.setDate(3, lesson.getStartDate());
      stmt.setDate(4, lesson.getEndDate());
      stmt.setInt(5, lesson.getTotalHours());
      stmt.setInt(6, lesson.getDayHours());
      stmt.setInt(7, lesson.getNo());
      return stmt.executeUpdate();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Connection con = dataSource.getConnection(); //
        PreparedStatement stmt = con.prepareStatement(//
            "delete from lms_lesson"//
                + " where lesson_id=?")) {
      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

}
