package com.eomcs.pms.handler;

import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateCommand implements Command {
  MemberDao memberDao;

  public MemberUpdateCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    System.out.println("[회원 변경]");
    int no = Prompt.inputInt("번호? ");

    try {
      Member member = memberDao.findByNo(no);
      if (member == null) {
        System.out.println("해당 번호의 회원이 존재하지 않습니다.");
        return;
      }

      member.setName(Prompt.inputString(String.format(
          "이름(%s)? ", member.getName())));
      member.setEmail(Prompt.inputString(String.format(
          "이메일(%s)? ", member.getEmail())));
      member.setPassword(Prompt.inputString("암호? "));
      member.setPhoto(Prompt.inputString(String.format(
          "사진(%s)? ", member.getPhoto())));
      member.setTel(Prompt.inputString(String.format(
          "전화(%s)? ", member.getTel())));

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!response.equalsIgnoreCase("y")) {
        System.out.println("회원 변경을 취소하였습니다.");
        return;
      }

      if (memberDao.update(member) == 0) {
        System.out.println("해당 번호의 회원이 존재하지 않습니다.");
      } else {
        System.out.println("회원을 변경하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("회원 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
