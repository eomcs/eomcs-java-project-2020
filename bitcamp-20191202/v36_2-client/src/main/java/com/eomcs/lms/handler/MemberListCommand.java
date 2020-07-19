package com.eomcs.lms.handler;

import java.util.List;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberListCommand implements Command {

  MemberDao memberDao;

  public MemberListCommand(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    try {
      List<Member> members = memberDao.findAll();
      for (Member m : members) {
        System.out.printf("%d, %s, %s, %s, %s\n", //
            m.getNo(), m.getName(), m.getEmail(), m.getTel(), m.getRegisteredDate());
      }
    } catch (Exception e) {
      System.out.println("목록 조회 실패!");
    }
  }
}
