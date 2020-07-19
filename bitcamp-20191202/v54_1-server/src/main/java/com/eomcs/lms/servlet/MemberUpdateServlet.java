package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;
import com.eomcs.util.Prompt;
import com.eomcs.util.RequestMapping;

@Component
public class MemberUpdateServlet {

  MemberService memberService;

  public MemberUpdateServlet(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/member/update")
  public void service(Scanner in, PrintStream out) throws Exception {
    int no = Prompt.getInt(in, out, "번호? ");

    Member old = memberService.get(no);
    if (old == null) {
      out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    Member member = new Member();

    member.setNo(no);
    member.setName(Prompt.getString(in, out, //
        String.format("이름(%s)? ", old.getName())));
    member.setEmail(Prompt.getString(in, out, //
        String.format("이메일(%s)? ", old.getEmail())));
    member.setPassword(Prompt.getString(in, out, "암호? "));
    member.setPhoto(Prompt.getString(in, out, //
        String.format("사진(%s)? ", old.getPhoto())));
    member.setTel(Prompt.getString(in, out, //
        String.format("전화(%s)? ", old.getTel())));

    if (memberService.update(member) > 0) {
      out.println("회원을 변경했습니다.");

    } else {
      out.println("변경에 실패했습니다.");
    }
  }
}
