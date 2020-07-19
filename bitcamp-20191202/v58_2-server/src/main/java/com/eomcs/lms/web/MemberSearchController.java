package com.eomcs.lms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.service.MemberService;
import com.eomcs.util.RequestMapping;

@Component
public class MemberSearchController {

  @Autowired
  MemberService memberService;

  @RequestMapping("/member/search")
  public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String keyword = request.getParameter("keyword");
    List<Member> members = memberService.search(keyword);
    request.setAttribute("list", members);
    return "/member/search.jsp";
  }
}
