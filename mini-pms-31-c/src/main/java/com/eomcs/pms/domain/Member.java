package com.eomcs.pms.domain;

import java.sql.Date;

public class Member {
  private int no;
  private String name;
  private String email;
  private String password;
  private String photo;
  private String tel;
  private Date registeredDate;

  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getPhoto() {
    return photo;
  }
  public void setPhoto(String photo) {
    this.photo = photo;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }

  // 객체의 필드 값을 CSV 형식의 문자열로 만들어 리턴한다.
  public String toCsvString() {
    // CSV 문자열을 만들 때 줄 바꿈 코드를 붙이지 않는다.
    // 줄바꿈 코드는 CSV 문자열을 받아서 사용하는 쪽에서 다룰 문제다. 
    return String.format("%d,%s,%s,%s,%s,%s,%s", 
        this.getNo(),
        this.getName(),
        this.getEmail(),
        this.getPassword(),
        this.getPhoto(),
        this.getTel(),
        this.getRegisteredDate());
  }

  // CSV 문자열을 가지고 객체를 생성한다.
  public static Member valueOfCsv(String csv) {
    String[] fields = csv.split(",");

    Member member = new Member();
    member.setNo(Integer.parseInt(fields[0]));
    member.setName(fields[1]);
    member.setEmail(fields[2]);
    member.setPassword(fields[3]);
    member.setPhoto(fields[4]);
    member.setTel(fields[5]);
    member.setRegisteredDate(Date.valueOf(fields[6]));

    return member;
  }
}
