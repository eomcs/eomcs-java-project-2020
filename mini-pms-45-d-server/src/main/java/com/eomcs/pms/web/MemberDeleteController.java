package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.service.MemberService;

@RequestMapping("/member/delete")
public class MemberDeleteController implements Controller {

  MemberService memberService;

  public MemberDeleteController(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));
    if (memberService.delete(no) == 0) {
      throw new Exception("해당 번호의 회원이 없습니다.");

    }
    return "redirect:list";
  }
}
