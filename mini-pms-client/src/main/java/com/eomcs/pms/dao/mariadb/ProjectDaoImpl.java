package com.eomcs.pms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("ProjectDao.findByNo", no);
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
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.update("ProjectDao.update", project);
      if (count == 0) {
        return 0;
      }

      // 프로젝트 팀원 변경한다.
      // => 기존에 설정된 모든 팀원을 삭제한다.
      sqlSession.delete("ProjectDao.deleteMember", project.getNo());

      // => 새로 팀원을 입력한다.
      for (Member member : project.getMembers()) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("memberNo", member.getNo());
        map.put("projectNo", project.getNo());
        sqlSession.insert("ProjectDao.insertMember", map);
      }
      sqlSession.commit();
      return 1;
    }
  }
}
