package com.eomcs.pms.handler;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;

public class MemberListCommand implements Command {
  MemberDao memberDao;

  public MemberListCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[회원 목록]");

    try {
      List<Member> list = memberDao.findAll();
      System.out.println("번호, 이름, 이메일, 전화, 등록일");
      for (Member member : list) {
        System.out.printf("%d, %s, %s, %s, %s\n",
            member.getNo(),
            member.getName(),
            member.getEmail(),
            member.getTel(),
            member.getRegisteredDate());
      }
    } catch (Exception e) {
      System.out.println("회원 목록 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
