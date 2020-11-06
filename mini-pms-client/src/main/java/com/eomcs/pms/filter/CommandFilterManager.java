package com.eomcs.pms.filter;

import java.util.ArrayList;
import com.eomcs.pms.handler.Request;

// 역할:
// - CommandFilter 구현체를 관리하고 실행시킨다.
// -
public class CommandFilterManager implements FilterChain {
  ArrayList<CommandFilter>  filters = new ArrayList<>();
  int nextFilterIndex = 0;

  public void add(CommandFilter filter) {
    filters.add(filter);
  }

  // 다음 순서의 필터를 실행시킨다.
  @Override
  public void doFilter(Request request) throws Exception {
    if (nextFilterIndex >= filters.size()) {
      // 다음 필터가 없다.
      return;
    }

    // 보관소에서 해당 인덱스의 필터를 꺼낸다.
    CommandFilter nextFilter = filters.get(nextFilterIndex++);
    nextFilter.doFilter(request, this);

  }
}
