package com.eomcs.pms.listener;

import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.dao.mariadb.BoardDaoImpl;
import com.eomcs.pms.dao.mariadb.MemberDaoImpl;
import com.eomcs.pms.dao.mariadb.ProjectDaoImpl;
import com.eomcs.pms.dao.mariadb.TaskDaoImpl;
import com.eomcs.pms.service.BoardService;
import com.eomcs.pms.service.DefaultBoardService;
import com.eomcs.pms.service.DefaultMemberService;
import com.eomcs.pms.service.DefaultProjectService;
import com.eomcs.pms.service.DefaultTaskService;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.SqlSessionFactoryProxy;

// 게시물, 회원, 프로젝트, 작업 데이터를 파일에서 로딩하고 파일로 저장하는 일을 한다.
public class DataHandlerListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    // 시스템에서 사용할 객체를 준비한다.
    try {
      // Mybatis 객체 준비
      SqlSessionFactoryProxy sqlSessionFactory = new SqlSessionFactoryProxy(
          new SqlSessionFactoryBuilder().build(
              Resources.getResourceAsStream("com/eomcs/pms/conf/mybatis-config.xml")));

      // DAO 구현체 생성
      BoardDao boardDao = new BoardDaoImpl(sqlSessionFactory);
      MemberDao memberDao = new MemberDaoImpl(sqlSessionFactory);
      ProjectDao projectDao = new ProjectDaoImpl(sqlSessionFactory);
      TaskDao taskDao = new TaskDaoImpl(sqlSessionFactory);

      // Service 구현체 생성
      BoardService boardService = new DefaultBoardService(boardDao);
      MemberService memberService = new DefaultMemberService(memberDao);
      ProjectService projectService = new DefaultProjectService(taskDao, projectDao, sqlSessionFactory);
      TaskService taskService = new DefaultTaskService(taskDao);

      // 다른 객체가 사용할 수 있도록 context 맵 보관소에 저장해둔다.
      context.put("boardService", boardService);
      context.put("memberService", memberService);
      context.put("projectService", projectService);
      context.put("taskService", taskService);

    } catch (Exception e) {
      System.out.println("Mybatis 및 DAO, 서비스 객체 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
  }
}
