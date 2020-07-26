package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App_h {

  public static void main(String[] args) {
    class Member {
      int no;
      String name;
      String email;
      String password;
      String photo;
      String tel;
      Date registeredDate;
    }

    // 최대 100 개의 레퍼런스를 생성한다.
    final int LENGTH = 100;
    Member[] members = new Member[LENGTH];
    int memberSize = 0;

    // 프로젝트 정보를 담을 메모리의 설계도를 만든다.
    class Project {
      int no;
      String title;
      String content;
      Date startDate;
      Date endDate;
      String owner;
      String members;
    }
    // 최대 100개의 Project 인스턴스의 주소를 저장할 레퍼런스 배열 준비
    Project[] projects = new Project[LENGTH];
    int projectSize = 0;

    Scanner keyboardScan = new Scanner(System.in);

    // 사용자로부터 명령어 입력을 반복해서 받는다.
    loop:
      while (true) {
        System.out.print("명령> ");
        String command = keyboardScan.nextLine();

        switch (command) {
          case "/member/add":
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

            break;
          case "/member/list":
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
            break;
          case "/project/add":
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
            break;
          case "/project/list":
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
}
