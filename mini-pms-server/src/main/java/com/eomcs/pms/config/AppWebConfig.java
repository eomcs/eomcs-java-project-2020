package com.eomcs.pms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.UrlPathHelper;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.web.interceptor.AuthInterceptor;
import com.eomcs.pms.web.interceptor.AutoLoginInterceptor;

@ComponentScan("com.eomcs.pms.web")
@EnableWebMvc
public class AppWebConfig implements WebMvcConfigurer {

  @Autowired MemberService memberService;

  @Bean
  public ViewResolver viewResolver() {
    return new InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    CommonsMultipartResolver mr = new CommonsMultipartResolver();
    mr.setMaxUploadSize(10000000);
    mr.setMaxInMemorySize(2000000);
    mr.setMaxUploadSizePerFile(5000000);
    return mr;
  }

  // @MatrixVariable 애노테이션 처리를 활성화시킨다.
  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    UrlPathHelper helper = new UrlPathHelper();
    helper.setRemoveSemicolonContent(false);
    configurer.setUrlPathHelper(helper);
  }

  // 프론트 컨트롤러에 적용할 인터셉터 설정하기
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 인터셉터 실행은 등록 순서이다.

    // 자동 로그인을 수행하는 인터셉터를 삽입한다.
    registry.addInterceptor(new AutoLoginInterceptor(memberService));

    // 모든 "/app/*" 요청에 대해 로그인 여부를 검사하는 인터셉터를 삽입한다.
    registry.addInterceptor(new AuthInterceptor());
  }
}


