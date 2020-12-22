package com.eomcs.pms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// Spring WebMVC 프레임워크에서 ContextLoaderListener 가 사용할 Java Config 이다.
// 웹 컴포넌트가 공유할 객체를 선언한다.
// 예) DAO, Service 등
//
@ComponentScan(
    value="com.eomcs.pms",
    excludeFilters = {
        @Filter(type = FilterType.REGEX, pattern = "com.eomcs.pms.web.*")
    })

// 스케줄링 기능을 다루는 @Scheduled, @Async 애노테이션 처리를 활성화시킨다.
@EnableAsync
@EnableScheduling

public class RootConfig {

}
