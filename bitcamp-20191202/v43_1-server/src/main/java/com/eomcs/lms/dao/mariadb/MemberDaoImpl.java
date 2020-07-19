package com.eomcs.lms.dao.mariadb;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberDaoImpl implements MemberDao {

  SqlSessionFactory sqlSessionFactory;

  public MemberDaoImpl( //
      SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.insert("MemberMapper.insertMember", member);
      sqlSession.commit();
      return count;
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("MemberMapper.selectMember");
    }
  }

  @Override
  public Member findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("MemberMapper.selectDetail", no);
    }
  }

  @Override
  public int update(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.update("MemberMapper.updateMember", member);
      sqlSession.commit();
      return count;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int count = sqlSession.delete("MemberMapper.deleteMember", no);
      sqlSession.commit();
      return count;
    }
  }

  @Override
  public List<Member> findByKeyword(String keyword) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("MemberMapper.selectByKeyword", keyword);
    }
  }

  @Override
  public Member findByEmailAndPassword(String email, String password) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      HashMap<String, Object> params = new HashMap<>();
      params.put("email", email);
      params.put("password", password);
      return sqlSession.selectOne("MemberMapper.selectByEmailPassword", params);
    }
  }
}
