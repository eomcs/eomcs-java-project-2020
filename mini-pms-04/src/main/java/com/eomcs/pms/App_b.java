package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

// 1) 낱개의 변수를 사용하여 여러 회원 정보 처리하기
// 2) 조건문을 사용하여 입출력 제어하기
// - 필요한 만큼만 입력 받고 출력하고 싶다.
public class App_b {

  public static void main(String[] args) {
    System.out.println("[회원]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 5명의 회원 정보를 저장할 메모리 준비
    int no1 = 0, no2 = 0, no3 = 0, no4 = 0, no5 = 0;
    String name1 = "", name2 = "", name3 = "", name4 = "", name5 = "";
    String email1 = "", email2 = "", email3 = "", email4 = "", email5 = "";
    String password1 = "", password2 = "", password3 = "", password4 = "", password5 = "";
    String photo1 = "", photo2 = "", photo3 = "", photo4 = "", photo5 = "";
    String tel1 = "", tel2 = "", tel3 = "", tel4 = "", tel5 = "";
    Date registeredDate1 = null, registeredDate2 = null, registeredDate3 = null, registeredDate4 = null, registeredDate5 = null;

    int size = 0;
    
    
    System.out.print("번호? ");
    no1 = Integer.parseInt(keyboardScan.nextLine());

    System.out.print("이름? ");
    name1 = keyboardScan.nextLine();

    System.out.print("이메일? ");
    email1 = keyboardScan.nextLine();

    System.out.print("암호? ");
    password1 = keyboardScan.nextLine();

    System.out.print("사진? ");
    photo1 = keyboardScan.nextLine();

    System.out.print("전화? ");
    tel1 = keyboardScan.nextLine();

    registeredDate1 = new java.sql.Date(System.currentTimeMillis());

    size++;
    System.out.println(); // 빈 줄 출력

    System.out.print("계속 입력하시겠습니까?(y/N) ");
    String str = keyboardScan.nextLine();
    
    if (str.equalsIgnoreCase("y")) {
      System.out.print("번호? ");
      no2 = Integer.parseInt(keyboardScan.nextLine());

      System.out.print("이름? ");
      name2 = keyboardScan.nextLine();

      System.out.print("이메일? ");
      email2 = keyboardScan.nextLine();

      System.out.print("암호? ");
      password2 = keyboardScan.nextLine();

      System.out.print("사진? ");
      photo2 = keyboardScan.nextLine();

      System.out.print("전화? ");
      tel2 = keyboardScan.nextLine();

      registeredDate2 = new java.sql.Date(System.currentTimeMillis());

      size++;
      System.out.println(); // 빈 줄 출력

      System.out.print("계속 입력하시겠습니까?(y/N) ");
      str = keyboardScan.nextLine();
      
      if (str.equalsIgnoreCase("y")) {
        System.out.print("번호? ");
        no3 = Integer.parseInt(keyboardScan.nextLine());

        System.out.print("이름? ");
        name3 = keyboardScan.nextLine();

        System.out.print("이메일? ");
        email3 = keyboardScan.nextLine();

        System.out.print("암호? ");
        password3 = keyboardScan.nextLine();

        System.out.print("사진? ");
        photo3 = keyboardScan.nextLine();

        System.out.print("전화? ");
        tel3 = keyboardScan.nextLine();

        registeredDate3 = new java.sql.Date(System.currentTimeMillis());

        size++;
        System.out.println(); // 빈 줄 출력

        System.out.print("계속 입력하시겠습니까?(y/N) ");
        str = keyboardScan.nextLine();
        
        if (str.equalsIgnoreCase("y")) {
          System.out.print("번호? ");
          no4 = Integer.parseInt(keyboardScan.nextLine());

          System.out.print("이름? ");
          name4 = keyboardScan.nextLine();

          System.out.print("이메일? ");
          email4 = keyboardScan.nextLine();

          System.out.print("암호? ");
          password4 = keyboardScan.nextLine();

          System.out.print("사진? ");
          photo4 = keyboardScan.nextLine();

          System.out.print("전화? ");
          tel4 = keyboardScan.nextLine();

          registeredDate4 = new java.sql.Date(System.currentTimeMillis());

          size++;
          System.out.println(); // 빈 줄 출력

          System.out.print("계속 입력하시겠습니까?(y/N) ");
          str = keyboardScan.nextLine();
          
          if (str.equalsIgnoreCase("y")) {
            System.out.print("번호? ");
            no5 = Integer.parseInt(keyboardScan.nextLine());

            System.out.print("이름? ");
            name5 = keyboardScan.nextLine();

            System.out.print("이메일? ");
            email5 = keyboardScan.nextLine();

            System.out.print("암호? ");
            password5 = keyboardScan.nextLine();

            System.out.print("사진? ");
            photo5 = keyboardScan.nextLine();

            System.out.print("전화? ");
            tel5 = keyboardScan.nextLine();

            registeredDate5 = new java.sql.Date(System.currentTimeMillis());

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
        no1, name1, email1, tel1, registeredDate1);
    if (size >= 2) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no2, name2, email2, tel2, registeredDate2);
    }
    if (size >= 3) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no3, name3, email3, tel3, registeredDate3);
    }
    if (size >= 4) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no4, name4, email4, tel4, registeredDate4);
    }
    if (size >= 5) {
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          no5, name5, email5, tel5, registeredDate5);
    }
  }
}
