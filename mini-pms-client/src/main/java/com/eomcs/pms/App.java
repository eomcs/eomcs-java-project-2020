package com.eomcs.pms;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.dao.mariadb.BoardDaoImpl;
import com.eomcs.pms.dao.mariadb.MemberDaoImpl;
import com.eomcs.pms.dao.mariadb.ProjectDaoImpl;
import com.eomcs.pms.dao.mariadb.TaskDaoImpl;
import com.eomcs.pms.filter.CommandFilterManager;
import com.eomcs.pms.filter.DefaultCommandFilter;
import com.eomcs.pms.filter.FilterChain;
import com.eomcs.pms.filter.LogCommandFilter;
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
import com.eomcs.pms.handler.Request;
import com.eomcs.pms.handler.TaskAddCommand;
import com.eomcs.pms.handler.TaskDeleteCommand;
import com.eomcs.pms.handler.TaskDetailCommand;
import com.eomcs.pms.handler.TaskListCommand;
import com.eomcs.pms.handler.TaskUpdateCommand;
import com.eomcs.pms.handler.WhoamiCommand;
import com.eomcs.pms.listener.AppInitListener;
import com.eomcs.util.Prompt;

public class App {

  // 옵저버와 공유할 맵 객체
  Map<String,Object> context = new Hashtable<>();

  // 옵저버를 보관할 컬렉션 객체
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 옵저버를 등록하는 메서드
  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  // 옵저버를 제거하는 메서드
  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  // service() 실행 전에 옵저버에게 통지한다.
  private void notifyApplicationContextListenerOnServiceStarted() {
    for (ApplicationContextListener listener : listeners) {
      // 곧 서비스를 시작할테니 준비하라고,
      // 서비스 시작에 관심있는 각 옵저버에게 통지한다.
      // => 옵저버에게 맵 객체를 넘겨준다.
      // => 옵저버는 작업 결과를 파라미터로 넘겨준 맵 객체에 담아 줄 것이다.
      listener.contextInitialized(context);
    }
  }

  // service() 실행 후에 옵저버에게 통지한다.
  private void notifyApplicationContextListenerOnServiceStopped() {
    for (ApplicationContextListener listener : listeners) {
      // 서비스가 종료되었으니 마무리 작업하라고,
      // 마무리 작업에 관심있는 각 옵저버에게 통지한다.
      // => 옵저버에게 맵 객체를 넘겨준다.
      // => 옵저버는 작업 결과를 파라미터로 넘겨준 맵 객체에 담아 줄 것이다.
      listener.contextDestroyed(context);
    }
  }


  public static void main(String[] args) throws Exception {
    App app = new App();

    // 옵저버 등록
    app.addApplicationContextListener(new AppInitListener());

    app.service();
  }

  public void service() throws Exception {

    notifyApplicationContextListenerOnServiceStarted();

    Map<String,Command> commandMap = new HashMap<>();

    // AppInitListener 가 준비한 Connection 객체를 꺼낸다.
    Connection con  = (Connection) context.get("con");

    BoardDao boardDao = new BoardDaoImpl(con);
    MemberDao memberDao = new MemberDaoImpl(con);
    ProjectDao projectDao = new ProjectDaoImpl(con);
    TaskDao taskDao = new TaskDaoImpl(con);

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
    commandMap.put("/project/list", new ProjectListCommand(projectDao));
    commandMap.put("/project/detail", new ProjectDetailCommand(projectDao));
    commandMap.put("/project/update", new ProjectUpdateCommand(projectDao, memberDao));
    commandMap.put("/project/delete", new ProjectDeleteCommand(projectDao));

    commandMap.put("/task/add", new TaskAddCommand(taskDao, projectDao, memberDao));
    commandMap.put("/task/list", new TaskListCommand(taskDao));
    commandMap.put("/task/detail", new TaskDetailCommand(taskDao));
    commandMap.put("/task/update", new TaskUpdateCommand(taskDao, projectDao, memberDao));
    commandMap.put("/task/delete", new TaskDeleteCommand(taskDao));

    commandMap.put("/hello", new HelloCommand());

    commandMap.put("/login", new LoginCommand(memberDao));
    commandMap.put("/whoami", new WhoamiCommand());
    commandMap.put("/logout", new LogoutCommand());

    // commandMap 객체를 context 맵에 보관한다.
    // => 필터나 커맨드 객체가 사용할 수 있기 때문이다.
    context.put("commandMap", commandMap);

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

    Deque<String> commandStack = new ArrayDeque<>();
    Queue<String> commandQueue = new LinkedList<>();

    loop:
      while (true) {
        String inputStr = Prompt.inputString("명령> ");

        if (inputStr.length() == 0) {
          continue;
        }

        commandStack.push(inputStr);
        commandQueue.offer(inputStr);

        switch (inputStr) {
          case "history": printCommandHistory(commandStack.iterator()); break;
          case "history2": printCommandHistory(commandQueue.iterator()); break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            // 커맨드나 필터가 사용할 객체를 준비한다.
            Request request = new Request(inputStr, context);

            // 필터들의 체인을 실행한다.
            if (filterChain != null) {
              filterChain.doFilter(request);
            }
        }
        System.out.println();
      }
    Prompt.close();

    // 필터들을 마무리시킨다.
    filterManager.destroy();

    notifyApplicationContextListenerOnServiceStopped();
  }

  void printCommandHistory(Iterator<String> iterator) {
    try {
      int count = 0;
      while (iterator.hasNext()) {
        System.out.println(iterator.next());
        count++;

        if ((count % 5) == 0 && Prompt.inputString(":").equalsIgnoreCase("q")) {
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("history 명령 처리 중 오류 발생!");
    }
  }
}
