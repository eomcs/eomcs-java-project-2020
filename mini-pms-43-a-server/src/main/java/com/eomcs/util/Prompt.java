package com.eomcs.util;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Scanner;

public class Prompt {
  static Scanner keyboardScan = new Scanner(System.in);

  // 다른 패키지에서 메서드를 호출할 수 있도록 사용 범위를 public 으로 공개한다.
  public static String inputString(String title) {
    System.out.print(title);
    return keyboardScan.nextLine();
  }

  public static String inputString(
      String title,
      PrintWriter out,
      BufferedReader in) throws Exception {
    // 클라이언트로 출력할 때는 제목 다음에 "!{}!" 문자열을 보내
    // 클라이언트가 사용자로부터 값을 입력 받아 다시 서버에 보내도록 요청한다.
    out.println(title); // 클라이언트가 출력할 프롬프트 제목
    out.println("!{}!"); // 클라이언트에게 값을 보내라는 요청
    out.flush(); // 주의! 출력하면 버퍼에 쌓인다. 서버로 보내고 싶다면 flush()를 호출하라!
    return in.readLine(); // 클라이언트가 보낸 값을 읽기
  }

  public static int inputInt(String title) {
    return Integer.parseInt(inputString(title));
  }

  public static int inputInt(
      String title,
      PrintWriter out,
      BufferedReader in) throws Exception {
    return Integer.parseInt(inputString(title, out, in));
  }

  public static Date inputDate(String title) {
    return Date.valueOf(inputString(title));
  }

  public static Date inputDate(
      String title,
      PrintWriter out,
      BufferedReader in) throws Exception {
    return Date.valueOf(inputString(title, out, in));
  }

  // 프롬프트의 사용이 모두 끝났으면
  // 이 메서드를 호출하여 System.in 입력 스트림 자원을 해제하도록 한다.
  public static void close() {
    keyboardScan.close();
  }
}
