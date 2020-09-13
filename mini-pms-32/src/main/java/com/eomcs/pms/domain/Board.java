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

  public static Board valueOfCsv(String csv) {
    String[] data = csv.split(",");

    Board board = new Board();
    board.setNo(Integer.parseInt(data[0]));
    board.setTitle(data[1]);
    board.setContent(data[2]);
    board.setWriter(data[3]);
    board.setRegisteredDate(Date.valueOf(data[4]));
    board.setViewCount(Integer.parseInt(data[5]));

    return board;
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d\n", 
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getWriter(),
        this.getRegisteredDate(),
        this.getViewCount());
  }

}