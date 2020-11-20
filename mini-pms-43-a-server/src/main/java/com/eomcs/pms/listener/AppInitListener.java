package com.eomcs.pms.listener;

import java.io.File;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.filter.CommandFilterManager;
import com.eomcs.pms.filter.DefaultCommandFilter;
import com.eomcs.pms.filter.FilterChain;
import com.eomcs.pms.filter.LogCommandFilter;

public class AppInitListener implements ApplicationContextListener {
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)에 오신 걸 환영합니다!");

    try {
      // 필터 관리자 준비
      CommandFilterManager filterManager = new CommandFilterManager();

      // 필터를 등록한다.
      filterManager.add(new LogCommandFilter());
      //filterManager.add(new AuthCommandFilter());
      filterManager.add(new DefaultCommandFilter());

      // 필터가 사용할 값을 context 맵에 담는다.
      File logFile = new File("command.log");
      context.put("logFile", logFile);

      // 필터들을 준비시킨다.
      filterManager.init(context);

      // 사용자가 입력한 명령을 처리할 필터 체인을 얻는다.
      FilterChain filterChain = filterManager.getFilterChains();

      // 필터 체인을 사용할 수 있도록 context 맵 보관소에 저장해 둔다.
      context.put("filterChain", filterChain);

    } catch (Exception e) {
      System.out.println("필터 관리자 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)을 종료합니다!");
  }
}
