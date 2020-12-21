package com.eomcs.pms.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("프로젝트 관리 시스템(PMS)에 오신 걸 환영합니다!");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("프로젝트 관리 시스템(PMS)을 종료합니다!");
  }
}
