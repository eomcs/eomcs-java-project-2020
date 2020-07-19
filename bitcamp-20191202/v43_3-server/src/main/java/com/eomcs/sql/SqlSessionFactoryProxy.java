package com.eomcs.sql;

import java.sql.Connection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class SqlSessionFactoryProxy implements SqlSessionFactory {

  SqlSessionFactory originalFactory;

  // SqlSession을 스레드에 보관할 저장소를 준비한다.
  ThreadLocal<SqlSession> sqlSessionLocal = new ThreadLocal<>();

  public SqlSessionFactoryProxy(SqlSessionFactory originalFactory) {
    this.originalFactory = originalFactory;
  }

  // 스레드 작업이 끝난 후 SqlSession 객체를 제거하고 닫는다.
  public void closeSession() {
    // 스레드에서 꺼낸다.
    SqlSession sqlSession = sqlSessionLocal.get();
    if (sqlSession != null) {
      sqlSessionLocal.remove(); // 스레드에서 제거
      // 이제 진짜로 SqlSession을 닫는다.
      ((SqlSessionProxy) sqlSession).realClose();
    }
  }

  @Override
  public SqlSession openSession() {
    // 기본으로 자동 커밋으로 동작하는 SqlSession을 만들어 리턴한다.
    return this.openSession(true);
  }

  @Override
  public SqlSession openSession(boolean autoCommit) {
    // 스레드에 보관된 것을 꺼낸다.
    SqlSession sqlSession = sqlSessionLocal.get();

    if (sqlSession == null) {
      // 스레드에 보관된 게 없다면 새로 만든다.
      sqlSession = new SqlSessionProxy(originalFactory.openSession(autoCommit));

      // 나중에 다른 곳에서 사용하도록 스레드에 보관한다.
      sqlSessionLocal.set(sqlSession);
    }

    return sqlSession;
  }

  @Override
  public SqlSession openSession(Connection connection) {
    return originalFactory.openSession(connection);
  }

  @Override
  public SqlSession openSession(TransactionIsolationLevel level) {
    return originalFactory.openSession(level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType) {
    return originalFactory.openSession(execType);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
    return originalFactory.openSession(execType, autoCommit);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
    return originalFactory.openSession(execType, level);
  }

  @Override
  public SqlSession openSession(ExecutorType execType, Connection connection) {
    return originalFactory.openSession(execType, connection);
  }

  @Override
  public Configuration getConfiguration() {
    return originalFactory.getConfiguration();
  }


}
