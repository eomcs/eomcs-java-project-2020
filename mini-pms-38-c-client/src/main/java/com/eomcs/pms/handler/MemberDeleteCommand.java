package com.eomcs.pms.handler;

import com.eomcs.pms.dao.MemberDao;
import com.eomcs.util.Prompt;

public class MemberDeleteCommand implements Command {
  MemberDao memberDao;

  public MemberDeleteCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    System.out.println("[회원 삭제]");
    int no = Prompt.inputInt("번호? ");

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("회원 삭제를 취소하였습니다.");
      return;
    }

    try {
      if (memberDao.delete(no) == 0) {
        System.out.println("해당 번호의 회원이 존재하지 않습니다.");
      } else {
        System.out.println("회원을 삭제하였습니다.");
      }

    } catch (Exception e) {
      System.out.println("회원 삭제 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
