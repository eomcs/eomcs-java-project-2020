package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 프론트 컨트롤러가 페이지 컨트롤러를 사용할 때
// 호출하는 메서드 규칙
public interface Controller {
  String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
