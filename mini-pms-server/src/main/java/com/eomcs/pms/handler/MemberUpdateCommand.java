package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import com.eomcs.util.Prompt;

public class MemberUpdateCommand implements Command {

  MemberService memberService;

  public MemberUpdateCommand(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    try {
      out.println("[회원 변경]");
      int no = Prompt.inputInt("번호? ", out, in);
      Member member = memberService.get(no);

      if (member == null) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      member.setName(Prompt.inputString(
          String.format("이름(%s)? ", member.getName()), out, in));
      member.setEmail(Prompt.inputString(
          String.format("이메일(%s)? ", member.getEmail()), out, in));
      member.setPassword(Prompt.inputString("암호? ", out, in));
      member.setPhoto(Prompt.inputString(
          String.format("사진(%s)? ", member.getPhoto()), out, in));
      member.setTel(Prompt.inputString(
          String.format("전화(%s)? ", member.getTel()), out, in));

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("회원 변경을 취소하였습니다.");
        return;
      }

      if (memberService.update(member) == 0) {
        out.println("해당 번호의 회원이 존재하지 않습니다.");
      } else {
        out.println("회원을 변경하였습니다.");
      }

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
