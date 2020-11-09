package com.eomcs.pms.filter;

import com.eomcs.pms.handler.Request;

// 역할:
// - CommandFilter 구현체를 관리하고 실행시킨다.
// -
public class CommandFilterManager {
  Chain firstChain;
  Chain lastChain;

  public void add(CommandFilter filter) {
    Chain chain = new Chain(filter);
    if (lastChain == null) {
      firstChain = lastChain = chain;
      return;
    }
    lastChain.nextChain = chain;
    lastChain = chain;
  }

  public FilterChain getFilterChains() {
    return firstChain;
  }

  private static class Chain implements FilterChain {
    CommandFilter filter;
    Chain nextChain;

    public Chain(CommandFilter filter) {
      this.filter = filter;
    }

    @Override
    public void doFilter(Request request) throws Exception {
      filter.doFilter(request, nextChain);
    }
  }
}
