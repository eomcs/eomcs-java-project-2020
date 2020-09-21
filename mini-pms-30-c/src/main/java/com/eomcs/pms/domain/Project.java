package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

public class Project implements CsvObject {
  private int no;
  private String title;
  private String content;
  private Date startDate;
  private Date endDate;
  private String owner;
  private String members;

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getOwner() {
    return owner;
  }
  public void setOwner(String owner) {
    this.owner = owner;
  }
  public String getMembers() {
    return members;
  }
  public void setMembers(String members) {
    this.members = members;
  }

  public Project(String csv) {
    String[] data = csv.split(",");

    this.setNo(Integer.parseInt(data[0]));
    this.setTitle(data[1]);
    this.setContent(data[2]);
    this.setStartDate(Date.valueOf(data[3]));
    this.setEndDate(Date.valueOf(data[4]));
    this.setOwner(data[5]);
    this.setMembers(data[6]);
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s\n", 
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getStartDate(),
        this.getEndDate(),
        this.getOwner(),
        this.getMembers());
  }

}
