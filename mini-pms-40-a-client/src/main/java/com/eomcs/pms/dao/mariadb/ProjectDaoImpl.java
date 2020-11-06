package com.eomcs.pms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public class ProjectDaoImpl implements com.eomcs.pms.dao.ProjectDao {

  Connection con;

  public ProjectDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public int insert(Project project) throws Exception {

    // 커넥션 객체에서 수행하는 작업을 수동 커밋하도록 설정한다.
    con.setAutoCommit(false);

    try {
      try (PreparedStatement stmt = con.prepareStatement(
          "insert into pms_project(title,content,sdt,edt,owner)"
              + " values(?,?,?,?,?)",
              Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, project.getTitle());
        stmt.setString(2, project.getContent());
        stmt.setDate(3, project.getStartDate());
        stmt.setDate(4, project.getEndDate());
        stmt.setInt(5, project.getOwner().getNo());
        stmt.executeUpdate();

        // 금방 입력한 프로젝트의 no 값을 가져오기
        try (ResultSet keyRs = stmt.getGeneratedKeys()) {
          keyRs.next();
          project.setNo(keyRs.getInt(1));
        }
      }

      // 위의 입력을 수행한 후 일부로 다음 입력을 60초 정도 지연시킨다.
      //Thread.sleep(60000);

      // 프로젝트에 참여하는 멤버의 정보를 저장한다.
      try (PreparedStatement stmt2 = con.prepareStatement(
          "insert into pms_member_project(member_no, project_no) values(?,?)")) {
        for (Member member : project.getMembers()) {
          stmt2.setInt(1, member.getNo());
          stmt2.setInt(2, project.getNo());
          stmt2.executeUpdate();
        }
      }

      // 프로젝트 멤버의 등록까지 예외없이 정상적으로 실행되었다면,
      // DBMS 서버에게 작업 내용을 실제 테이블에 반영하라고 요구한다.
      con.commit();

      return 1;

    } catch (Exception e) {
      // 작업을 수행하는 중에 예외가 발생하면
      // 이전에 수행했던 작업도 되돌린다.
      // 즉 마지막 커밋 상태로 되돌린다.
      con.rollback();

      // 예외가 발생하면 여기서 처리하지 말고 호출자에게 떠넘긴다.
      throw e;

    } finally {
      // 정상적으로 실행하거나 또는 예외가 발생해도
      // DB 커넥션은 다시 원래의 auto commit 상태로 만든다.
      con.setAutoCommit(true);
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
    try (PreparedStatement stmt = con.prepareStatement(
        "select p.no, p.title, p.sdt, p.edt, m.no owner_no, m.name owner_name"
            + " from pms_project p inner join pms_member m on p.owner=m.no"
            + " order by p.no desc")) {

      try (ResultSet rs = stmt.executeQuery()) {
        ArrayList<Project> projects = new ArrayList<>();
        while (rs.next()) {
          Project project = new Project();
          project.setNo(rs.getInt("no"));
          project.setTitle(rs.getString("title"));
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
          projects.add(project);
        }
        return projects;
      }
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
