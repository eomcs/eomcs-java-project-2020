package com.eomcs.pms.handler;

import java.util.Map;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.util.Prompt;

public class TaskDeleteCommand implements Command {
  TaskDao taskDao;

  public TaskDeleteCommand(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[작업 삭제]");

    try  {
      int no = Prompt.inputInt("번호? ");

      String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
      if (!response.equalsIgnoreCase("y")) {
        System.out.println("작업 삭제를 취소하였습니다.");
        return;
      }

      if (taskDao.delete(no) == 0) {
        System.out.println("해당 번호의 작업이 존재하지 않습니다.");
      } else {
        System.out.println("작업을 삭제하였습니다.");
      }

    } catch (Exception e) {
      System.out.println("작업 삭제 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
