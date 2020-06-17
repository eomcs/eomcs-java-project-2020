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
    System.out.print(title);
    return Integer.parseInt(keyboardScan.nextLine());
  }

  static Date inputDate(String title) {
    System.out.print(title);
    return Date.valueOf(keyboardScan.nextLine());
  }
}
