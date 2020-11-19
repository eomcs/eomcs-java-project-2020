package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

// 사용자의 명령을 처리하는 객체에 대해 호출할 메서드 규칙을 정의 한다.
public interface Command {
  // 클라이언트 요청 처리와 관련된 객체들 간에
  // 데이터 교환을 위해 맵 객체를 파라미터로 추가한다.
  void execute(PrintWriter out, BufferedReader in, Map<String,Object> context);
}
