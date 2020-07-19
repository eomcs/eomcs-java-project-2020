package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import com.eomcs.lms.domain.Member;

public class MemberDetailServlet implements Servlet {

  List<Member> members;

  public MemberDetailServlet(List<Member> members) {
    this.members = members;
  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();

    Member member = null;
    for (Member m : members) {
      if (m.getNo() == no) {
        member = m;
        break;
      }
    }

    if (member != null) {
      out.writeUTF("OK");
      out.writeObject(member);

    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 회원이 없습니다.");
    }
  }
}
