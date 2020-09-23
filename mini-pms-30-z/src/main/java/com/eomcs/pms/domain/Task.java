package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

public class Task implements CsvObject {
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

  public Task(String csv) {
    String[] data = csv.split(",");

    this.setNo(Integer.parseInt(data[0]));
    this.setContent(data[1]);
    this.setDeadline(Date.valueOf(data[2]));
    this.setStatus(Integer.parseInt(data[3]));
    this.setOwner(data[4]);
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%d,%s\n", 
        this.getNo(),
        this.getContent(),
        this.getDeadline(),
        this.getStatus(),
        this.getOwner());
  }

}
