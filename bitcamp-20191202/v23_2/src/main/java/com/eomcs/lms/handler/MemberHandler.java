package com.eomcs.lms.handler;

import java.sql.Date;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.AbstractList;
import com.eomcs.util.Prompt;

public class MemberHandler {
  
  // ArrayList나 LinkedList를 마음대로 사용할 수 있도록 
  // 객체 목록을 관리하는 필드를 선언할 때  
  // 이들 클래스의 수퍼 클래스로 선언한다.
  // => 대신 이 필드에 들어갈 객체는 생성자에서 파라미터로 받는다.
  // => 이렇게 하면 ArrayList도 사용할 수 있고, LinkedList도 사용할 수 있어
  //    유지보수에 좋다. 즉 선택의 폭이 넓어진다.
  AbstractList<Member> memberList;

  Prompt prompt;

  public MemberHandler(Prompt prompt, AbstractList<Member> list) {
    this.prompt = prompt;
    this.memberList = list;
    // Handler가 사용할 List 객체(의존 객체; dependency)를 생성자에서 직접 만들지 않고
    // 이렇게 생성자가 호출될 때 파라미터로 받으면,
    // 필요에 따라 List 객체를 다른 객체로 대체하기가 쉽다.
    // 예를 들어 ArrayList를 사용하다가 LinkedList로 바꾸기 쉽다.
    // LinkeList를 사용하다가 다른 객체로 바꾸기가 쉽다.
    // 즉 다형적 변수에 법칙에 따라 List의 하위 객체라면 어떤 객체든지 가능하다.
    // 이런식으로 의존 객체를 외부에서 주입받는 것을 
    // "Dependency Injection(DI; 의존성주입)"이라 부른다.
    // => 즉 의존 객체를 부품화하여 교체하기 쉽도록 만드는 방식이다. 
  }
  
  public void listMember() {
    // Member 객체의 목록을 저장할 배열을 넘기는데 크기가 0인 배열을 넘긴다.
    // toArray()는 내부에서 새 배열을 만들고, 값을 복사한 후 리턴한다.
    Member[] arr = this.memberList.toArray(new Member[] {});
    for (Member m : arr) {
      System.out.printf("%d, %s, %s, %s, %s\n", 
          m.getNo(), m.getName(), m.getEmail(), 
          m.getTel(), m.getRegisteredDate());
    }
  }

  public void addMember() {
    Member member = new Member();

    member.setNo(prompt.inputInt("번호? "));
    member.setName(prompt.inputString("이름? "));
    member.setEmail(prompt.inputString("이메일? "));
    member.setPassword(prompt.inputString("암호? "));
    member.setPhoto(prompt.inputString("사진? "));
    member.setTel(prompt.inputString("전화? "));
    member.setRegisteredDate(new Date(System.currentTimeMillis()));
    
    this.memberList.add(member);
    
    System.out.println("저장하였습니다.");
  }
  
  public void detailMember() {
    int index = indexOfMember(prompt.inputInt("번호? "));
    
    if (index == -1) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }
    
    Member member = this.memberList.get(index);
    
    System.out.printf("번호: %d\n", member.getNo());
    System.out.printf("이름: %s\n", member.getName());
    System.out.printf("이메일: %s\n", member.getEmail());
    System.out.printf("암호: %s\n", member.getPassword());
    System.out.printf("사진: %s\n", member.getPhoto());
    System.out.printf("전화: %s\n", member.getTel());
  }
  
  public void updateMember() {
    int index = indexOfMember(prompt.inputInt("번호? "));
    
    if (index == -1) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }
    
    Member oldMember = this.memberList.get(index);
    Member newMember = new Member();
    
    newMember.setNo(oldMember.getNo());
    
    newMember.setName(prompt.inputString(
        String.format("이름(%s)? ", oldMember.getName()), 
        oldMember.getName()));

    newMember.setEmail(prompt.inputString(
        String.format("이메일(%s)? ", oldMember.getEmail()), 
        oldMember.getEmail()));
    
    newMember.setPassword(prompt.inputString(
        String.format("암호(%s)? ", oldMember.getPassword()), 
        oldMember.getPassword()));
    
    newMember.setPhoto(prompt.inputString(
        String.format("사진(%s)? ", oldMember.getPhoto()), 
        oldMember.getPhoto()));
    
    newMember.setTel(prompt.inputString(
        String.format("전화(%s)? ", oldMember.getTel()), 
        oldMember.getTel()));
    
    if (oldMember.equals(newMember)) {
      System.out.println("회원 변경을 취소하였습니다.");
      return;
    }
    this.memberList.set(index, newMember);
    System.out.println("회원을 변경했습니다.");
  }
  
  public void deleteMember() {
    int index = indexOfMember(prompt.inputInt("번호? "));
    
    if (index == -1) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }
    
    this.memberList.remove(index);
    
    System.out.println("회원을 삭제했습니다.");
  }
  
  private int indexOfMember(int no) {
    for (int i = 0; i < this.memberList.size(); i++) {
      if (this.memberList.get(i).getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
