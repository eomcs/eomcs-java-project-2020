package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Prompt;

@CommandAnno("/project/delete")
public class ProjectDeleteCommand implements Command {

  ProjectService projectService;

  public ProjectDeleteCommand(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    BufferedReader in = request.getReader();

    try {
      out.println("[프로젝트 삭제]");
      int no = Prompt.inputInt("번호? ", out, in);

      String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("프로젝트 삭제를 취소하였습니다.");
        return;
      }

      if (projectService.delete(no) == 0) {
        out.println("해당 번호의 프로젝트가 없습니다.");
        return;
      }

      out.println("프로젝트를 삭제하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
