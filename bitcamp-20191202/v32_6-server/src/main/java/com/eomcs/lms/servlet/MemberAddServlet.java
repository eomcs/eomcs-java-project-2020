package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import com.eomcs.lms.domain.Member;

public class MemberAddServlet implements Servlet {

  List<Member> members;

  public MemberAddServlet(List<Member> members) {
    this.members = members;
  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Member member = (Member) in.readObject();

    int i = 0;
    for (; i < members.size(); i++) {
      if (members.get(i).getNo() == member.getNo()) {
        break;
      }
    }

    if (i == members.size()) {
      members.add(member);
      out.writeUTF("OK");

    } else {
      out.writeUTF("FAIL");
      out.writeUTF("같은 번호의 회원이 있습니다.");
    }
  }
}
