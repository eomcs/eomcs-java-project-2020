package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 낱개의 변수를 사용할 때
// 2) 조건문을 사용하여 입력 및 출력 제어
// 3) 배열 사용
// 4) 반복문 사용
// 5) 배열의 개수를 변수에 저장하여 크기 변경을 쉽게 하기
// 6) 배열의 개수를 저장한 변수의 값을 코드 중간에서 변경하지 못하게 막기 => final
// 7) 코드를 관리하기 쉽게 기능 단위로 분리하기 => 메서드
// 8) 복합 데이터를 저장할 메모리를 설계하기 => 클래스

public class App_h {

  static class Member {
    int no;
    String name;
    String email;
    String password;
    String photo;
    String tel;
    Date registeredDate;
  }

  static final int LENGTH = 5;
  static Member[] members = new Member[LENGTH];
  static int size = 0;

  public static void main(String[] args) {
    System.out.println("[회원]");

    inputMembers();

    System.out.println("--------------------------------");

    printMembers();
  }

  static void inputMembers() {
Scanner keyboardScan = new Scanner(System.in);


    for (int i = 0; i < LENGTH; i++) {
      Member member = new Member();

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

      members[size++] = member;
      System.out.println(); // 빈 줄 출력

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      String str = keyboardScan.nextLine();
      if (!str.equalsIgnoreCase("y")) {
        break;
      }
      System.out.println(); // 빈 줄 출력
    }

    keyboardScan.close();
  }

  static void printMembers() {
    for (int i = 0; i < size; i++) {
      Member m = members[i];
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          m.no, m.name, m.email, m.tel, m.registeredDate);
    }
  }
}
