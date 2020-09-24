package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

//Task 클래스는 CsvObject 규칙에 따라 구현했기 때문에 
//이 클래스는 toCsvString() 메서드가 있음을 보장한다.
//따라서 이 클래스의 객체를 사용하는 측에서는 
//확실하고 일관되게 메서드를 호출하여 CSV 문자열을 추출할 수 있다.
//
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

  @Override
  public String toCsvString() {
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
