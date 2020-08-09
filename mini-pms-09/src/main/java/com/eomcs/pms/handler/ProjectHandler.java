package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  // 프로젝트 데이터
  static class Project {
    int no;
    String title;
    String content;
    Date startDate;
    Date endDate;
    String owner;
    String members;
  }
  static final int LENGTH = 100;  // PLENGTH 를 LENGTH 로 변경한다.
  static Project[] list = new Project[LENGTH]; // projects 를 list 로 변경한다.
  static int size = 0; // psize 를 size 로 변경한다.

  //다른 패키지에서 이 메서드를 사용할 수 있도록 public 으로 사용 범위를 공개한다.
  public static void add() {
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
  
  public static void list() {
    System.out.println("[프로젝트 목록]");
    
    for (int i = 0; i < size; i++) {
      Project project = list[i];
      System.out.printf("%d, %s, %s, %s, %s\n",
          project.no, 
          project.title, 
          project.startDate, 
          project.endDate, 
          project.owner);
    }
  }
}
