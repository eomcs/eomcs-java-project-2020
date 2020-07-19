package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 낱개의 변수를 사용할 때
// 2) 조건문을 사용하여 입력 및 출력 제어
// 3) 배열 사용
// 4) 반복문 사용
// 5) 배열의 개수를 변수에 저장하여 크기 변경을 쉽게 하기

public class App_e {

  public static void main(String[] args) {
    System.out.println("[회원]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 입력 개수
    int length = 5;

    int[] no = new int[length];
    String[] name = new String[length];
    String[] email = new String[length];
    String[] password = new String[length];
    String[] photo = new String[length];
    String[] tel = new String[length];
    Date[] registeredDate = new Date[length];

    int size = 0;

    for (int i = 0; i < length; i++) {
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

    System.out.println("--------------------------------");

    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }
}
