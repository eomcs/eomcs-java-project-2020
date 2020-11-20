package com.eomcs.pms;

public class Test {
  public static void main(String[] args) {
    String str = "SessionID=";
    String[] values = str.split("=");
    for (String value : values) {
      System.out.println("> " + value);

    }
  }
}
