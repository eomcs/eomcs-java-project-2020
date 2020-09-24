package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDeleteCommand implements Command {

  List<Task> taskList;

  public TaskDeleteCommand(List<Task> list) {
    this.taskList = list;
  }

  @Override
  public void execute() {
    System.out.println("[작업 삭제]");
    int no = Prompt.inputInt("번호? ");
    int index = indexOf(no);

    if (index == -1) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("작업 삭제를 취소하였습니다.");
      return;
    }

    taskList.remove(index);
    System.out.println("작업을 삭제하였습니다.");
  }

  private int indexOf(int no) {
    for (int i = 0; i < taskList.size(); i++) {
      Task task = taskList.get(i);
      if (task.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
