package com.eomcs.pms.dao.mariadb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.pms.domain.Project;

public class ProjectDaoImpl implements com.eomcs.pms.dao.ProjectDao {

  SqlSessionFactory sqlSessionFactory;

  public ProjectDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Project project) throws Exception {

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

      // 프로젝트 정보 입력
      int count = sqlSession.insert("ProjectDao.insert", project);

      // 프로젝트의 멤버 정보 입력
      sqlSession.insert("ProjectDao.insertMembers", project);

      return count;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("ProjectDao.delete", no);
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
      return sqlSession.selectList("ProjectDao.findAll");
    }
  }

  @Override
  public List<Project> findByKeyword(String item, String keyword) throws Exception {
    HashMap<String,Object> map = new HashMap<>();
    map.put("item", item);
    map.put("keyword", keyword);

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("ProjectDao.findByKeyword", map);
    }
  }

  @Override
  public List<Project> findByDetailKeyword(Map<String,Object> keywords) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("ProjectDao.findByDetailKeyword", keywords);
    }
  }

  @Override
  public int update(Project project) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("ProjectDao.update", project);
    }
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("ProjectDao.deleteMembers", projectNo);
    }
  }
}
