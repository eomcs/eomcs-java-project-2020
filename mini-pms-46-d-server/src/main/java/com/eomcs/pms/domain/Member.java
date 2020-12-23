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
  private int state;

  public int getNo() {
    return no;
  }
  public Member setNo(int no) {
    this.no = no;
    return this;
  }
  public String getName() {
    return name;
  }
  public Member setName(String name) {
    this.name = name;
    return this;
  }
  public String getEmail() {
    return email;
  }
  public Member setEmail(String email) {
    this.email = email;
    return this;
  }
  public String getPassword() {
    return password;
  }
  public Member setPassword(String password) {
    this.password = password;
    return this;
  }
  public String getPhoto() {
    return photo;
  }
  public Member setPhoto(String photo) {
    this.photo = photo;
    return this;
  }
  public String getTel() {
    return tel;
  }
  public Member setTel(String tel) {
    this.tel = tel;
    return this;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public Member setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
    return this;
  }
  public int getState() {
    return state;
  }
  public void setState(int state) {
    this.state = state;
  }
}






