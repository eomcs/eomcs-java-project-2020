package com.eomcs.pms.domain;

import java.sql.Date;

public class Project {
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

  // 객체의 필드 값을 CSV 형식의 문자열로 만들어 리턴한다.
  public String toCsvString() {
    // CSV 문자열을 만들 때 줄 바꿈 코드를 붙이지 않는다.
    // 줄바꿈 코드는 CSV 문자열을 받아서 사용하는 쪽에서 다룰 문제다. 
    return String.format("%d,%s,%s,%s,%s,%s,%s", 
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getStartDate(),
        this.getEndDate(),
        this.getOwner(),
        this.getMembers());
  }

  // CSV 문자열을 가지고 객체를 생성한다.
  public static Project valueOfCsv(String csv) {
    String[] fields = csv.split(",");

    Project project = new Project();
    project.setNo(Integer.parseInt(fields[0]));
    project.setTitle(fields[1]);
    project.setContent(fields[2]);
    project.setStartDate(Date.valueOf(fields[3]));
    project.setEndDate(Date.valueOf(fields[4]));
    project.setOwner(fields[5]);
    project.setMembers(fields[6]);

    return project;
  }

}
