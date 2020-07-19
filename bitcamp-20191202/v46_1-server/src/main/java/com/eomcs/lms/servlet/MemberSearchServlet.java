package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;
import com.eomcs.util.Component;
import com.eomcs.util.Prompt;

@Component("/member/search")
public class MemberSearchServlet implements Servlet {

  MemberService memberService;

  public MemberSearchServlet(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    String keyword = Prompt.getString(in, out, "검색어? ");

    List<Member> members = memberService.search(keyword);
    for (Member m : members) {
      out.printf("%d, %s, %s, %s, %s\n", //
          m.getNo(), //
          m.getName(), //
          m.getEmail(), //
          m.getTel(), //
          m.getRegisteredDate());
    }
  }
}
