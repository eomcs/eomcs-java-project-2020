package com.eomcs.pms.handler;

import java.util.Map;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Prompt;

public class MemberAddCommand implements Command {

  MemberService memberService;

  public MemberAddCommand(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[회원 등록]");

    Member member = new Member();
    member.setName(Prompt.inputString("이름? "));
    member.setEmail(Prompt.inputString("이메일? "));
    member.setPassword(Prompt.inputString("암호? "));
    member.setPhoto(Prompt.inputString("사진? "));
    member.setTel(Prompt.inputString("전화? "));

    try {
      memberService.add(member);
      System.out.println("회원을 등록하였습니다.");

    } catch (Exception e) {
      System.out.println("회원 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
