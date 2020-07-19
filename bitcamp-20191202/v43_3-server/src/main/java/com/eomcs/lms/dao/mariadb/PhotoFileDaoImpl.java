package com.eomcs.lms.dao.mariadb;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;

public class PhotoFileDaoImpl implements PhotoFileDao {

  SqlSessionFactory sqlSessionFactory;

  public PhotoFileDaoImpl( //
      SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(PhotoBoard photoBoard) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.insert(//
          "PhotoFileMapper.insertPhotoFile", photoBoard);
      return count;
    }
  }

  @Override
  public List<PhotoFile> findAll(int boardNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList(//
          "PhotoFileMapper.selectPhotoFile", boardNo);
    }
  }

  @Override
  public int deleteAll(int boardNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.delete(//
          "PhotoFileMapper.deletePhotoFile", boardNo);
      return count;
    }
  }
}
