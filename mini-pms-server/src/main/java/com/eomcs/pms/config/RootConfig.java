package com.eomcs.pms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

// Spring WebMVC 프레임워크에서 ContextLoaderListener 가 사용할 Java Config 이다.
// 웹 컴포넌트가 공유할 객체를 선언한다.
// 예) DAO, Service 등
//
@ComponentScan(
    value="com.eomcs.pms",
    excludeFilters = {
        @Filter(type = FilterType.REGEX, pattern = "com.eomcs.pms.web.*")
    })
public class RootConfig {

}
