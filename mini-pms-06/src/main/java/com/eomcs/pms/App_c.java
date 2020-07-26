package com.eomcs.pms;

import java.util.Scanner;

public class App_c {

  public static void main(String[] args) {

    Scanner keyboardScan = new Scanner(System.in);

    // 사용자로부터 명령어 입력을 반복해서 받는다.
    while (true) {
      System.out.print("명령> ");
      String command = keyboardScan.nextLine();
      System.out.println(command);

      // "quit" 이나 "exit"를 입력하면 반복을 종료한다.
      if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
        break;
      }
    }

    keyboardScan.close();

    /*
    class Member {
      int no;
      String name;
      String email;
      String password;
      String photo;
      String tel;
      Date registeredDate;
    }

    System.out.println("[회원]");



    // 최대 100 개의 레퍼런스를 생성한다.
    final int LENGTH = 100;
    Member[] members = new Member[LENGTH];

    int size = 0;
    for (int i = 0; i < 100; i++) {
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
      members[i] = member;

      size++;
      System.out.println(); // 빈 줄 출력

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String str = keyboardScan.nextLine();
      if (!str.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println(); // 빈 줄 출력
    }



    System.out.println("--------------------------------");

    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          members[i].no, // 회원 번호
          members[i].name, // 이름
          members[i].email, // 이메일
          members[i].tel, // 전화
          members[i].registeredDate // 가입일
          );
    }
     */
  }
}