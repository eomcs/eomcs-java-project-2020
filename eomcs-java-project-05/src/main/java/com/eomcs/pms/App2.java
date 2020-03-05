package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App2 {

  public static void main(String[] args) {
    System.out.println("[프로젝트]");

    // 키보드에서 사용자가 입력한 값을 읽어 문자열이나 정수, 부동소수점 등으로 리턴하는 역할
    Scanner keyboardScan = new Scanner(System.in);

    System.out.print("번호? ");
    int no = keyboardScan.nextInt();
    keyboardScan.nextLine(); // 번호 뒤에 남아 있는 줄바꿈 코드를 제거한다.

    System.out.print("프로젝트명? ");
    String title = keyboardScan.nextLine();

    System.out.print("내용? ");
    String content = keyboardScan.nextLine();

    System.out.print("시작일? ");
    Date startDate = Date.valueOf(keyboardScan.nextLine());

    System.out.print("종료일? ");
    Date endDate = Date.valueOf(keyboardScan.nextLine());

    System.out.print("생성자 번호? ");
    int ownerNo = keyboardScan.nextInt();

    keyboardScan.close();

    System.out.println("--------------------------------");

    System.out.printf("번호: %d\n", no);
    System.out.printf("프로젝트명: %s\n", title);
    System.out.printf("내용: %s\n", content);
    System.out.printf("시작일: %s\n", startDate);
    System.out.printf("종료일: %s\n", endDate);
    System.out.printf("생성자 번호: %d\n", ownerNo);
  }
}
