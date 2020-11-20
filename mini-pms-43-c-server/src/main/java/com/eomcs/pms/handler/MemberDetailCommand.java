package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Prompt;

@CommandAnno("/member/detail")
public class MemberDetailCommand implements Command {

  MemberService memberService;

  public MemberDetailCommand(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    BufferedReader in = request.getReader();

    try {
      out.println("[회원 상세보기]");
      int no = Prompt.inputInt("번호? ", out, in);

      Member member = memberService.get(no);

      if (member == null) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      out.printf("이름: %s\n", member.getName());
      out.printf("이메일: %s\n", member.getEmail());
      out.printf("사진: %s\n", member.getPhoto());
      out.printf("전화: %s\n", member.getTel());
      out.printf("등록일: %s\n", member.getRegisteredDate());

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
