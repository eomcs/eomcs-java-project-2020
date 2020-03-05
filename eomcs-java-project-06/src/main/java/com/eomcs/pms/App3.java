package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class App3 {

  public static void main(String[] args) {
    System.out.println("[작업]");

    Scanner keyboardScan = new Scanner(System.in);

    // 최대 100개의 작업 정보를 저장할 메모리 준비
    // => 배열의 크기를 미리 변수에 저장하여 사용한다.
    // => 코드 중간에 배열의 크기가 바뀌지 않도록 변수의 값 변경을 제한한다.
    // => 한 번 설정된 값은 바꿀 수 없음을 표시하기 위해 변수명을 대문자로 표현한다.
    final int LENGTH = 100;
    int[] no = new int[LENGTH];
    String[] content = new String[LENGTH];
    Date[] endDate = new Date[LENGTH];
    int[] projectNo = new int[LENGTH];
    int[] state = new int[LENGTH];

    int size = 0;
    for (int i = 0; i < 100; i++) {
      System.out.print("번호? ");
      no[i] = Integer.parseInt(keyboardScan.nextLine());

      System.out.print("내용? ");
      content[i] = keyboardScan.nextLine();

      System.out.print("완료일? ");
      endDate[i] = Date.valueOf(keyboardScan.nextLine());

      System.out.print("프로젝트 번호? ");
      projectNo[i] = Integer.parseInt(keyboardScan.nextLine());

      System.out.println("상태?");
      System.out.println("0: 신규");
      System.out.println("1: 진행중");
      System.out.println("2: 완료");
      System.out.print("> ");
      state[i] = Integer.valueOf(keyboardScan.nextLine());

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
      String stateLabel = null;
      switch (state[i]) {
        case 1:
          stateLabel = "진행중";
          break;
        case 2:
          stateLabel = "완료";
          break;
        default:
          stateLabel = "신규";
      }
      // 번호, 작업명, 종료일, 프로젝트 번호, 상태
      System.out.printf("%d, %s, %s, %d, %s\n", // 출력 형식 지정
          no[i], content[i], endDate[i], projectNo[i], stateLabel);
    }
  }
}
