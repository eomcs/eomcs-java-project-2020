package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App_d {

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

  //main()과 addProject()가 함께 사용하려면 스태틱 멤버로 만들어야 한다.
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

  public static void main(String[] args) {


    // 작업 정보를 담을 메모리의 설계도를 만든다.
    class Task {
      int no;
      String content;
      Date deadline;
      int status;
      String owner;
    }

    // 최대 100개의 Task 인스턴스의 주소를 저장할 레퍼런스 배열 준비
    Task[] tasks = new Task[LENGTH];
    int taskSize = 0;


    // 사용자로부터 명령어 입력을 반복해서 받는다.
    loop:
      while (true) {
        System.out.print("명령> ");
        String command = keyboardScan.nextLine();

        switch (command) {
          case "/member/add": addMember(); break;
          case "/member/list": listMember(); break;
          case "/project/add": addProject(); break;
          case "/project/list": listProject(); break;
          case "/task/add":
            System.out.println("[작업 등록]");

            // 작업 정보를 저장할 Task 인스턴스를 생성한다.
            Task task = new Task();

            System.out.print("번호? ");
            task.no = Integer.parseInt(keyboardScan.nextLine());

            System.out.print("내용? ");
            task.content = keyboardScan.nextLine();

            System.out.print("마감일? ");
            task.deadline = Date.valueOf(keyboardScan.nextLine());

            System.out.println("상태?");
            System.out.println("0: 신규");
            System.out.println("1: 진행중");
            System.out.println("2: 완료");
            System.out.print("> ");
            task.status = Integer.valueOf(keyboardScan.nextLine());

            System.out.print("담당자? ");
            task.owner = keyboardScan.nextLine();

            // 작업 정보를 담은 Task 인스턴스 주소를 배열에 저장한다.
            tasks[taskSize++] = task;
            break;
          case "/task/list":
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
            break;
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

    // 클래스 설계도에 따라 회원 정보를 담을 메모리(인스턴스)를 준비한다.
    Member member = new Member();

    // 메모리에 회원 정보를 저장한다.
    System.out.print("번호? ");
    member.no = Integer.parseInt(keyboardScan.nextLine());

    System.out.print("이름? ");
    member.name = keyboardScan.nextLine();

    System.out.print("이메일? ");
    member.email = keyboardScan.nextLine();

    System.out.print("암호? ");
    member.password = keyboardScan.nextLine();

    System.out.print("사진? ");
    member.photo = keyboardScan.nextLine();

    System.out.print("전화? ");
    member.tel = keyboardScan.nextLine();

    member.registeredDate = new java.sql.Date(System.currentTimeMillis());

    // 회원 정보를 담은 인스턴스의 주소를 배열에 저장한다.
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

    System.out.print("만든이? ");
    project.owner = keyboardScan.nextLine();

    System.out.print("팀원? ");
    project.members = keyboardScan.nextLine();

    // Project 인스턴스 주소를 배열에 저장
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
}
