package com.eomcs.pms;

import java.sql.Date;

public class ProjectHandler {

  static class Project {
    int no;
    String title;
    String content;
    Date startDate;
    Date endDate;
    String owner;
    String members;
  }

  static final int LENGTH = 100;
  static Project[] list = new Project[LENGTH];
  static int size = 0;

  static void add() {
    System.out.println("[프로젝트 등록]");

    Project project = new Project();
    project.no = Prompt.inputInt("번호? ");
    project.title = Prompt.inputString("프로젝트명? ");
    project.content = Prompt.inputString("내용? ");
    project.startDate = Prompt.inputDate("시작일? ");
    project.endDate = Prompt.inputDate("종료일? ");
    project.owner = Prompt.inputString("만든이? ");
    project.members = Prompt.inputString("팀원? ");

    list[size++] = project;
  }

  static void list() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < size; i++) {
      // 번호, 프로젝트명, 시작일, 종료일, 만든이
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          list[i].no, // 프로젝트 번호
          list[i].title, // 프로젝트명
          list[i].startDate, // 시작일
          list[i].endDate, // 종료일
          list[i].owner // 프로젝트 생성자
          );
    }
  }
}
