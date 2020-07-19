package com.eomcs.lms.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// AppWebApplicationInitializer가 설정하는
// DispatcherServlet(/app/* 요청 처리)의 IoC 컨테이너를 위한 설정이다.
//
@ComponentScan(value = "com.eomcs.lms.web")
@EnableWebMvc
public class AppWebConfig {

  static Logger logger = LogManager.getLogger(AppWebConfig.class);

  public AppWebConfig() {
    logger.debug("AppWebConfig 객체 생성!");
  }

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver vr = new InternalResourceViewResolver(//
        "/WEB-INF/jsp/", // prefix
        ".jsp" // suffix
    );
    return vr;
  }

  @Bean
  public MultipartResolver multipartResolver() {
    CommonsMultipartResolver mr = new CommonsMultipartResolver();
    mr.setMaxUploadSize(10000000);
    mr.setMaxInMemorySize(2000000);
    mr.setMaxUploadSizePerFile(5000000);
    return mr;
  }
}


