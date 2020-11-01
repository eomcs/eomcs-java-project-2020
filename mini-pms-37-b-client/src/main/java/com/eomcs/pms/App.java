package com.eomcs.pms;

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
import com.eomcs.pms.handler.BoardAddCommand;
import com.eomcs.pms.handler.BoardDeleteCommand;
import com.eomcs.pms.handler.BoardDetailCommand;
import com.eomcs.pms.handler.BoardListCommand;
import com.eomcs.pms.handler.BoardUpdateCommand;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloCommand;
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

    commandMap.put("/board/add", new BoardAddCommand());
    commandMap.put("/board/list", new BoardListCommand());
    commandMap.put("/board/detail", new BoardDetailCommand());
    commandMap.put("/board/update", new BoardUpdateCommand());
    commandMap.put("/board/delete", new BoardDeleteCommand());

    MemberListCommand memberListCommand = new MemberListCommand();
    commandMap.put("/member/add", new MemberAddCommand());
    commandMap.put("/member/list", memberListCommand);
    commandMap.put("/member/detail", new MemberDetailCommand());
    commandMap.put("/member/update", new MemberUpdateCommand());
    commandMap.put("/member/delete", new MemberDeleteCommand());

    commandMap.put("/project/add", new ProjectAddCommand(memberListCommand));
    commandMap.put("/project/list", new ProjectListCommand());
    commandMap.put("/project/detail", new ProjectDetailCommand());
    commandMap.put("/project/update", new ProjectUpdateCommand(memberListCommand));
    commandMap.put("/project/delete", new ProjectDeleteCommand());

    commandMap.put("/task/add", new TaskAddCommand(memberListCommand));
    commandMap.put("/task/list", new TaskListCommand());
    commandMap.put("/task/detail", new TaskDetailCommand());
    commandMap.put("/task/update", new TaskUpdateCommand(memberListCommand));
    commandMap.put("/task/delete", new TaskDeleteCommand());

    commandMap.put("/hello", new HelloCommand());

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
            Command command = commandMap.get(inputStr);
            if (command != null) {
              try {
                // 실행 중 오류가 발생할 수 있는 코드는 try 블록 안에 둔다.
                command.execute();
              } catch (Exception e) {
                // 오류가 발생하면 그 정보를 갖고 있는 객체의 클래스 이름을 출력한다.
                System.out.println("--------------------------------------------------------------");
                System.out.printf("명령어 실행 중 오류 발생: %s\n", e);
                System.out.println("--------------------------------------------------------------");
              }
            } else {
              System.out.println("실행할 수 없는 명령입니다.");
            }
        }
        System.out.println();
      }

    Prompt.close();

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
