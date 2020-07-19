package com.eomcs.lms;

import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Spring IoC 컨테이너가 이 클래스를 Java Config로 자동 인식하려면
// 다음 태그를 붙여야 한다.
// 단, 이 클래스가 @ComponentScan 에서 지정한 패키지 안에 있어야 한다.
@Configuration

// 다음 애노테이션을 선언하면,
// @Transactional 이 붙은 메서드가 있을 경우
// 트랜잭션 제어 코드가 삽입된 프록시 객체를 자동생성한다.
@EnableTransactionManagement

// Spring IoC 컨테이너에서 사용할 Properties 파일을 로딩하기
@PropertySource("classpath:com/eomcs/lms/conf/jdbc.properties")
public class DatabaseConfig {

  static Logger logger = LogManager.getLogger(DatabaseConfig.class);

  // @PropertySource로 로딩한 .properties 파일의 값을 사용하고 싶다면,
  // 다음 애노테이션을 인스턴스 필드 앞에 붙여라.
  // Spring IoC 컨테이너가 이 클래스의 객체를 생성할 때
  // 해당 필드에 프로퍼티 값을 자동으로 주입할 것이다.
  @Value("${jdbc.driver}")
  String jdbcDriver;

  @Value("${jdbc.url}")
  String jdbcUrl;

  @Value("${jdbc.username}")
  String jdbcUsername;

  @Value("${jdbc.password}")
  String jdbcPassword;

  public DatabaseConfig() {
    logger.debug("DatabaseConfig 객체 생성!");
  }

  // Spring IoC 컨테이너에 수동으로 객체를 등록하고 싶다면,
  // 그 객체를 만들어 주는 팩토리 메서드를 정의해야 한다.
  // => 단 메서드 선언부에 @Bean 애노테이션을 붙여야 한다.
  // => 그래야만 Spring IoC 컨테이너는
  // 이 메서드를 호출하고 그 리턴 값을 보관한다.

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(jdbcDriver);
    ds.setUrl(jdbcUrl);
    ds.setUsername(jdbcUsername);
    ds.setPassword(jdbcPassword);
    return ds;
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      // 필요한 값이 있다면 이렇게 파라미터로 선언만 하라.
      // 단 IoC 컨테이너에 들어 있는 값이어야 한다.
      DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}


