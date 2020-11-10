package com.eomcs.pms.dao.mariadb;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.eomcs.pms.domain.Member;

public class MemberDaoImpl implements com.eomcs.pms.dao.MemberDao {

  SqlSessionFactory sqlSessionFactory;

  public MemberDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public int insert(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      return sqlSession.insert("MemberDao.insert", member);
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      return sqlSession.delete("MemberDao.delete", no);
    }
  }

  @Override
  public Member findByNo(int no) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      return sqlSession.selectOne("MemberDao.findByNo", no);
    }
  }

  @Override
  public Member findByName(String name) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      List<Member> members = sqlSession.selectList("MemberDao.findByName", name);
      if (members.size() > 0) {
        return members.get(0);
      } else {
        return null;
      }
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("MemberDao.findAll");
    }
  }

  @Override
  public int update(Member member) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
      return sqlSession.update("MemberDao.update", member);
    }
  }

  @Override
  public List<Member> findByProjectNo(int projectNo) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("MemberDao.findByProjectNo", projectNo);
    }
  }

  @Override
  public Member findByEmailPassword(String email, String password) throws Exception {
    HashMap<String,Object> map = new HashMap<>();
    map.put("email", email);
    map.put("password", password);

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("MemberDao.findByEmailPassword", map);
    }
  }
}
