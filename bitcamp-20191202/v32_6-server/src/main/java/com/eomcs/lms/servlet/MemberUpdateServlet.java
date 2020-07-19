package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import com.eomcs.lms.domain.Member;

public class MemberUpdateServlet implements Servlet {

  List<Member> members;

  public MemberUpdateServlet(List<Member> members) {
    this.members = members;
  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Member member = (Member) in.readObject();

    int index = -1;
    for (int i = 0; i < members.size(); i++) {
      if (members.get(i).getNo() == member.getNo()) {
        index = i;
        break;
      }
    }

    if (index != -1) {
      members.set(index, member);
      out.writeUTF("OK");
    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 회원이 없습니다.");
    }
  }
}
