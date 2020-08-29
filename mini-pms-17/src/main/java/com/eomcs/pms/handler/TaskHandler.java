package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Task;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Prompt;

public class TaskHandler {

  //Task 객체 목록을 저장할 ArrayList 객체를 준비한다.
  // 제네릭 문법으로 항목의 타입을 지정한다.
  ArrayList<Task> taskList = new ArrayList<>();
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

    // 제네릭 문법에 따라 add()를 호출할 때 넘겨줄 수 있는 값은 
    // Task 또는 그 하위 타입의 인스턴스만 가능하다.
    // 다른 타입은 불가능하다.
    taskList.add(task);
  }

  public void list() {
    System.out.println("[작업 목록]");

    // 제네릭 문법에 따라 리턴 타입이 'Task[]' 이기 때문에
    // 따로 형변환 할 필요가 없다.
    // 대신 Task[] 배열을 리턴해 달라는 의미로 배열의 타입 정보를 넘긴다.
    Task[] tasks = taskList.toArray(Task[].class);

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
      System.out.printf("%d, %s, %s, %s, %s\n",
          task.getNo(),
          task.getContent(),
          task.getDeadline(),
          stateLabel,
          task.getOwner());
    }
  }
}
