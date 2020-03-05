package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    System.out.println("[회원]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 100명의 회원 정보를 저장할 메모리 준비
    int[] no = new int[100];
    String[] name = new String[100];
    String[] email = new String[100];
    String[] password = new String[100];
    String[] photo = new String[100];
    String[] tel = new String[100];
    Date[] registeredDate = new Date[100];

    int size = 0;
    for (int i = 0; i < 100; i++) {
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
      // 번호, 이름, 이메일, 전화, 가입
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[i], name[i], email[i], tel[i], registeredDate[i]);
    }
  }
}
