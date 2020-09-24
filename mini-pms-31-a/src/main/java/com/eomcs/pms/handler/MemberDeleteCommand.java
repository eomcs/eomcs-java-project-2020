package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberDeleteCommand implements Command {

  List<Member> memberList;

  public MemberDeleteCommand(List<Member> list) {
    this.memberList = list;
  }

  @Override
  public void execute() {
    System.out.println("[회원 삭제]");
    int no = Prompt.inputInt("번호? ");
    int index = indexOf(no);

    if (index == -1) {
      System.out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("회원 삭제를 취소하였습니다.");
      return;
    }

    memberList.remove(index);
    System.out.println("회원을 삭제하였습니다.");
  }

  private int indexOf(int no) {
    for (int i = 0; i < memberList.size(); i++) {
      Member member = memberList.get(i);
      if (member.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
