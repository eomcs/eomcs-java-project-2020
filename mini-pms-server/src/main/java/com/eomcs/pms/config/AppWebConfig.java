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
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.util.UrlPathHelper;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.web.interceptor.AuthInterceptor;
import com.eomcs.pms.web.interceptor.AutoLoginInterceptor;

@ComponentScan("com.eomcs.pms.web")
@EnableWebMvc
public class AppWebConfig implements WebMvcConfigurer {

  @Autowired MemberService memberService;

  //  @Bean
  //  public ViewResolver viewResolver() {
  //    return new InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
  //  }

  // Tiles 를 사용하여 JSP를 실행할 ViewResolver를 등록한다.
  @Bean
  public ViewResolver tilesViewResolver() {
    UrlBasedViewResolver vr = new UrlBasedViewResolver();

    // 페이지 컨트롤러가 리턴한 URL 끝에 붙일 접미사를 지정한다.
    vr.setSuffix(".app");

    // JSP를 실행할 View 객체를 지정한다.
    // => 보통 JSP를 처리하는 기본 뷰는 JstlView 클래스이다.
    // => 지금 우리는 Tiles 기술을 기반으로 JSP를 처리해야 하기 때문에
    //    JstlView 대신 TilesView를 설정해야 한다.
    vr.setViewClass(TilesView.class);

    // ViewResolver 가 여러 개 있을 경우 어느 것을 우선으로 사용할 것인지 지정한다.
    vr.setOrder(1);

    return vr;
  }

  // TilesView 객체가 사용할 설정 관리자를 준비한다.
  @Bean
  public TilesConfigurer tilesConfigurer() {
    TilesConfigurer configurer = new TilesConfigurer();
    // Tiles 설정 파일의 경로를 지정한다.
    configurer.setDefinitions("/WEB-INF/tiles/defs/tiles.xml");
    return configurer;
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


