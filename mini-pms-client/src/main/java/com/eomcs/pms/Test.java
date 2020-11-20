package com.eomcs.pms;

import java.net.URLDecoder;

public class Test {
  public static void main(String[] args) throws Exception {
    String str = "abc%20def";
    System.out.println(URLDecoder.decode(str, "UTF-8"));
  }
}
