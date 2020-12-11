package com.eomcs.pms.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@Controller
public class ProjectUpdateController {

  ProjectService projectService;

  public ProjectUpdateController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @RequestMapping("/project/update")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

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
    return "redirect:list";
  }
}
