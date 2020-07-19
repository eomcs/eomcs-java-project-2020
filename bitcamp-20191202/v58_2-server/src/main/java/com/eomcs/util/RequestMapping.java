package com.eomcs.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 실행 중에 메서드에 붙은 애노테이션 정보를 추출해야 한다.
@Retention(RetentionPolicy.RUNTIME)

// 메서드에만 이 애노테이션을 붙일 수 있도록 사용 범위를 제한한다.
@Target({ElementType.METHOD})

public @interface RequestMapping {
  // 서블릿 메서드와 명령어를 연결하기 위해,
  // 명령어를 저장할 프로퍼티를 정의한다.
  String value();
}
