package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@WebServlet("/project/detail")
public class ProjectDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService = (ProjectService) ctx.getAttribute("projectService");
    MemberService memberService = (MemberService) ctx.getAttribute("memberService");
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    response.setContentType("text/html;charset=UTF-8");

    try {
      int no = Integer.parseInt(request.getParameter("no"));
      Project project = projectService.get(no);
      if (project == null) {
        throw new Exception("해당 프로젝트가 없습니다!");
      }

      request.setAttribute("project", project);
      request.setAttribute("members", memberService.list());
      request.setAttribute("tasks", taskService.listByProject(no));
      request.setAttribute("viewName", "/project/detail.jsp");

    } catch (Exception e) {
      request.setAttribute("exception", e);
    }
  }
}
