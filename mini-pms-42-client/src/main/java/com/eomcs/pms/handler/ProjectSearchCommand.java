package com.eomcs.pms.handler;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Prompt;

public class ProjectSearchCommand implements Command {

  ProjectService projectService;

  public ProjectSearchCommand(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[프로젝트 검색]");

    try {
      String keyword = Prompt.inputString("검색어? ");

      List<Project> list = projectService.list(keyword);
      System.out.println("번호, 프로젝트명, 시작일 ~ 종료일, 관리자, 팀원");

      for (Project project : list) {
        StringBuilder members = new StringBuilder();
        for (Member member : project.getMembers()) {
          if (members.length() > 0) {
            members.append(",");
          }
          members.append(member.getName());
        }

        System.out.printf("%d, %s, %s ~ %s, %s, [%s]\n",
            project.getNo(),
            project.getTitle(),
            project.getStartDate(),
            project.getEndDate(),
            project.getOwner().getName(),
            members.toString());
      }
    } catch (Exception e) {
      System.out.println("프로젝트 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
