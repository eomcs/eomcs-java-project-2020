package com.eomcs.pms.handler;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

public class ProjectListCommand implements Command {

  ProjectService projectService;

  public ProjectListCommand(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();

    out.println("[프로젝트 목록]");

    try {
      List<Project> list = projectService.list();
      out.println("번호, 프로젝트명, 시작일 ~ 종료일, 관리자, 팀원");

      for (Project project : list) {
        StringBuilder members = new StringBuilder();
        for (Member member : project.getMembers()) {
          if (members.length() > 0) {
            members.append(",");
          }
          members.append(member.getName());
        }

        out.printf("%d, %s, %s ~ %s, %s, [%s]\n",
            project.getNo(),
            project.getTitle(),
            project.getStartDate(),
            project.getEndDate(),
            project.getOwner().getName(),
            members.toString());
      }
    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
