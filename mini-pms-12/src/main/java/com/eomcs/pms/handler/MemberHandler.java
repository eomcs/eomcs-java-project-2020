package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.util.Prompt;

public class MemberHandler {

  // 회원 데이터
  static class Member {
    int no;
    String name;
    String email;
    String password;
    String photo;
    String tel;
    Date registeredDate;
  }
  static final int LENGTH = 100;

  Member[] list = new Member[LENGTH]; // list로 이름을 바꾼다.
  int size = 0;

  // 인스턴스 메서드
  // => 인스턴스가 있어야만 호출할 수 있는 메서드이다.
  // => 인스턴스를 사용하는 메서드인 경우 인스턴스 메서드로 선언하라.
  // => 호출할 때는 반드시 인스턴스 주소를 줘야 한다.
  //      인스턴스주소.메서드명();
  // => 이렇게 인스턴스의 변수 값을 다루는 메서드는
  //    "연산자(operation)"라 부를 수 있다.
  //
  // 다른 패키지에서 이 메서드를 사용할 수 있도록 public 으로 사용 범위를 공개한다.
  public void add() {
    System.out.println("[회원 등록]");

    Member member = new Member();
    member.no = Prompt.inputInt("번호? ");
    member.name = Prompt.inputString("이름? ");
    member.email = Prompt.inputString("이메일? ");
    member.password = Prompt.inputString("암호? ");
    member.photo = Prompt.inputString("사진? ");
    member.tel = Prompt.inputString("전화? ");
    member.registeredDate = new java.sql.Date(System.currentTimeMillis());

    this.list[this.size++] = member;
  }

  public void list() {
    System.out.println("[회원 목록]");

    for (int i = 0; i < this.size; i++) {
      Member member = this.list[i];
      System.out.printf("%d, %s, %s, %s, %s\n",
          member.no,
          member.name,
          member.email,
          member.tel,
          member.registeredDate);
    }
  }

  public Member findByName(String name) {
    for (int i = 0; i < this.size; i++) {
      Member member = this.list[i];
      if (member.name.equals(name)) {
        return member;
      }
    }
    return null;
  }
}
