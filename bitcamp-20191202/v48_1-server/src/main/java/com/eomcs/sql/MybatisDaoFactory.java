package com.eomcs.sql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MybatisDaoFactory {

  InvocationHandler invocationHandler;

  public MybatisDaoFactory(SqlSessionFactory sqlSessionFactory) {

    invocationHandler = (proxy, method, args) -> {
      // 이 메서드는 DAO 프록시의 메서드가 호출될 때마다 실행된다.
      // 할 일: SqlSession을 이용하여 SQL을 실행한다.
      // 준비물:
      // 1) 실행할 SQL의 namespace와 id
      // 2) select 목록/select 한 개/insert/update/delete 여부
      //
      // 동작원리:
      // 1) 인터페이스명과 메서드명을 이용하여 실행할 SQL 아이디를 알아낸다.
      // 2) 리턴 타입에 따라 호출할 메서드를 결정한다.
      // 3) 파라미터 유무에 따라 메서드에 넘길 항목을 결정한다.

      // => SQL ID 알아내기
      Class<?> clazz = proxy.getClass(); // 프록시 객체의 정보를 알아낸다.
      Class<?> daoInterface = clazz.getInterfaces()[0]; // 프록시 객체가 구현한 인터페이스 정보를 알아낸다.
      String interfaceName = daoInterface.getName();
      String methodName = method.getName();
      String sqlId = String.format("%s.%s", interfaceName, methodName);
      System.out.printf("SQL ID => %s\n", sqlId);

      // => 리턴 타입에 따라 메서드를 호출한다.
      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
        Class<?> returnType = method.getReturnType();
        if (returnType == List.class) {
          return (args == null) ? sqlSession.selectList(sqlId) : //
          sqlSession.selectList(sqlId, args[0]);
        } else if (returnType == int.class || returnType == void.class) {
          return (args == null) ? sqlSession.update(sqlId) : // update()는 insert(), delete() 과 같다.
          sqlSession.update(sqlId, args[0]);
        } else {
          return (args == null) ? sqlSession.selectOne(sqlId) : //
          sqlSession.selectOne(sqlId, args[0]);
        }
      }
    };
  }

  @SuppressWarnings("unchecked")
  public <T> T createDao(Class<T> daoInterface) {
    return (T) Proxy.newProxyInstance(//
        this.getClass().getClassLoader(), //
        new Class[] {daoInterface}, //
        invocationHandler);
  }
}
