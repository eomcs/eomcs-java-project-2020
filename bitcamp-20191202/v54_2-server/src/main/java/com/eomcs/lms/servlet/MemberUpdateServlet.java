package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;
import com.eomcs.util.RequestMapping;

@Component
public class MemberUpdateServlet {

  MemberService memberService;

  public MemberUpdateServlet(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/member/update")
  public void service(Map<String, String> params, PrintStream out) throws Exception {
    Member member = new Member();
    member.setNo(Integer.parseInt(params.get("no")));
    member.setName(params.get("name"));
    member.setEmail(params.get("email"));
    member.setPassword(params.get("password"));
    member.setPhoto(params.get("photo"));
    member.setTel(params.get("tel"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='2;url=/member/list'>");
    out.println("<title>회원 변경</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 변경 결과</h1>");

    if (memberService.update(member) > 0) {
      out.println("<p>회원을 변경했습니다.</p>");

    } else {
      out.println("<p>변경에 실패했습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
