package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Member;

public interface MemberService {
  List<Member> list(String name) throws Exception;
}
