package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateServlet implements Servlet {

  MemberDao memberDao;

  public MemberUpdateServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    int no = Prompt.getInt(in, out, "번호? ");

    Member old = memberDao.findByNo(no);
    if (old == null) {
      out.println("해당 번호의 회원이 없습니다.");
      return;
    }

    Member member = new Member();

    member.setNo(no);
    member.setName(Prompt.getString(in, out, //
        String.format("이름(%s)? \n", old.getName()), //
        old.getName()));
    member.setEmail(Prompt.getString(in, out, //
        String.format("이메일(%s)? \n", old.getEmail()), //
        old.getEmail()));
    member.setPassword(Prompt.getString(in, out, "암호? "));
    member.setPhoto(Prompt.getString(in, out, //
        String.format("사진(%s)? \n", old.getPhoto()), //
        old.getPhoto()));
    member.setTel(Prompt.getString(in, out, //
        String.format("전화(%s)? \n", old.getTel()), //
        old.getTel()));

    if (memberDao.update(member) > 0) {
      out.println("회원을 변경했습니다.");

    } else {
      out.println("변경에 실패했습니다.");
    }
  }
}
