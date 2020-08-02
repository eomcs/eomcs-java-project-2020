package com.eomcs.pms;

import java.sql.Date;

// 1) 사용자의 입력을 받는 프롬프트 메서드를 별도의 클래스로 분리한다
public class App_a {

  // 회원 데이터
  static class Member {
    int no;
    String name;
    String email;
    String password;
    String photo;
    String tel;
    Date registeredDate;
  }
  static final int LENGTH = 100;
  static Member[] members = new Member[LENGTH];
  static int size = 0;
  
  // 프로젝트 데이터
  static class Project {
    // Project 클래스에 선언하는 변수는 
    // 기존의 회원 데이터 관련 변수나 작업 데이터 관련 변수와 구분되기 때문에 
    // 변수 이름을 다르게 할 필요가 없다.
    int no;
    String title;
    String content;
    Date startDate;
    Date endDate;
    String owner;
    String members;
  }
  static final int PLENGTH = 100;
  static Project[] projects = new Project[PLENGTH];
  static int psize = 0;
  
  // 작업 데이터
  static class Task {
    int no;
    String content;
    Date deadline;
    int status;
    String owner;
  }
  static final int TLENGTH = 100;
  static Task[] tasks = new Task[TLENGTH];
  static int tsize = 0;

  public static void main(String[] args) {
    
    loop:
      while (true) {
        String command = Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add":
            addMember();
            break;
          case "/member/list":
            listMember();
            break;
          case "/project/add":
            addProject();
            break;
          case "/project/list":
            listProject();
            break;
          case "/task/add":
            addTask();
            break;
          case "/task/list":
            listTask();
            break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
  
  static void addMember() {
    System.out.println("[회원 등록]");
    
    Member member = new Member();
    member.no = Prompt.inputInt("번호? ");
    member.name = Prompt.inputString("이름? ");
    member.email = Prompt.inputString("이메일? ");
    member.password = Prompt.inputString("암호? ");
    member.photo = Prompt.inputString("사진? ");
    member.tel = Prompt.inputString("전화? ");
    member.registeredDate = new java.sql.Date(System.currentTimeMillis());
    
    members[size++] = member;
  }
  
  static void listMember() {
    System.out.println("[회원 목록]");
    
    for (int i = 0; i < size; i++) {
      Member member = members[i];
      System.out.printf("%d, %s, %s, %s, %s\n",
          member.no, 
          member.name, 
          member.email, 
          member.tel, 
          member.registeredDate);
    }
  }
  
  static void addProject() {
    System.out.println("[프로젝트 등록]");
    
    Project project = new Project();
    project.no = Prompt.inputInt("번호? ");
    project.title = Prompt.inputString("프로젝트명? ");
    project.content = Prompt.inputString("내용? ");
    project.startDate = Prompt.inputDate("시작일? ");
    project.endDate = Prompt.inputDate("종료일? ");
    project.owner = Prompt.inputString("만든이? ");
    project.members = Prompt.inputString("팀원? ");

    projects[psize++] = project;
  }
  
  static void listProject() {
    System.out.println("[프로젝트 목록]");
    
    for (int i = 0; i < psize; i++) {
      Project project = projects[i];
      System.out.printf("%d, %s, %s, %s, %s\n",
          project.no, 
          project.title, 
          project.startDate, 
          project.endDate, 
          project.owner);
    }
  }
  
  static void addTask() {
    System.out.println("[작업 등록]");
    
    Task task = new Task();
    task.no = Prompt.inputInt("번호? ");
    task.content = Prompt.inputString("내용? ");
    task.deadline = Prompt.inputDate("마감일? ");
    task.status = Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    task.owner = Prompt.inputString("담당자? ");

    tasks[tsize++] = task;
  }
  
  static void listTask() {
    System.out.println("[작업 목록]");
    
    for (int i = 0; i < tsize; i++) {
      Task task = tasks[i];
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
