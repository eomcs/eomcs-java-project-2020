package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateCommand implements Command {

  List<Task> taskList;
  MemberListCommand memberListCommand;

  public TaskUpdateCommand(List<Task> list, MemberListCommand memberListCommand) {
    this.taskList = list;
    this.memberListCommand = memberListCommand;
  }

  @Override
  public void execute() {
    System.out.println("[작업 변경]");
    int no = Prompt.inputInt("번호? ");
    Task task = findByNo(no);

    if (task == null) {
      System.out.println("해당 번호의 작업이 없습니다.");
      return;
    }

    String content = Prompt.inputString(
        String.format("내용(%s)? ", task.getContent()));
    Date deadline = Prompt.inputDate(
        String.format("마감일(%s)? ", task.getDeadline()));
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
    int status = Prompt.inputInt(
        String.format("상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", stateLabel));

    String owner = null;
    while (true) {
      String name = Prompt.inputString(
          String.format("담당자(%s)?(취소: 빈 문자열) ", task.getOwner()));

      if (name.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (memberListCommand.findByName(name) != null) {
        owner = name;
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("작업 변경을 취소하였습니다.");
      return;
    }

    task.setContent(content);
    task.setDeadline(deadline);
    task.setStatus(status);
    task.setOwner(owner);

    System.out.println("작업을 변경하였습니다.");
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
