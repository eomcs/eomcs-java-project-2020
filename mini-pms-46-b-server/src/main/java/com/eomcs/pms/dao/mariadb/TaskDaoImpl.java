package com.eomcs.pms.dao.mariadb;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;
import com.eomcs.pms.domain.Task;

@Repository
public class TaskDaoImpl implements com.eomcs.pms.dao.TaskDao {

  SqlSessionFactory sqlSessionFactory;

  public TaskDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Task task) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("TaskDao.insert", task);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("TaskDao.delete", no);
    }
  }

  @Override
  public int deleteByProjectNo(int projectNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("TaskDao.deleteByProjectNo", projectNo);
    }
  }

  @Override
  public Task findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("TaskDao.findByNo", no);
    }
  }

  @Override
  public List<Task> findAll(Map<String,Object> map) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("TaskDao.findAll", map);
    }
  }

  @Override
  public int update(Task task) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("TaskDao.update", task);
    }
  }
}
