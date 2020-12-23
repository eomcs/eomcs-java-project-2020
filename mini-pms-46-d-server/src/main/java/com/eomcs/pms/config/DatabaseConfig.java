package com.eomcs.pms.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@PropertySource("classpath:com/eomcs/pms/config/jdbc.properties")

// @Transactional 애노테이션을 처리할 객체를 활성화시킨다.
// => 그래야만 @Transactional 애노테이션이 동작될 것이다.
@EnableTransactionManagement

public class DatabaseConfig {

  @Bean
  public DataSource dataSource(
      @Value("${jdbc.driver}") String jdbcDriver,
      @Value("${jdbc.url}") String jdbcUrl,
      @Value("${jdbc.username}") String jdbcUsername,
      @Value("${jdbc.password}") String jdbcPassword) {
    // DB 커넥션풀 객체 생성
    // => DB 커넥션을 생성한 후 내부 버퍼에 보관해 둔다.
    // => 요청할 때 빌려주고, 사용 후 반납 받는다.
    // => 그래서 DB 커넥션을 매번 생성하지 않게 한다.
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(jdbcDriver);
    ds.setUrl(jdbcUrl);
    ds.setUsername(jdbcUsername);
    ds.setPassword(jdbcPassword);
    return ds;
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      DataSource dataSource) {
    // 트랜잭션 관리자 생성
    // => commit/rollback 을 다룬다.
    return new DataSourceTransactionManager(dataSource);
  }
}


