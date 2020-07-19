package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.domain.Lesson;

public class LessonDaoImpl implements LessonDao {

  Connection con;

  public LessonDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public int insert(Lesson lesson) throws Exception {
    try (Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate(
          "insert into lms_lesson(sdt, edt, tot_hr, day_hr, titl, conts)" + " values('"
              + lesson.getStartDate().toString() + "', '" + lesson.getEndDate().toString() + "', "
              + lesson.getTotalHours() + ", " + lesson.getDayHours() + ", '" + lesson.getTitle()
              + "', '" + lesson.getDescription() + "')");
      return result;
    }
  }

  @Override
  public List<Lesson> findAll() throws Exception {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select lesson_id, titl, sdt, edt, tot_hr from lms_lesson")) {

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
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select lesson_id, titl, conts, sdt, edt, tot_hr, day_hr" + " from lms_lesson"
                + " where lesson_id=" + no)) {

      if (rs.next()) { // 데이터를 한 개 가져왔으면 true를 리턴한다.
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

  @Override
  public int update(Lesson lesson) throws Exception {
    try (Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("update lms_lesson set" //
          + " titl='" + lesson.getTitle() //
          + "', conts='" + lesson.getDescription() //
          + "', sdt='" + lesson.getStartDate() //
          + "', edt='" + lesson.getEndDate() //
          + "', tot_hr=" + lesson.getTotalHours() //
          + ", day_hr=" + lesson.getDayHours() //
          + " where lesson_id=" + lesson.getNo());

      return result;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("delete from lms_lesson where lesson_id=" + no);

      return result;
    }
  }

}
