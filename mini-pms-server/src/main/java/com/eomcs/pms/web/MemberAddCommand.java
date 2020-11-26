package com.eomcs.pms.web;

import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Prompt;

@CommandAnno("/member/add")
public class MemberAddCommand implements Command {

  MemberService memberService;

  public MemberAddCommand(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    BufferedReader in = request.getReader();

    try {
      out.println("[회원 등록]");

      Member member = new Member();
      member.setName(Prompt.inputString("이름? ", out, in));
      member.setEmail(Prompt.inputString("이메일? ", out, in));
      member.setPassword(Prompt.inputString("암호? ", out, in));
      member.setPhoto(Prompt.inputString("사진? ", out, in));
      member.setTel(Prompt.inputString("전화? ", out, in));

      memberService.add(member);
      System.out.println("회원을 등록하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
