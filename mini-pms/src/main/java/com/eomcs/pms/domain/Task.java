package com.eomcs.pms.domain;

import java.sql.Date;

public class Task {
  private int no;
  private String content;
  private Date deadline;
  private int status;
  private String owner;

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getDeadline() {
    return deadline;
  }
  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }

  // 객체의 필드 값을 CSV 형식의 문자열로 만들어 리턴한다.
  public String toCsvString() {
    // CSV 문자열을 만들 때 줄 바꿈 코드를 붙이지 않는다.
    // 줄바꿈 코드는 CSV 문자열을 받아서 사용하는 쪽에서 다룰 문제다. 
    return String.format("%d,%s,%s,%d,%s", 
        this.getNo(),
        this.getContent(),
        this.getDeadline(),
        this.getStatus(),
        this.getOwner());
  }

  // CSV 문자열을 가지고 객체를 생성한다.
  public static Task valueOfCsv(String csv) {
    String[] data = csv.split(",");

    Task task = new Task();
    task.setNo(Integer.parseInt(data[0]));
    task.setContent(data[1]);
    task.setDeadline(Date.valueOf(data[2]));
    task.setStatus(Integer.parseInt(data[3]));
    task.setOwner(data[4]);

    return task;
  }

}
