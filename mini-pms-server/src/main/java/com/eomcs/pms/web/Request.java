package com.eomcs.pms.web;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

public class Request {
  String commandPath;
  Map<String,Object> context;
  PrintWriter out;
  BufferedReader in;
  String sessionId;

  public Request(
      String commandPath,
      Map<String,Object> context,
      PrintWriter out,
      BufferedReader in,
      String sessionId) {

    this.commandPath = commandPath;
    this.context = context;
    this.out = out;
    this.in = in;
    this.sessionId = sessionId;
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

  @SuppressWarnings("unchecked")
  public Map<String,Object> getSession() {
    return (Map<String,Object>) context.get(sessionId);
  }

  public void invalidateSession() {
    context.remove(sessionId);
  }
}




