package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

public class Request {
  String commandPath;
  Map<String,Object> context;
  PrintWriter out;
  BufferedReader in;

  public Request(
      String commandPath,
      Map<String,Object> context,
      PrintWriter out,
      BufferedReader in) {
    this.commandPath = commandPath;
    this.context = context;
    this.out = out;
    this.in = in;
  }

  public String getCommandPath() {
    return commandPath;
  }

  public Map<String, Object> getContext() {
    return context;
  }

  public PrintWriter getWriter() {
    return out;
  }

  public BufferedReader getReader() {
    return in;
  }
}




