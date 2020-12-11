package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@RequestMapping("/member/list")
public class MemberListController implements Controller {

  MemberService memberService;

  public MemberListController(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    List<Member> list = memberService.list();

    request.setAttribute("list", list);
    return "/member/list.jsp";
  }
}
