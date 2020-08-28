package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Task;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Prompt;

public class TaskHandler {

  //Task 객체 목록을 저장할 ArrayList 객체를 준비한다.
  ArrayList taskList = new ArrayList();
  MemberHandler memberHandler;

  public TaskHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  public void add() {
    System.out.println("[작업 등록]");

    Task task = new Task();
    task.setNo(Prompt.inputInt("번호? "));
    task.setContent(Prompt.inputString("내용? "));
    task.setDeadline(Prompt.inputDate("마감일? "));
    task.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));

    while (true) {
      String name = Prompt.inputString("담당자?(취소: 빈 문자열) ");

      if (name.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (memberHandler.findByName(name) != null) {
        task.setOwner(name);
        break;
      }

      System.out.println("등록된 회원이 아닙니다.");
    }

    taskList.add(task);
  }

  public void list() {
    System.out.println("[작업 목록]");
    Object[] tasks = taskList.toArray();

    for (Object obj : tasks) {
      Task task = (Task) obj;
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
      System.out.printf("%d, %s, %s, %s, %s\n",
          task.getNo(),
          task.getContent(),
          task.getDeadline(),
          stateLabel,
          task.getOwner());
    }
  }
}
