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

public class App_g {

  static final int LENGTH = 5;

  static int[] no = new int[LENGTH];
  static String[] name = new String[LENGTH];
  static String[] email = new String[LENGTH];
  static String[] password = new String[LENGTH];
  static String[] photo = new String[LENGTH];
  static String[] tel = new String[LENGTH];
  static  Date[] registeredDate = new Date[LENGTH];

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
      System.out.print("번호? ");
      no[i] = Integer.parseInt(keyboardScan.nextLine());

      System.out.print("이름? ");
      name[i] = keyboardScan.nextLine();

      System.out.print("이메일? ");
      email[i] = keyboardScan.nextLine();

      System.out.print("암호? ");
      password[i] = keyboardScan.nextLine();

      System.out.print("사진? ");
      photo[i] = keyboardScan.nextLine();

      System.out.print("전화? ");
      tel[i] = keyboardScan.nextLine();

      registeredDate[i] = new java.sql.Date(System.currentTimeMillis());

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
  }

  static void printMembers() {
    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }
}
