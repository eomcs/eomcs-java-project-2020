package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.service.ProjectService;

@RequestMapping("/project/delete")
public class ProjectDeleteController implements Controller {

  ProjectService projectService;

  public ProjectDeleteController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));
    if (projectService.delete(no) == 0) {
      throw new Exception("해당 프로젝트가 없습니다.");
    }

    return "redirect:list";
  }
}
