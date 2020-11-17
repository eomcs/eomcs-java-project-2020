package com.eomcs.pms.handler;

import java.util.Map;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Prompt;

public class ProjectDeleteCommand implements Command {

  ProjectService projectService;

  public ProjectDeleteCommand(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[프로젝트 삭제]");
    int no = Prompt.inputInt("번호? ");

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }

    try {
      if (projectService.delete(no) == 0) {
        System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
        return;
      }
      System.out.println("프로젝트를 삭제하였습니다.");

    } catch (Exception e) {
      System.out.println("프로젝트 삭제 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
