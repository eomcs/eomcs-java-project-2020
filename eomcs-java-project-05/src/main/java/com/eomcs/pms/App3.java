package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App3 {

  public static void main(String[] args) {
    System.out.println("[작업]");

    // 키보드에서 사용자가 입력한 값을 읽어 문자열이나 정수, 부동소수점 등으로 리턴하는 역할
    Scanner keyboardScan = new Scanner(System.in);

    System.out.print("번호? ");
    int no = Integer.parseInt(keyboardScan.nextLine());

    System.out.print("내용? ");
    String content = keyboardScan.nextLine();

    System.out.print("완료일? ");
    Date endDate = Date.valueOf(keyboardScan.nextLine());

    System.out.print("프로젝트 번호? ");
    int projectNo = Integer.parseInt(keyboardScan.nextLine());

    System.out.println("상태?");
    System.out.println("0: 신규");
    System.out.println("1: 진행중");
    System.out.println("2: 완료");
    System.out.print("> ");
    int state = Integer.valueOf(keyboardScan.nextLine());

    keyboardScan.close();

    System.out.println("--------------------------------");

    System.out.printf("번호: %d\n", no);
    System.out.printf("내용: %s\n", content);
    System.out.printf("완료일: %s\n", endDate);
    System.out.printf("프로젝트 번호: %d\n", projectNo);

    switch (state) {
      case 1:
        System.out.println("상태: 진행중");
        break;
      case 2:
        System.out.println("상태: 완료");
        break;
      default:
        System.out.println("상태: 신규");
    }

  }
}
