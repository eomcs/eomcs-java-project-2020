package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 낱개의 변수를 사용하여 여러 회원 정보 처리하기
// 2) 조건문을 사용하여 입출력 제어하기
// - 필요한 만큼만 입력 받고 출력하고 싶다.
// 3) 배열을 사용하여 여러 개의 값을 다루기
// - 배열을 사용하면 간단하게 여러 개의 변수를 선언할 수 있다.
// 4) 반복문을 사용하여 여러 개의 값을 다루기
// - 반복문을 사용하면 같은 코드를 중복해서 작성할 필요가 없다.
// 5) 배열 개수를 변수에서 관리하기
// - 변수의 값만 바꾸면 배열 개수를 바로 변경할 수 있어 편하다.
// 6) 상수를 사용하여 초기 값을 변경하지 못하게 막기
// - 변수는 중간에 값을 바꿀 수 있기 때문에 값을 바꾸지 말아야 하는 경우
//   상수로 선언한다.
// 7) 메서드를 사용하여 코드를 분리하기
// - 메서드를 사용하면 기능 별로 코드를 별도의 블럭으로 분리할 수 있다.
//   작은 단위로 코드가 분리되면 관리하기 쉽다.
// 8) 클래스를 사용하여 데이터를 담을 메모리를 정의한다.
// - 번호, 이름, 이메일 등 각각의 데이터를 낱개로 다루는 것 보다
//   하나의 데이터로 묶어서 다루면 값을 다루기가 편하다.
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
