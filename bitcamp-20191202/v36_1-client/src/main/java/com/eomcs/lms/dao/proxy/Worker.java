package com.eomcs.lms.dao.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// DaoProxyHelper가 작업을 수행할 때 일시적으로 사용하는 도구
// => 이런 관계를 UML에서는 "의존 관계(Dependency)"라 부른다.
// => UML class diagram
// [DapProxyHelper]-------->[Worker]
//
public interface Worker {
  Object execute(ObjectInputStream in, ObjectOutputStream out) throws Exception;
}


