package com.eomcs.pms.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
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
import com.eomcs.pms.handler.BoardAddCommand;
import com.eomcs.pms.handler.BoardDeleteCommand;
import com.eomcs.pms.handler.BoardDetailCommand;
import com.eomcs.pms.handler.BoardListCommand;
import com.eomcs.pms.handler.BoardUpdateCommand;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloCommand;
import com.eomcs.pms.handler.LoginCommand;
import com.eomcs.pms.handler.LogoutCommand;
import com.eomcs.pms.handler.MemberAddCommand;
import com.eomcs.pms.handler.MemberDeleteCommand;
import com.eomcs.pms.handler.MemberDetailCommand;
import com.eomcs.pms.handler.MemberListCommand;
import com.eomcs.pms.handler.MemberUpdateCommand;
import com.eomcs.pms.handler.ProjectAddCommand;
import com.eomcs.pms.handler.ProjectDeleteCommand;
import com.eomcs.pms.handler.ProjectDetailCommand;
import com.eomcs.pms.handler.ProjectListCommand;
import com.eomcs.pms.handler.ProjectUpdateCommand;
import com.eomcs.pms.handler.TaskAddCommand;
import com.eomcs.pms.handler.TaskDeleteCommand;
import com.eomcs.pms.handler.TaskDetailCommand;
import com.eomcs.pms.handler.TaskListCommand;
import com.eomcs.pms.handler.TaskUpdateCommand;
import com.eomcs.pms.handler.WhoamiCommand;

public class AppInitListener implements ApplicationContextListener {
  @Override
  public void contextInitialized(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)에 오신 걸 환영합니다!");

    // 시스템에서 사용할 객체를 준비한다.
    try {
      Connection con = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");

      // Mybatis 객체 준비
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
          Resources.getResourceAsStream("com/eomcs/pms/conf/mybatis-config.xml"));

      // DAO 구현체 생성
      BoardDao boardDao = new BoardDaoImpl(sqlSessionFactory);
      MemberDao memberDao = new MemberDaoImpl(sqlSessionFactory);
      ProjectDao projectDao = new ProjectDaoImpl(sqlSessionFactory);
      TaskDao taskDao = new TaskDaoImpl(con, sqlSessionFactory);

      // Command 구현체 생성 및 commandMap 객체 준비
      Map<String,Command> commandMap = new HashMap<>();

      commandMap.put("/board/add", new BoardAddCommand(boardDao, memberDao));
      commandMap.put("/board/list", new BoardListCommand(boardDao));
      commandMap.put("/board/detail", new BoardDetailCommand(boardDao));
      commandMap.put("/board/update", new BoardUpdateCommand(boardDao));
      commandMap.put("/board/delete", new BoardDeleteCommand(boardDao));

      commandMap.put("/member/add", new MemberAddCommand(memberDao));
      commandMap.put("/member/list", new MemberListCommand(memberDao));
      commandMap.put("/member/detail", new MemberDetailCommand(memberDao));
      commandMap.put("/member/update", new MemberUpdateCommand(memberDao));
      commandMap.put("/member/delete", new MemberDeleteCommand(memberDao));

      commandMap.put("/project/add", new ProjectAddCommand(projectDao, memberDao));
      commandMap.put("/project/list", new ProjectListCommand(projectDao, memberDao));
      commandMap.put("/project/detail", new ProjectDetailCommand(projectDao));
      commandMap.put("/project/update", new ProjectUpdateCommand(projectDao, memberDao));
      commandMap.put("/project/delete", new ProjectDeleteCommand(projectDao, taskDao));

      commandMap.put("/task/add", new TaskAddCommand(taskDao, projectDao, memberDao));
      commandMap.put("/task/list", new TaskListCommand(taskDao));
      commandMap.put("/task/detail", new TaskDetailCommand(taskDao));
      commandMap.put("/task/update", new TaskUpdateCommand(taskDao, projectDao, memberDao));
      commandMap.put("/task/delete", new TaskDeleteCommand(taskDao));

      commandMap.put("/hello", new HelloCommand());

      commandMap.put("/login", new LoginCommand(memberDao));
      commandMap.put("/whoami", new WhoamiCommand());
      commandMap.put("/logout", new LogoutCommand());

      context.put("commandMap", commandMap);

    } catch (Exception e) {
      System.out.println("시스템이 사용할 객체를 준비하는 중에 오류 발생");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
    System.out.println("프로젝트 관리 시스템(PMS)을 종료합니다!");

    try {
      Connection con = (Connection) context.get("con");
      con.close();
    } catch (Exception e) {
      // 커넥션을 닫다가 오류가 발생하더라도 무시한다.
    }
  }
}
