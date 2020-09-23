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

  public String toCsvString() {
    return String.format("%d,%s,%s,%d,%s\n", 
        this.getNo(),
        this.getContent(),
        this.getDeadline(),
        this.getStatus(),
        this.getOwner());
  }

}
