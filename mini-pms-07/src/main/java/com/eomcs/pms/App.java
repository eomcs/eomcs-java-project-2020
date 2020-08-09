package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 회원 정보를 저장할 새 메모리 타입을 정의한다
// 2) 프로젝트 정보를 저장할 새 메모리 타입을 정의한다
// 3) 작업 정보를 저장할 새 메모리 타입을 정의한다
public class App {

  static Scanner keyboardScan = new Scanner(System.in);
  
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
        String command = promptString("명령> ");

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

    keyboardScan.close();
  }
  
  static void addMember() {
    System.out.println("[회원 등록]");
    
    Member member = new Member();
    member.no = promptInt("번호? ");
    member.name = promptString("이름? ");
    member.email = promptString("이메일? ");
    member.password = promptString("암호? ");
    member.photo = promptString("사진? ");
    member.tel = promptString("전화? ");
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
    project.no = promptInt("번호? ");
    project.title = promptString("프로젝트명? ");
    project.content = promptString("내용? ");
    project.startDate = promptDate("시작일? ");
    project.endDate = promptDate("종료일? ");
    project.owner = promptString("만든이? ");
    project.members = promptString("팀원? ");

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
    task.no = promptInt("번호? ");
    task.content = promptString("내용? ");
    task.deadline = promptDate("마감일? ");
    task.status = promptInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    task.owner = promptString("담당자? ");

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
  
  static String promptString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  static int promptInt(String title) {
    return Integer.parseInt(promptString(title));
  }

  static Date promptDate(String title) {
    return Date.valueOf(promptString(title));
  }
}
