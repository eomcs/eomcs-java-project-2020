package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDetailCommand implements Command {

  List<Task> taskList;

  public TaskDetailCommand(List<Task> list) {
    this.taskList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[작업 상세보기]");
      int no = Prompt.inputInt("번호? ", out, in);
      Task task = findByNo(no);

      if (task == null) {
        out.println("해당 번호의 작업이 없습니다.");
        return;
      }

      out.printf("내용: %s\n", task.getContent());
      out.printf("마감일: %s\n", task.getDeadline());
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
      out.printf("상태: %s\n", stateLabel);
      out.printf("담당자: %s\n", task.getOwner());

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private Task findByNo(int no) {
    for (int i = 0; i < taskList.size(); i++) {
      Task task = taskList.get(i);
      if (task.getNo() == no) {
        return task;
      }
    }
    return null;
  }
}
