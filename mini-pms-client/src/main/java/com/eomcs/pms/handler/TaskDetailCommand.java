package com.eomcs.pms.handler;

import java.util.Map;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskDetailCommand implements Command {
  TaskDao taskDao;

  public TaskDetailCommand(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[작업 상세보기]");

    try {
      int no = Prompt.inputInt("번호? ");

      Task task = taskDao.findByNo(no);
      if (task == null) {
        System.out.println("해당 번호의 작업이 존재하지 않습니다.");
        return;
      }

      System.out.printf("내용: %s\n", task.getContent());
      System.out.printf("마감일: %s\n", task.getDeadline());

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
      System.out.printf("상태: %s\n", stateLabel);
      System.out.printf("담당자: %s\n", task.getOwner().getName());

    } catch (Exception e) {
      System.out.println("작업 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
