package com.eomcs.pms.domain;

import java.sql.Date;

public class Board {
  private int no;
  private String title;
  private String content;
  private String writer;
  private Date registeredDate;
  private int viewCount;

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
  public String getWriter() {
    return writer;
  }
  public void setWriter(String writer) {
    this.writer = writer;
  }
  public Date getRegisteredDate() {
    return registeredDate;
  }
  public void setRegisteredDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  // 객체의 필드 값을 CSV 형식의 문자열로 만들어 리턴한다.
  public String toCsvString() {
    // CSV 문자열을 만들 때 줄 바꿈 코드를 붙이지 않는다.
    // 줄바꿈 코드는 CSV 문자열을 받아서 사용하는 쪽에서 다룰 문제다. 
    return String.format("%d,%s,%s,%s,%s,%d", 
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getWriter(),
        this.getRegisteredDate(),
        this.getViewCount());
  }

  // CSV 문자열을 가지고 객체를 생성한다.
  public static Board valueOfCsv(String csv) {
    String[] fields = csv.split(",");

    Board board = new Board();
    board.setNo(Integer.parseInt(fields[0]));
    board.setTitle(fields[1]);
    board.setContent(fields[2]);
    board.setWriter(fields[3]);
    board.setRegisteredDate(Date.valueOf(fields[4]));
    board.setViewCount(Integer.parseInt(fields[5]));

    return board;
  }

}






