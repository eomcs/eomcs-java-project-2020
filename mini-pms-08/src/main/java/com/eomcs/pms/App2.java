package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App2 {

  public static void main(String[] args) {

    // 프로젝트 정보를 담을 메모리의 설계도를 만든다.
    class Project {
      int no;
      String title;
      String content;
      Date startDate;
      Date endDate;
      int ownerNo;
    }

    System.out.println("[프로젝트]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 100개의 Project 인스턴스의 주소를 저장할 레퍼런스 배열 준비
    final int LENGTH = 100;
    Project[] projects = new Project[LENGTH];

    int size = 0;
    for (int i = 0; i < 100; i++) {

      // 프로젝트 정보를 담은 Project 인스턴스를 생성한다.
      Project project = new Project();

      System.out.print("번호? ");
      project.no = Integer.valueOf(keyboardScan.nextLine());

      System.out.print("프로젝트명? ");
      project.title = keyboardScan.nextLine();

      System.out.print("내용? ");
      project.content = keyboardScan.nextLine();

      System.out.print("시작일? ");
      project.startDate = Date.valueOf(keyboardScan.nextLine());

      System.out.print("종료일? ");
      project.endDate = Date.valueOf(keyboardScan.nextLine());

      System.out.print("생성자 번호? ");
      project.ownerNo = Integer.valueOf(keyboardScan.nextLine());

      // Project 인스턴스 주소를 배열에 저장
      projects[size++] = project;

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
      // 번호, 프로젝트명, 시작일, 종료일, 생성자 번호
      System.out.printf("%d, %s, %s, %s, %d\n", // 출력 형식 지정
          projects[i].no, // 프로젝트 번호
          projects[i].title, // 프로젝트명
          projects[i].startDate, // 시작일
          projects[i].endDate, // 종료일
          projects[i].ownerNo // 프로젝트 생성자
      );
    }
  }
}
