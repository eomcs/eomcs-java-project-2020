package com.eomcs.pms.domain;

import java.io.Serializable;
import java.sql.Date;

//serialize 기능을 활성화시킨다.
//=> java.io.Serializable 인터페이스를 구현한다.
//
public class Task implements Serializable {
  private static final long serialVersionUID = 1L;

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


}
