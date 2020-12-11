package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.service.ProjectService;

@Controller
public class ProjectDeleteController {

  ProjectService projectService;

  public ProjectDeleteController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @RequestMapping("/project/delete")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));
    if (projectService.delete(no) == 0) {
      throw new Exception("해당 프로젝트가 없습니다.");
    }

    return "redirect:list";
  }
}
