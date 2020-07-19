package com.eomcs.lms.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.service.BoardService;

public class BoardServiceImpl2 implements BoardService {

  SqlSessionFactory sqlSessionFactory;

  public BoardServiceImpl2(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void add(Board board) throws Exception {
    // 스레드 마다 SqlSession 객체를 구분해서 사용하기 때문에
    // 메서드가 호출될 때 마다 SqlSession 객체를 생성해야 한다.
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      // SqlSession 객체를 사용하여 BoardDao 인터페이스의 구현체를 얻는다.
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);

      boardDao.insert(board);
    }
  }

  @Override
  public List<Board> list() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      return boardDao.findAll();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      return boardDao.delete(no);
    }
  }

  @Override
  public Board get(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      return boardDao.findByNo(no);
    }
  }

  @Override
  public int update(Board board) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
      return boardDao.update(board);
    }
  }
}
