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
      // 프로젝트에 소속된 모든 멤버를 삭제한다.
      sqlSession.delete("ProjectDao.deleteMembers", no);

      // 프로젝트 멤버 삭제 후 일부러 예외를 발생시킨다.
      // 그러면 위에서 수행한 프로젝트 멤버 삭제가 완료되지 않고 취소될 것이다.
      //      if (100 == 100) {
      //        throw new Exception("일부러 예외 발생!");
      //      }

      // => 프로젝트를 삭제한다.
      int count = sqlSession.delete("ProjectDao.delete", no);

      return count;
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
}
