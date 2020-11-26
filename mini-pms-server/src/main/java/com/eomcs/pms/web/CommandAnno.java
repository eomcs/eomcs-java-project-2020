package com.eomcs.pms.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 커맨드 객체에 붙일 특별한 주석
// => 커맨드의 이름을 설정하는 용도이다.
// => JVM에서 객체를 생성할 때 사용할 것이다.
//
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAnno {
  String value(); // 이름을 저장하는 프로퍼티
}
