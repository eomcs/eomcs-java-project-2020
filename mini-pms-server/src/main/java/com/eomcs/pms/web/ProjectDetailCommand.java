package com.eomcs.pms.web;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Prompt;

@CommandAnno("/project/detail")
public class ProjectDetailCommand implements Command {

  ProjectService projectService;
  TaskService taskService;

  public ProjectDetailCommand(
      ProjectService projectService,
      TaskService taskService) {
    this.projectService = projectService;
    this.taskService = taskService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    BufferedReader in = request.getReader();

    try {
      out.println("[프로젝트 상세보기]");
      int no = Prompt.inputInt("번호? ", out, in);

      Project project = projectService.get(no);

      if (project == null) {
        out.println("해당 번호의 프로젝트가 없습니다.");
        return;
      }

      out.printf("프로젝트명: %s\n", project.getTitle());
      out.printf("내용: %s\n", project.getContent());
      out.printf("기간: %s ~ %s\n",
          project.getStartDate(),
          project.getEndDate());
      out.printf("관리자: %s\n", project.getOwner().getName());
      out.print("팀원: ");
      project.getMembers().forEach(
          member -> out.print(member.getName() + " "));
      out.println();

      out.println("작업:");
      out.println("--------------------------------");

      List<Task> tasks = taskService.listByProject(no);

      out.println("번호, 작업내용, 마감일, 작업자, 상태");
      for (Task task : tasks) {
        String stateLabel = null;
        switch (task.getStatus()) {
          case 1:
            stateLabel = "진행중";
            break;
          case 2:
            stateLabel = "완료";
            break;
          default:
            stateLabel = "신규";
        }
        out.printf("%d, %s, %s, %s, %s\n",
            task.getNo(),
            task.getContent(),
            task.getDeadline(),
            task.getOwner().getName(),
            stateLabel);
      }

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
