package com.eomcs.pms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public class ProjectDaoImpl implements com.eomcs.pms.dao.ProjectDao {

  Connection con;
  SqlSessionFactory sqlSessionFactory;

  public ProjectDaoImpl(Connection con, SqlSessionFactory sqlSessionFactory) {
    this.con = con;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Project project) throws Exception {

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

      // 프로젝트 정보 입력
      int count = sqlSession.insert("ProjectDao.insert", project);

      // 프로젝트의 멤버 정보 입력
      for (Member member : project.getMembers()) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("memberNo", member.getNo());
        map.put("projectNo", project.getNo());
        sqlSession.insert("ProjectDao.insertMember", map);
      }

      sqlSession.commit();
      return count;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    con.setAutoCommit(false);
    try {
      // => 프로젝트의 작업을 지운다.
      try (PreparedStatement stmt = con.prepareStatement(
          "delete from pms_task where project_no=" + no)) {
        stmt.executeUpdate();
      }

      // => 프로젝트에 참여하는 모든 팀원을 삭제한다.
      try (PreparedStatement stmt = con.prepareStatement(
          "delete from pms_member_project where project_no=" + no)) {
        stmt.executeUpdate();
      }

      // => 프로젝트를 삭제한다.
      int count = 0;
      try (PreparedStatement stmt = con.prepareStatement(
          "delete from pms_project where no=?")) {
        stmt.setInt(1, no);
        count = stmt.executeUpdate();
      }

      con.commit();
      return count;

    } catch (Exception e) {
      con.rollback();
      throw e;

    } finally {
      con.setAutoCommit(true);
    }
  }

  @Override
  public Project findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " p.no,"
            + " p.title,"
            + " p.content,"
            + " p.sdt,"
            + " p.edt,"
            + " m.no owner_no,"
            + " m.name owner_name"
            + " from pms_project p inner join pms_member m on p.owner=m.no"
            + " where p.no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Project project = new Project();
          project.setNo(rs.getInt("no"));
          project.setTitle(rs.getString("title"));
          project.setContent(rs.getString("content"));
          project.setStartDate(rs.getDate("sdt"));
          project.setEndDate(rs.getDate("edt"));

          Member owner = new Member();
          owner.setNo(rs.getInt("owner_no"));
          owner.setName(rs.getString("owner_name"));
          project.setOwner(owner);

          ArrayList<Member> members = new ArrayList<>();
          try (PreparedStatement stmt2 = con.prepareStatement(
              "select mp.member_no, m.name"
                  + " from pms_member_project mp"
                  + " inner join pms_member m on mp.member_no=m.no"
                  + " where mp.project_no=" + rs.getInt("no"));
              ResultSet memberRs = stmt2.executeQuery()) {

            while (memberRs.next()) {
              Member member = new Member();
              member.setNo(memberRs.getInt("member_no"));
              member.setName(memberRs.getString("name"));
              members.add(member);
            }
          }
          project.setMembers(members);
          return project;
        }
        return null;
      }
    }
  }

  @Override
  public List<Project> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      List<Project> projects = sqlSession.selectList("ProjectDao.findAll");
      return projects;
    }
  }

  @Override
  public int update(Project project) throws Exception {
    con.setAutoCommit(false);
    try {
      try (PreparedStatement stmt = con.prepareStatement(
          "update pms_project set"
              + " title = ?,"
              + " content = ?,"
              + " sdt = ?,"
              + " edt = ?,"
              + " owner = ?"
              + " where no = ?")) {

        stmt.setString(1, project.getTitle());
        stmt.setString(2, project.getContent());
        stmt.setDate(3, project.getStartDate());
        stmt.setDate(4, project.getEndDate());
        stmt.setInt(5, project.getOwner().getNo());
        stmt.setInt(6, project.getNo());
        int count = stmt.executeUpdate();

        if (count == 0) {
          return 0;
        }
      }

      // 프로젝트 팀원 변경한다.
      // => 기존에 설정된 모든 팀원을 삭제한다.
      try (PreparedStatement stmt = con.prepareStatement(
          "delete from pms_member_project where project_no=" + project.getNo())) {
        stmt.executeUpdate();
      }

      // => 새로 팀원을 입력한다.
      try (PreparedStatement stmt = con.prepareStatement(
          "insert into pms_member_project(member_no, project_no) values(?,?)")) {
        for (Member member : project.getMembers()) {
          stmt.setInt(1, member.getNo());
          stmt.setInt(2, project.getNo());
          stmt.executeUpdate();
        }
      }
      con.commit();
      return 1;

    } catch (Exception e) {
      con.rollback();
      throw e;

    } finally {
      con.setAutoCommit(true);
    }
  }
}
