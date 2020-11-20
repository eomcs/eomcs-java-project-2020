package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Member;

// Command 규칙에 따라 클래스를 정의한다.
public class LogoutCommand implements Command {

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    Member loginUser = (Member) context.get("loginUser");
    if (loginUser == null) {
      out.println("로그인 된 상태가 아닙니다!");
      return;
    }

    out.printf("%s 님 안녕히 가세요!\n", loginUser.getName());

    context.remove("loginUser");
  }
}
