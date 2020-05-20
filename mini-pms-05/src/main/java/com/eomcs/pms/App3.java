package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App3 {

  public static void main(String[] args) {

    // 작업 정보를 담을 메모리의 설계도를 만든다.
    class Task {
      int no;
      String content;
      Date deadline;
      int projectNo;
      int status;
    }

    System.out.println("[작업]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 100개의 Task 인스턴스의 주소를 저장할 레퍼런스 배열 준비
    final int LENGTH = 100;
    Task[] tasks = new Task[LENGTH];

    int size = 0;
    for (int i = 0; i < 100; i++) {

      // 작업 정보를 저장할 Task 인스턴스를 생성한다.
      Task task = new Task();

      System.out.print("번호? ");
      task.no = Integer.parseInt(keyboardScan.nextLine());

      System.out.print("내용? ");
      task.content = keyboardScan.nextLine();

      System.out.print("마감일? ");
      task.deadline = Date.valueOf(keyboardScan.nextLine());

      System.out.print("프로젝트 번호? ");
      task.projectNo = Integer.parseInt(keyboardScan.nextLine());

      System.out.println("상태?");
      System.out.println("0: 신규");
      System.out.println("1: 진행중");
      System.out.println("2: 완료");
      System.out.print("> ");
      task.status = Integer.valueOf(keyboardScan.nextLine());

      // 작업 정보를 담은 Task 인스턴스 주소를 배열에 저장한다.
      tasks[size++] = task;
      System.out.println(); // 빈 줄 출력

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String str = keyboardScan.nextLine();
      if (!str.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println(); // 빈 줄 출력
    }

    keyboardScan.close();

    System.out.println("--------------------------------");

    for (int i = 0; i < size; i++) {
      String stateLabel = null;
      switch (tasks[i].status) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      // 번호, 작업명, 마감일, 프로젝트 번호, 상태
      System.out.printf("%d, %s, %s, %d, %s\n", // 출력 형식 지정
          tasks[i].no, // 작업 번호
          tasks[i].content, // 작업 내용
          tasks[i].deadline, // 마감일
          tasks[i].projectNo, // 프로젝트 번호
          stateLabel // 작업상태
      );
    }
  }
}
