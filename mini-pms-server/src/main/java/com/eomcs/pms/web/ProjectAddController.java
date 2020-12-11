package com.eomcs.pms.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@Controller
public class ProjectAddController {

  ProjectService projectService;

  public ProjectAddController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @RequestMapping("/project/add")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Project project = new Project();
    project.setTitle(request.getParameter("title"));
    project.setContent(request.getParameter("content"));
    project.setStartDate(Date.valueOf(request.getParameter("startDate")));
    project.setEndDate(Date.valueOf(request.getParameter("endDate")));

    HttpSession session = request.getSession();
    Member loginUser = (Member) session.getAttribute("loginUser");
    project.setOwner(loginUser);

    List<Member> members = new ArrayList<>();
    String[] memberNoList = request.getParameterValues("members");
    if (memberNoList != null) {
      for (String memberNo : memberNoList) {
        members.add(new Member().setNo(Integer.parseInt(memberNo)));
      }
    }
    project.setMembers(members);

    projectService.add(project);
    return "redirect:list";
  }
}
