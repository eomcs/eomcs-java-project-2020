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

  public MemberHandler memberHandler;

  // 인스턴스 메서드
  // => 인스턴스가 있어야만 호출할 수 있는 메서드이다.
  // => 인스턴스를 사용하는 메서드인 경우 인스턴스 메서드로 선언하라.
  // => 호출할 때는 반드시 인스턴스 주소를 줘야 한다.
  //      인스턴스주소.메서드명();
  // => 이렇게 인스턴스의 변수 값을 다루는 메서드는
  //    "연산자(operation)"라 부를 수 있다.
  //  
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
