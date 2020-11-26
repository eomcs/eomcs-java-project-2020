package com.eomcs.pms.web;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@CommandAnno("/member/list")
public class MemberListCommand implements Command {

  MemberService memberService;

  public MemberListCommand(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();

    out.println("[회원 목록]");

    try {
      List<Member> list = memberService.list();

      out.println("번호, 이름, 이메일, 전화, 등록일");
      for (Member member : list) {
        out.printf("%d, %s, %s, %s, %s\n",
            member.getNo(),
            member.getName(),
            member.getEmail(),
            member.getTel(),
            member.getRegisteredDate());
      }
    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
