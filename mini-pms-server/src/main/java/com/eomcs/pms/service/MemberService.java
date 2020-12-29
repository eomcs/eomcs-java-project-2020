package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Member;

public interface MemberService {
  int delete(int no) throws Exception;
  int add(Member member) throws Exception;
  List<Member> list() throws Exception;
  List<Member> list(String name) throws Exception;
  List<Member> listForProject(int projectNo) throws Exception;
  Member get(int no) throws Exception;
  Member get(String email, String password) throws Exception;
  Member get(String email) throws Exception;
  int update(Member member) throws Exception;
}
