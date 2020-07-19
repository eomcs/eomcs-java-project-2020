package com.eomcs.sql;

import java.sql.Connection;

public class PlatformTransactionManager {

  DataSource dataSource;

  public PlatformTransactionManager(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void beginTransaction() throws Exception {
    // 현재 스레드에 보관된 커넥션을 가져온다.
    Connection con = dataSource.getConnection();

    // 커넥션을 수동 커밋 상태로 만든다.
    con.setAutoCommit(false);
  }

  public void commit() throws Exception {
    Connection con = dataSource.getConnection();
    con.commit();
    con.setAutoCommit(true);
  }

  public void rollback() throws Exception {
    Connection con = dataSource.getConnection();
    con.rollback();
    con.setAutoCommit(true);
  }
}
