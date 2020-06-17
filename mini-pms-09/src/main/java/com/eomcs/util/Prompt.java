package com.eomcs.util;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {
  static Scanner keyboardScan = new Scanner(System.in);

  public static String inputString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  public static int inputInt(String title) {
    System.out.print(title);
    return Integer.parseInt(keyboardScan.nextLine());
  }

  public static Date inputDate(String title) {
    System.out.print(title);
    return Date.valueOf(keyboardScan.nextLine());
  }

  // 키보드 입력 스트림을 닫는 메서드 추가
  public static void close() {
    keyboardScan.close();
  }
}
