package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Map;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Prompt;

public class ProjectUpdateCommand implements Command {

  ProjectService projectService;

  public ProjectUpdateCommand(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    try {
      out.println("[프로젝트 변경]");
      int no = Prompt.inputInt("번호? ", out, in);

      Project project = projectService.get(no);

      if (project == null) {
        out.println("해당 번호의 프로젝트가 없습니다.");
        return;
      }

      String value = Prompt.inputString(String.format(
          "프로젝트명(%s)? ", project.getTitle()), out, in);
      if (value.length() > 0) {
        project.setTitle(value);
      }

      value = Prompt.inputString(String.format(
          "내용(%s)? ", project.getContent()), out, in);
      if (value.length() > 0) {
        project.setContent(value);
      }

      value = Prompt.inputString(String.format(
          "시작일(%s)? ", project.getStartDate()), out, in);
      if (value.length() > 0) {
        project.setStartDate(Date.valueOf(value));
      }

      value = Prompt.inputString(String.format(
          "종료일(%s)? ", project.getEndDate()), out, in);
      if (value.length() > 0) {
        project.setEndDate(Date.valueOf(value));
      }

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!response.equalsIgnoreCase("y")) {
        out.println("프로젝트 변경을 취소하였습니다.");
        return;
      }

      if (projectService.update(project) == 0) {
        out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
        return;
      }

      out.println("프로젝트를 변경하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
