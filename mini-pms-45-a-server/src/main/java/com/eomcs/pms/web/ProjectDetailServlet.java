package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/project/detail")
public class ProjectDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService = (ProjectService) ctx.getAttribute("projectService");
    MemberService memberService = (MemberService) ctx.getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");

    try {
      int no = Integer.parseInt(request.getParameter("no"));
      request.setAttribute("project", projectService.get(no));
      request.setAttribute("members", memberService.list());
      request.getRequestDispatcher("/project/detail.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
