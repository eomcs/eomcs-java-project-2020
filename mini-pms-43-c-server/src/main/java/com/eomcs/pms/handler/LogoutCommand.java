package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Member;

@CommandAnno("/logout")
public class LogoutCommand implements Command {

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    Map<String,Object> session = request.getSession();

    Member loginUser = (Member) session.get("loginUser");
    if (loginUser == null) {
      out.println("로그인 된 상태가 아닙니다!");
      return;
    }

    out.printf("%s 님 안녕히 가세요!\n", loginUser.getName());

    request.invalidateSession();
  }
}
