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
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/project/update")
public class ProjectUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService =
        (ProjectService) ctx.getAttribute("projectService");

    try {
      Project project = new Project();
      project.setNo(Integer.parseInt(request.getParameter("no")));
      project.setTitle(request.getParameter("title"));
      project.setContent(request.getParameter("content"));
      project.setStartDate(Date.valueOf(request.getParameter("startDate")));
      project.setEndDate(Date.valueOf(request.getParameter("endDate")));

      // 프로젝트에 참여할 회원 정보를 담는다.
      List<Member> members = new ArrayList<>();
      String[] memberNoList = request.getParameterValues("members");
      if (memberNoList != null) {
        for (String memberNo : memberNoList) {
          members.add(new Member().setNo(Integer.parseInt(memberNo)));
        }
      }
      project.setMembers(members);

      if (projectService.update(project) == 0) {
        throw new Exception("해당 프로젝트가 존재하지 않습니다.");
      }

      response.sendRedirect("list");

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
