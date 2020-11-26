package com.eomcs.pms.web;

import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@CommandAnno("/task/list")
public class TaskListCommand implements Command {

  TaskService taskService;

  public TaskListCommand(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();

    out.println("[작업 목록]");

    try {
      List<Task> list = taskService.list();
      out.println("번호, 작업내용, 마감일, 작업자, 상태");

      for (Task task : list) {
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
