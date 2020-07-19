package com.eomcs.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 클래스 이름을 지정할 때 붙일 애노테이션을 정의한다.
// => 컴파일 한 후에도 .class 파일에 이 애노테이션 정보를 유지하게 한다.
// => JVM에서 이 애노테이션 정보를 꺼낼 수 있도록 허락한다.
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
  // 이 애노테이션을 적용할 때
  // 컴포넌트의 이름을 지정할 수 있도록 프로퍼티를 선언한다.
  String value() default ""; // 만약 값을 지정하지 않으면 빈 문자열이 설정된다.
}
