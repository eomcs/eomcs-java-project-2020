package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class TaskHandler {

  // 작업 데이터
  static class Task {
    int no;
    String content;
    Date deadline;
    int status;
    String owner;
  }
  static final int LENGTH = 100; // TLENGTH 를 LENGTH 로 변경한다.

  Task[] list = new Task[LENGTH]; // tasks 를 list 로 변경한다.
  int size = 0; // tsize 를 size 로 변경한다.

  // 외부에서 직접 이 변수를 사용하지 않기 때문에
  // public 으로 공개한 것을 취소한다.
  MemberHandler memberHandler;

  // TaskHandler의 인스턴스를 생성하려는 개발자에게
  // TaskHandler가 사용할 의존 객체(MemberHandler)를 반드시 주입하도록 강제한다.
  public TaskHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  //다른 패키지에서 이 메서드를 사용할 수 있도록 public 으로 사용 범위를 공개한다.
  public void add() {
    System.out.println("[작업 등록]");

    Task task = new Task();
    task.no = Prompt.inputInt("번호? ");
    task.content = Prompt.inputString("내용? ");
    task.deadline = Prompt.inputDate("마감일? ");
    task.status = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");

    while (true) {
      String name = Prompt.inputString("담당자?(취소: 빈 문자열) ");

      if (name.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (memberHandler.findByName(name) != null) {
        task.owner = name;
        break;
      }

      System.out.println("등록된 회원이 아닙니다.");
    }

    this.list[this.size++] = task;
  }

  public void list() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < this.size; i++) {
      Task task = this.list[i];
      String stateLabel = null;
      switch (task.status) {
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
          task.no,
          task.content,
          task.deadline,
          stateLabel,
          task.owner);
    }
  }
}
