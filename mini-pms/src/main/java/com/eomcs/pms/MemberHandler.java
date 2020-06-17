package com.eomcs.pms;

import java.sql.Date;

public class MemberHandler {

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
  static Member[] list = new Member[LENGTH];
  static int size = 0;

  static void add() {
    System.out.println("회원 등록!");

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

  static void list() {
    System.out.println("회원 목록!");

    for (int i = 0; i < size; i++) {
      // 번호, 이름, 이메일, 전화, 가입일
      System.out.printf("%d, %s, %s, %s, %s\n", // 출력 형식 지정
          list[i].no, // 회원 번호
          list[i].name, // 이름
          list[i].email, // 이메일
          list[i].tel, // 전화
          list[i].registeredDate // 가입일
          );
    }
  }
}
