package com.eomcs.pms.web;

import java.io.BufferedReader;
import java.io.PrintWriter;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Prompt;

@CommandAnno("/task/delete")
public class TaskDeleteCommand implements Command {

  TaskService taskService;

  public TaskDeleteCommand(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public void execute(Request request) {
    PrintWriter out = request.getWriter();
    BufferedReader in = request.getReader();

    try {
      out.println("[작업 삭제]");
      int no = Prompt.inputInt("번호? ", out, in);

      String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("작업 삭제를 취소하였습니다.");
        return;
      }

      if (taskService.delete(no) == 0) {
        out.println("해당 번호의 작업이 존재하지 않습니다.");
        return ;
      }

      out.println("작업을 삭제하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
