package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 낱개의 변수를 사용하여 여러 회원 정보 처리하기
// 2) 조건문을 사용하여 입출력 제어하기
// - 필요한 만큼만 입력 받고 출력하고 싶다.
// 3) 배열을 사용하여 여러 개의 값을 다루기
// - 배열을 사용하면 간단하게 여러 개의 변수를 선언할 수 있다.
public class App_c {

  public static void main(String[] args) {
    System.out.println("[회원]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 5명의 회원 정보를 저장할 메모리 준비
    int[] no = new int[5];
    String[] name = new String[5];
    String[] email = new String[5];
    String[] password = new String[5];
    String[] photo = new String[5];
    String[] tel = new String[5];
    Date[] registeredDate = new Date[5];

    int size = 0;
    
    
    System.out.print("번호? ");
    no[0] = Integer.parseInt(keyboardScan.nextLine());

    System.out.print("이름? ");
    name[0] = keyboardScan.nextLine();

    System.out.print("이메일? ");
    email[0] = keyboardScan.nextLine();

    System.out.print("암호? ");
    password[0] = keyboardScan.nextLine();

    System.out.print("사진? ");
    photo[0] = keyboardScan.nextLine();

    System.out.print("전화? ");
    tel[0] = keyboardScan.nextLine();

    registeredDate[0] = new java.sql.Date(System.currentTimeMillis());

    size++;
    System.out.println(); // 빈 줄 출력

    System.out.print("계속 입력하시겠습니까?(y/N) ");
    String str = keyboardScan.nextLine();
    
    if (str.equalsIgnoreCase("y")) {
      System.out.print("번호? ");
      no[1] = Integer.parseInt(keyboardScan.nextLine());

      System.out.print("이름? ");
      name[1] = keyboardScan.nextLine();

      System.out.print("이메일? ");
      email[1] = keyboardScan.nextLine();

      System.out.print("암호? ");
      password[1] = keyboardScan.nextLine();

      System.out.print("사진? ");
      photo[1] = keyboardScan.nextLine();

      System.out.print("전화? ");
      tel[1] = keyboardScan.nextLine();

      registeredDate[1] = new java.sql.Date(System.currentTimeMillis());

      size++;
      System.out.println(); // 빈 줄 출력

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      str = keyboardScan.nextLine();
      
      if (str.equalsIgnoreCase("y")) {
        System.out.print("번호? ");
        no[2] = Integer.parseInt(keyboardScan.nextLine());

        System.out.print("이름? ");
        name[2] = keyboardScan.nextLine();

        System.out.print("이메일? ");
        email[2] = keyboardScan.nextLine();

        System.out.print("암호? ");
        password[2] = keyboardScan.nextLine();

        System.out.print("사진? ");
        photo[2] = keyboardScan.nextLine();

        System.out.print("전화? ");
        tel[2] = keyboardScan.nextLine();

        registeredDate[2] = new java.sql.Date(System.currentTimeMillis());

        size++;
        System.out.println(); // 빈 줄 출력

        System.out.print("계속 입력하시겠습니까?(y/N) ");
        str = keyboardScan.nextLine();
        
        if (str.equalsIgnoreCase("y")) {
          System.out.print("번호? ");
          no[3] = Integer.parseInt(keyboardScan.nextLine());

          System.out.print("이름? ");
          name[3] = keyboardScan.nextLine();

          System.out.print("이메일? ");
          email[3] = keyboardScan.nextLine();

          System.out.print("암호? ");
          password[3] = keyboardScan.nextLine();

          System.out.print("사진? ");
          photo[3] = keyboardScan.nextLine();

          System.out.print("전화? ");
          tel[3] = keyboardScan.nextLine();

          registeredDate[3] = new java.sql.Date(System.currentTimeMillis());

          size++;
          System.out.println(); // 빈 줄 출력

          System.out.print("계속 입력하시겠습니까?(y/N) ");
          str = keyboardScan.nextLine();
          
          if (str.equalsIgnoreCase("y")) {
            System.out.print("번호? ");
            no[4] = Integer.parseInt(keyboardScan.nextLine());

            System.out.print("이름? ");
            name[4] = keyboardScan.nextLine();

            System.out.print("이메일? ");
            email[4] = keyboardScan.nextLine();

            System.out.print("암호? ");
            password[4] = keyboardScan.nextLine();

            System.out.print("사진? ");
            photo[4] = keyboardScan.nextLine();

            System.out.print("전화? ");
            tel[4] = keyboardScan.nextLine();

            registeredDate[4] = new java.sql.Date(System.currentTimeMillis());

            size++;
            System.out.println(); // 빈 줄 출력
          }
        }
      }
    }
    
    keyboardScan.close();

    System.out.println("--------------------------------");

    // 번호, 이름, 이메일, 전화, 가입일
    System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
        no[0], name[0], email[0], tel[0], registeredDate[0]);
    if (size >= 2) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[1], name[1], email[1], tel[1], registeredDate[1]);
    }
    if (size >= 3) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[2], name[2], email[2], tel[2], registeredDate[2]);
    }
    if (size >= 4) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[3], name[3], email[3], tel[3], registeredDate[3]);
    }
    if (size >= 5) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no[4], name[4], email[4], tel[4], registeredDate[4]);
    }
  }
}
