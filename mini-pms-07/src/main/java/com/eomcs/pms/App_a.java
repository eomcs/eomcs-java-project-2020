package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 회원 정보를 저장할 새 메모리 타입을 정의한다
public class App_a {

  static Scanner keyboardScan = new Scanner(System.in);
  
  // 회원 데이터
  
  // 회원 정보를 담을 메모리의 설계도를 만든다.
  // 클래스?
  // - 애플리케이션에서 다룰 특정 데이터(수업정보, 학생정보, 게시물, 제품정보 등)의
  // 메모리 구조를 설계하는 문법이다.
  // - 이렇게 개발자가 새롭게 정의한 데이터 타입을
  // "사용자 정의 데이터 타입"이라 부른다.
  // - 즉 '클래스'는 '사용자 정의 데이터 타입'을 만들 때 사용하는 문법이다.
  //
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
  
  // Member 인스턴스의 주소를 보관할 레퍼런스 배열을 준비한다.
  static Member[] members = new Member[LENGTH];
  
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
    
    // 회원 정보를 담을 메모리(Member 클래스의 인스턴스)를 준비한다.
    Member member = new Member();
    member.no = promptInt("번호? ");
    member.name = promptString("이름? ");
    member.email = promptString("이메일? ");
    member.password = promptString("암호? ");
    member.photo = promptString("사진? ");
    member.tel = promptString("전화? ");
    member.registeredDate = new java.sql.Date(System.currentTimeMillis());
    
    // 인스턴스의 주소를 레퍼런스 배열에 저장한다.
    members[size++] = member;
  }
  
  static void listMember() {
    System.out.println("[회원 목록]");
    
    for (int i = 0; i < size; i++) {
      // 레퍼런스 배열에서 인스턴스 주소를 꺼내 member 라는 레퍼런스 변수에 저장한다.
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
    
    pno[psize] = promptInt("번호? ");
    ptitle[psize] = promptString("프로젝트명? ");
    pcontent[psize] = promptString("내용? ");
    pstartDate[psize] = promptDate("시작일? ");
    pendDate[psize] = promptDate("종료일? ");
    powner[psize] = promptString("만든이? ");
    pmembers[psize] = promptString("팀원? ");

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
    
    tno[tsize] = promptInt("번호? ");
    tcontent[tsize] = promptString("내용? ");
    tdeadline[tsize] = promptDate("마감일? ");
    tstatus[tsize] = promptInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ");
    towner[tsize] = promptString("담당자? ");

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
