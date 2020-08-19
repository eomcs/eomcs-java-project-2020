package com.eomcs.pms.domain;

import java.sql.Date;

// 게시물 데이터
// - 패키지 멤버 클래스는 static 이 될 수 없다.
// - 다른 패키지에 있는 클래스가 사용해야 하기 때문에 public 으로 공개한다.
public class Board {
  // 다른 패키지의 클래스가 인스턴스 필드를 직접 사용할 수 있도록 public으로 공개한다.
  public int no;
  public String title;
  public String content;
  public String writer;
  public Date registeredDate;
  public int viewCount;
}