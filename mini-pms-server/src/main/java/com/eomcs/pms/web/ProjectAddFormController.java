package com.eomcs.pms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
public class ProjectAddFormController {

  MemberService memberService;

  public ProjectAddFormController(MemberService memberService) {
    this.memberService = memberService;
  }

  @RequestMapping("/project/form")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    List<Member> members = memberService.list();
    request.setAttribute("members", members);
    return "/project/form.jsp";
  }
}
