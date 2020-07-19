package com.eomcs.sql;

public interface TransactionCallback {
  // 이 메서드는 TransactionTemplate이 호출한다.
  // => 이 메서드를 구현하는 클래스는
  // 이 메서드에 트랜잭션으로 묶어서 다룰 작업을 기술해야 한다.
  Object doInTransaction() throws Exception;
}
