package com.eomcs.pms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.pms.domain.Board;

// Mybatis 적용
// => SqlSessionFactory를 자체적으로 생성하지 않고
//    생성자를 통해 외부에서 주입 받는다.(Dependency Injection: DI)
//
public class BoardDaoImpl implements com.eomcs.pms.dao.BoardDao{

  SqlSessionFactory sqlSessionFactory;

  public BoardDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Board board) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.insert("BoardDao.insert", board);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.delete("BoardDao.delete", no);
    }
  }

  @Override
  public Board findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("BoardDao.findByNo", no);
    }
  }

  @Override
  public List<Board> findAll(String keyword) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("BoardDao.findAll", keyword);
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("BoardDao.update", board);
    }
  }

  @Override
  public int updateViewCount(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.update("BoardDao.updateViewCount", no);
    }
  }
}








