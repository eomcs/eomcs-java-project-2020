package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@WebServlet("/member/detail")
public class MemberDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    MemberService memberService =
        (MemberService) ctx.getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      Member member = memberService.get(no);
      if (member == null) {
        throw new Exception("해당 회원이 없습니다!");
      }
      request.setAttribute("member", member);
      request.setAttribute("viewName", "/member/detail.jsp");

    } catch (Exception e) {
      request.setAttribute("exception", e);
    }
  }
}
