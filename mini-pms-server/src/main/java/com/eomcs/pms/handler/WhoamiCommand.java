package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Member;

// Command 규칙에 따라 클래스를 정의한다.
public class WhoamiCommand implements Command {

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    Member member = (Member) context.get("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }
    out.printf("사용자 번호: %d\n", member.getNo());
    out.printf("이름: %s\n", member.getName());
    out.printf("이메일: %s\n", member.getEmail());
    out.printf("사진: %s\n", member.getPhoto());
    out.printf("전화: %s\n", member.getTel());
    out.printf("등록일: %s\n", member.getRegisteredDate());
  }
}
