package com.eomcs.util;

import java.io.PrintStream;
import java.sql.Date;
import java.util.Scanner;

public class Prompt {
  public static int getInt(Scanner in, PrintStream out, String title) {
    return Integer.parseInt(getString(in, out, title));
  }

  public static int getInt(Scanner in, PrintStream out, String title, String defaultValue) {
    return Integer.parseInt(getString(in, out, title, defaultValue));
  }

  public static String getString(Scanner in, PrintStream out, String title) {
    out.println(title);
    out.println("!{}!");
    out.flush();
    return in.nextLine();
  }

  public static String getString(Scanner in, PrintStream out, String title, String defaultValue) {
    out.println(title);
    out.println("!{}!");
    out.flush();
    String value = in.nextLine();

    if (value.length() != 0) {
      return value;
    } else {
      return defaultValue;
    }
  }

  public static Date getDate(Scanner in, PrintStream out, String title) {
    return Date.valueOf(getString(in, out, title));
  }

  public static Date getDate(Scanner in, PrintStream out, String title, String defaultValue) {
    return Date.valueOf(getString(in, out, title, defaultValue));
  }
}
