package com.eomcs.pms.domain;

import java.sql.Date;
import com.eomcs.util.CsvObject;

// Board 클래스는 CsvObject 규칙에 따라 구현했기 때문에 
// 이 클래스는 toCsvString() 메서드가 있음을 보장한다.
// 따라서 이 클래스의 객체를 사용하는 측에서는 
// 확실하고 일관되게 메서드를 호출하여 CSV 문자열을 추출할 수 있다.
//
public class Board implements CsvObject {
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

  // 이제 이 메서드는 CsvObject 인터페이스를 통해 
  // 공식적인 규칙으로서 사용될 것이다.
  // 즉 Board 클래스에서 임의로 만든 메서드가 아니라
  // 인터페이스를 통해 공개된 메서드로 격상되었다.
  @Override
  public String toCsvString() {
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

  // 다른 생성자가 있으면 컴파일러가 기본 생성자를 만들어주지 않으니까
  // 다음과 같이 별도로 만들어야 한다.
  public Board() {}

  // CSV 문자열을 받아 인스턴스 필드를 초기화시키는 생성자
  public Board(String csv) {
    String[] fields = csv.split(",");

    this.setNo(Integer.parseInt(fields[0]));
    this.setTitle(fields[1]);
    this.setContent(fields[2]);
    this.setWriter(fields[3]);
    this.setRegisteredDate(Date.valueOf(fields[4]));
    this.setViewCount(Integer.parseInt(fields[5]));
  }
}






