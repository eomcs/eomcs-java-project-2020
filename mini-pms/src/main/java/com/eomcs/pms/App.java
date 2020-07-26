package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  static Scanner keyboardScan = new Scanner(System.in);

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
  static int memberSize = 0;

  static class Project {
    int no;
    String title;
    String content;
    Date startDate;
    Date endDate;
    String owner;
    String members;
  }

  static Project[] projects = new Project[LENGTH];
  static int projectSize = 0;

  //main()과 addTask()가 함께 사용하려면 스태틱 멤버로 만들어야 한다.
  static class Task {
    int no;
    String content;
    Date deadline;
    int status;
    String owner;
  }

  // 최대 100개의 Task 인스턴스의 주소를 저장할 레퍼런스 배열 준비
  static Task[] tasks = new Task[LENGTH];
  static int taskSize = 0;

  public static void main(String[] args) {

    loop:
      while (true) {
        String command = promptString("명령> ");

        switch (command) {
          case "/member/add": addMember(); break;
          case "/member/list": listMember(); break;
          case "/project/add": addProject(); break;
          case "/project/list": listProject(); break;
          case "/task/add": addTask(); break;
          case "/task/list": listTask(); break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println();
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

    members[memberSize++] = member;
  }

  static void listMember() {
    System.out.println("[회원 목록]");

    for (int i = 0; i < memberSize; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          members[i].no, // 회원 번호
          members[i].name, // 이름
          members[i].email, // 이메일
          members[i].tel, // 전화
          members[i].registeredDate // 가입일
          );
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

    projects[projectSize++] = project;
  }

  static void listProject() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < projectSize; i++) {
      // 번호, 프로젝트명, 시작일, 종료일, 만든이
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          projects[i].no, // 프로젝트 번호
          projects[i].title, // 프로젝트명
          projects[i].startDate, // 시작일
          projects[i].endDate, // 종료일
          projects[i].owner // 프로젝트 생성자
          );
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

    tasks[taskSize++] = task;
  }

  static void listTask() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < taskSize; i++) {
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
      // 번호, 작업명, 마감일, 담당자, 상태
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          tasks[i].no, // 작업 번호
          tasks[i].content, // 작업 내용
          tasks[i].deadline, // 마감일
          tasks[i].owner, // 담당자
          stateLabel // 작업상태
          );
    }
  }

  static String promptString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  static int promptInt(String title) {
    System.out.print(title);
    return Integer.parseInt(keyboardScan.nextLine());
  }

  static Date promptDate(String title) {
    System.out.print(title);
    return Date.valueOf(keyboardScan.nextLine());
  }
}
