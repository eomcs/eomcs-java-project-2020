package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    // 회원 정보를 담을 메모리의 설계도를 만든다.
    // 클래스?
    // - 애플리케이션에서 다룰 특정 데이터(수업정보, 학생정보, 게시물, 제품정보 등)의
    // 메모리 구조를 설계하는 문법이다.
    // - 이렇게 개발자가 새롭게 정의한 데이터 타입을
    // "사용자 정의 데이터 타입"이라 부른다.
    // - 즉 '클래스'는 '사용자 정의 데이터 타입'을 만들 때 사용하는 문법이다.
    //
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

    Scanner keyboardScan = new Scanner(System.in);

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

    keyboardScan.close();

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
  }
}
