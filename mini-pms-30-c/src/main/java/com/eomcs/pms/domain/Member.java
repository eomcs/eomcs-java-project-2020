package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

public class Member implements CsvObject {
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

  public Member(String csv) {
    String[] data = csv.split(",");

    this.setNo(Integer.parseInt(data[0]));
    this.setName(data[1]);
    this.setEmail(data[2]);
    this.setPassword(data[3]);
    this.setPhoto(data[4]);
    this.setTel(data[5]);
    this.setRegisteredDate(Date.valueOf(data[6]));
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s\n", 
        this.getNo(),
        this.getName(),
        this.getEmail(),
        this.getPassword(),
        this.getPhoto(),
        this.getTel(),
        this.getRegisteredDate());
  }
}
