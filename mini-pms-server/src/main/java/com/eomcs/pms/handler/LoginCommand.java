package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Prompt;

@CommandAnno("/login")
public class LoginCommand implements Command {

  MemberService memberService;

  public LoginCommand(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    BufferedReader in = request.getReader();
    Map<String,Object> session = request.getSession();

    out.println("[로그인]");

    if (session.get("loginUser") != null) {
      out.println("로그인 되어 있습니다!");
      return;
    }

    try {
      String email = Prompt.inputString("이메일? ", out, in);
      String password = Prompt.inputString("암호? ", out, in);

      Member member = memberService.get(email, password);
      if (member == null) {
        out.println("사용자 정보가 맞지 않습니다.");
      } else {
        // 로그인이 성공했으면 회원 정보를
        // 각 클라이언트의 전용 보관소인 session에 저장한다.
        session.put("loginUser", member);
        out.printf("%s 님 반갑습니다.\n", member.getName());
      }

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
