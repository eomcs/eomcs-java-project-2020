package com.eomcs.pms;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {
  static Scanner keyboardScan = new Scanner(System.in);

  static String inputString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  static Date inputDate(String title) {
    return Date.valueOf(inputString(title));
  }
  
  // 프롬프트의 사용이 모두 끝났으면 
  // 이 메서드를 호출하여 System.in 입력 스트림 자원을 해제하도록 한다.
  static void close() {
    keyboardScan.close();
  }
}
