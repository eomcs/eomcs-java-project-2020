package com.eomcs.pms.web;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/project/add")
public class ProjectAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService =
        (ProjectService) ctx.getAttribute("projectService");

    try {
      Project project = new Project();
      project.setTitle(request.getParameter("title"));
      project.setContent(request.getParameter("content"));
      project.setStartDate(Date.valueOf(request.getParameter("startDate")));
      project.setEndDate(Date.valueOf(request.getParameter("endDate")));

      HttpSession session = request.getSession();
      Member loginUser = (Member) session.getAttribute("loginUser");
      project.setOwner(loginUser);

      // 프로젝트에 참여할 회원 정보를 담는다.
      List<Member> members = new ArrayList<>();
      String[] memberNoList = request.getParameterValues("members");
      if (memberNoList != null) {
        for (String memberNo : memberNoList) {
          //          Member member = new Member();
          //          member.setNo(Integer.parseInt(memberNo));
          //          members.add(member);
          members.add(new Member().setNo(Integer.parseInt(memberNo)));
        }
      }
      project.setMembers(members);

      projectService.add(project);
      response.sendRedirect("list");

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
