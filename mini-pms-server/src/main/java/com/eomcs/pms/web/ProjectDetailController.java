package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@Controller
public class ProjectDetailController {

  ProjectService projectService;
  MemberService memberService;
  TaskService taskService;

  public ProjectDetailController(
      ProjectService projectService,
      MemberService memberService,
      TaskService taskService) {
    this.projectService = projectService;
    this.memberService = memberService;
    this.taskService = taskService;
  }

  @RequestMapping("/project/detail")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");

    int no = Integer.parseInt(request.getParameter("no"));
    Project project = projectService.get(no);
    if (project == null) {
      throw new Exception("해당 프로젝트가 없습니다!");
    }

    request.setAttribute("project", project);
    request.setAttribute("members", memberService.list());
    request.setAttribute("tasks", taskService.listByProject(no));
    return "/project/detail.jsp";
  }
}
