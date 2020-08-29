package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Member;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Prompt;

public class MemberHandler {

  //Member 객체 목록을 저장할 ArrayList 객체를 준비한다.
  ArrayList<Member> memberList = new ArrayList<>();

  // 다른 패키지에서 이 메서드를 사용할 수 있도록 public 으로 사용 범위를 공개한다.
  public void add() {
    System.out.println("[회원 등록]");

    Member member = new Member();
    member.setNo(Prompt.inputInt("번호? "));
    member.setName(Prompt.inputString("이름? "));
    member.setEmail(Prompt.inputString("이메일? "));
    member.setPassword(Prompt.inputString("암호? "));
    member.setPhoto(Prompt.inputString("사진? "));
    member.setTel(Prompt.inputString("전화? "));
    member.setRegisteredDate(new java.sql.Date(System.currentTimeMillis()));

    // 제네릭 문법에 따라 add()를 호출할 때 넘겨줄 수 있는 값은 
    // Member 또는 그 하위 타입의 인스턴스만 가능하다.
    // 다른 타입은 불가능하다.
    memberList.add(member);
  }

  public void list() {
    System.out.println("[회원 목록]");

    // 제네릭 문법에 따라 리턴 타입이 'Member[]' 이기 때문에
    // 따로 형변환 할 필요가 없다.
    // 대신 Member[] 배열을 리턴해 달라는 의미로 배열의 타입 정보를 넘긴다.
    Member[] members = memberList.toArray(Member[].class);

    for (Member member : members) {
      System.out.printf("%d, %s, %s, %s, %s\n",
          member.getNo(),
          member.getName(),
          member.getEmail(),
          member.getTel(),
          member.getRegisteredDate());
    }
  }

  public Member findByName(String name) {
    // 제네릭 문법에 따라 리턴 타입이 'Member[]' 이기 때문에
    // 따로 형변환 할 필요가 없다.
    // 대신 Member[] 배열을 리턴해 달라는 의미로 배열의 타입 정보를 넘긴다.
    Member[] members = memberList.toArray(Member[].class);

    for (Member member : members) {
      if (member.getName().equals(name)) {
        return member;
      }
    }
    return null;
  }
}
