package com.eomcs.pms;

import java.sql.Date;

public class TaskHandler {

  static class Task {
    int no;
    String content;
    Date deadline;
    int status;
    String owner;
  }

  static final int LENGTH = 100;
  static Task[] list = new Task[LENGTH];
  static int size = 0;

  static void add() {
    System.out.println("[작업 등록]");

    Task task = new Task();
    task.no = Prompt.inputInt("번호? ");
    task.content = Prompt.inputString("내용? ");
    task.deadline = Prompt.inputDate("마감일? ");
    task.status = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    task.owner = Prompt.inputString("담당자? ");

    list[size++] = task;
  }

  static void list() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < size; i++) {
      String stateLabel = null;
      switch (list[i].status) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      // 번호, 작업명, 마감일, 담당자, 상태
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          list[i].no, // 작업 번호
          list[i].content, // 작업 내용
          list[i].deadline, // 마감일
          list[i].owner, // 담당자
          stateLabel // 작업상태
          );
    }
  }
}
