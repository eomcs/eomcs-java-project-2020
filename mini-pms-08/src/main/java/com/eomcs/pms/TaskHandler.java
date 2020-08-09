package com.eomcs.pms;

import java.sql.Date;

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
  static Task[] list = new Task[LENGTH]; // tasks 를 list 로 변경한다.
  static int size = 0; // tsize 를 size 로 변경한다.

  static void add() { // 메서드 이름을 변경한다.
    System.out.println("[작업 등록]");
    
    Task task = new Task();
    task.no = Prompt.inputInt("번호? ");
    task.content = Prompt.inputString("내용? ");
    task.deadline = Prompt.inputDate("마감일? ");
    task.status = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    task.owner = Prompt.inputString("담당자? ");

    list[size++] = task;
  }
  
  static void list() { // 메서드 이름을 변경한다.
    System.out.println("[작업 목록]");
    
    for (int i = 0; i < size; i++) {
      Task task = list[i];
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
