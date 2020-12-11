package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.pms.domain.Member;

@RequestMapping("/auth/logout")
public class LogoutController implements Controller {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession();

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      session.invalidate();
    }

    response.setContentType("text/html;charset=UTF-8");
    request.setAttribute("loginUser", loginUser);
    return "/auth/logout.jsp";
  }
}
