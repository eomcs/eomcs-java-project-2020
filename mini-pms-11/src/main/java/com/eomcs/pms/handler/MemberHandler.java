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
  static Member[] list = new Member[LENGTH]; // list로 이름을 바꾼다.
  static int size = 0;

  // 다른 패키지에서 이 메서드를 사용할 수 있도록 public 으로 사용 범위를 공개한다.
  public static void add() {
    System.out.println("[회원 등록]");
    
    Member member = new Member();
    member.no = Prompt.inputInt("번호? ");
    member.name = Prompt.inputString("이름? ");
    member.email = Prompt.inputString("이메일? ");
    member.password = Prompt.inputString("암호? ");
    member.photo = Prompt.inputString("사진? ");
    member.tel = Prompt.inputString("전화? ");
    member.registeredDate = new java.sql.Date(System.currentTimeMillis());
    
    list[size++] = member;
  }
  
  public static void list() {
    System.out.println("[회원 목록]");
    
    for (int i = 0; i < size; i++) {
      Member member = list[i];
      System.out.printf("%d, %s, %s, %s, %s\n",
          member.no, 
          member.name, 
          member.email, 
          member.tel, 
          member.registeredDate);
    }
  }
  
  public static Member findByName(String name) {
    for (int i = 0; i < size; i++) {
      Member member = list[i];
      if (member.name.equals(name)) {
        return member;
      }
    }
    return null;
  }
}
