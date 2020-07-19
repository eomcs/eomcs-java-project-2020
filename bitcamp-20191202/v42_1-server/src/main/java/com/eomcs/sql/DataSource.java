package com.eomcs.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DataSource {
  String jdbcUrl;
  String username;
  String password;

  // 스레드에 값을 보관하는 일을 할 도구 준비
  ThreadLocal<Connection> connectionLocal = new ThreadLocal<>();

  // 반납 받은 커넥션을 보관할 저장소를 준비한다.
  ArrayList<Connection> conList = new ArrayList<>();

  public DataSource(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection() throws Exception {

    // 먼저 스레드에 Connection 객체가 보관되어 있는지 알아 본다.
    Connection con = connectionLocal.get();
    if (con != null) { // 보관된게 있다면,
      System.out.println("스레드에 보관된 Connection 객체 리턴!");
      return con; // 보관된 Connection 객체를 리턴한다.
    }

    // 스레드에 보관된 Connection 객체가 없다면,
    // 기존에 반납한 Connection 객체가 있는지 검사한다.
    if (conList.size() > 0) {

      // 1) 있다면, 반납 받은 객체를 리턴한다.
      con = conList.remove(0);
      System.out.println("기존에 반납 받은 Connection 객체 재사용!");

    } else {
      // 2) 없다면, 새로 Connection 객체를 만들어 리턴한다.
      con = new ConnectionProxy(DriverManager.getConnection( //
          jdbcUrl, //
          username, //
          password));
      System.out.println("새 Connection 객체 생성!");
    }

    // 물론 리턴하기 전에 스레드에 Connection 객체를 보관한다.
    connectionLocal.set(con);

    System.out.printf("DataSource: 현재 보관중인 객체 %d개\n", conList.size());

    return con;
  }

  public Connection removeConnection() {
    // 스레드에 보관된 Connection 객체를 제거한다.
    Connection con = connectionLocal.get();
    if (con != null) {
      connectionLocal.remove();
      System.out.println("스레드에 보관된 Connection 객체 제거 했음!");

      // Connection 객체는 다시 사용할 수 있게 반납한다.
      conList.add(con);
    }
    System.out.printf("DataSource: 현재 보관중인 객체 %d개\n", conList.size());
    return con;
  }

  public void clean() {
    // 보관소에 저장된 모든 커넥션을 닫는다.
    while (conList.size() > 0) {
      try {
        ((ConnectionProxy) conList.remove(0)).realClose();
      } catch (Exception e) {
        // 커넥션을 닫다가 발생한 오류는 무시한다!
      }
    }
  }
}
