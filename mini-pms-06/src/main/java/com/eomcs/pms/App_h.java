package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 회원 데이터를 입력하는 코드를 메서드로 분리한다.
// 2) 회원 데이터 목록을 출력하는 코드를 메서드로 분리한다.
// 3) 프로젝트 데이터를 입력하는 코드를 메서드로 분리한다.
// 4) 프로젝트 데이터 목록을 출력하는 코드를 메서드로 분리한다.
// 5) 작업 데이터를 입력하는 코드를 메서드로 분리한다.
// 6) 작업 데이터 목록을 출력하는 코드를 메서드로 분리한다.
// 7) 사용자로부터 입력 받는 코드를 메서드로 분리한다.
// 8) 프롬프트 제목을 파라미터로 받아 출력한다.
public class App_h {

  static Scanner keyboardScan = new Scanner(System.in);

  // 회원 데이터
  static final int LENGTH = 100;
  static int[] no = new int[LENGTH];
  static String[] name = new String[LENGTH];
  static String[] email = new String[LENGTH];
  static String[] password = new String[LENGTH];
  static String[] photo = new String[LENGTH];
  static String[] tel = new String[LENGTH];
  static Date[] registeredDate = new Date[LENGTH];
  static int size = 0;

  // 프로젝트 데이터
  static final int PLENGTH = 100;
  static int[] pno = new int[PLENGTH];
  static String[] ptitle = new String[PLENGTH];
  static String[] pcontent = new String[PLENGTH];
  static Date[] pstartDate = new Date[PLENGTH];
  static Date[] pendDate = new Date[PLENGTH];
  static String[] powner = new String[PLENGTH];
  static String[] pmembers = new String[PLENGTH];
  static int psize = 0;

  // 작업 데이터
  static final int TLENGTH = 100;
  static int[] tno = new int[TLENGTH];
  static String[] tcontent = new String[TLENGTH];
  static Date[] tdeadline = new Date[TLENGTH];
  static String[] towner = new String[TLENGTH];
  static int[] tstatus = new int[TLENGTH];
  static int tsize = 0;

  public static void main(String[] args) {

    loop:
      while (true) {
        String command = prompt("명령> ");

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

    no[size] = Integer.parseInt(prompt("번호? "));
    name[size] = prompt("이름? ");
    email[size] = prompt("이메일? ");
    password[size] = prompt("암호? ");
    photo[size] = prompt("사진? ");
    tel[size] = prompt("전화? ");
    registeredDate[size] = new java.sql.Date(System.currentTimeMillis());

    size++;
  }

  static void listMember() {
    System.out.println("[회원 목록]");
    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }

  static void addProject() {
    System.out.println("[프로젝트 등록]");

    pno[psize] = Integer.valueOf(prompt("번호? "));
    ptitle[psize] = prompt("프로젝트명? ");
    pcontent[psize] = prompt("내용? ");
    pstartDate[psize] = Date.valueOf(prompt("시작일? "));
    pendDate[psize] = Date.valueOf(prompt("종료일? "));
    powner[psize] = prompt("만든이? ");
    pmembers[psize] = prompt("팀원? ");

    psize++;
  }

  static void listProject() {
    System.out.println("[프로젝트 목록]");

    for (int i = 0; i < psize; i++) {
      // 번호, 프로젝트명, 시작일, 종료일, 만든이
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          pno[i], ptitle[i], pstartDate[i], pendDate[i], powner[i]);
    }
  }

  static void addTask() {
    System.out.println("[작업 등록]");

    tno[tsize] = Integer.parseInt(prompt("번호? "));
    tcontent[tsize] = prompt("내용? ");
    tdeadline[tsize] = Date.valueOf(prompt("마감일? "));
    tstatus[tsize] = Integer.valueOf(prompt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));
    towner[tsize] = prompt("담당자? ");

    tsize++;
  }

  static void listTask() {
    System.out.println("[작업 목록]");

    for (int i = 0; i < tsize; i++) {
      String stateLabel = null;
      switch (tstatus[i]) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      // 번호, 작업명, 마감일, 프로젝트, 상태, 담당자
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          tno[i], tcontent[i], tdeadline[i], stateLabel, towner[i]);
    }
  }

  static String prompt(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }
}
