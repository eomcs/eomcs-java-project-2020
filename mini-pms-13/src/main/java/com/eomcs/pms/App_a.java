package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App_a {

  public static void main(String[] args) {

    BoardHandler boardHandler = new BoardHandler();
    BoardHandler boardHandler2 = new BoardHandler();
    BoardHandler boardHandler3 = new BoardHandler();
    BoardHandler boardHandler4 = new BoardHandler();
    BoardHandler boardHandler5 = new BoardHandler();
    BoardHandler boardHandler6 = new BoardHandler();

    MemberHandler memberHandler = new MemberHandler();

    ProjectHandler projectHandler = new ProjectHandler();

    // ProjectHandler의 의존 객체 주입을 막는다.
    //projectHandler.memberHandler = memberHandler;

    TaskHandler taskHandler = new TaskHandler();

    // TaskHandler의 의존 객체 주입을 막는다.
    //taskHandler.memberHandler = memberHandler;

    // 인스턴스를 사용하는데 필요한 의존 객체 주입을 누락하더라도
    // 컴파일러는 알 수가 없다.
    // 실행할 때 비로서 의존 객체가 누락된 문제가 발생할 것이다.

    loop:
      while (true) {
        String command = Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add": memberHandler.add(); break;
          case "/member/list": memberHandler.list(); break;
          case "/project/add": projectHandler.add(); break;
          case "/project/list": projectHandler.list(); break;
          case "/task/add": taskHandler.add(); break;
          case "/task/list": taskHandler.list(); break;
          case "/board/add": boardHandler.add(); break;
          case "/board/list": boardHandler.list(); break;
          case "/board2/add": boardHandler2.add(); break;
          case "/board2/list": boardHandler2.list(); break;
          case "/board3/add": boardHandler3.add(); break;
          case "/board3/list": boardHandler3.list(); break;
          case "/board4/add": boardHandler4.add(); break;
          case "/board4/list": boardHandler4.list(); break;
          case "/board5/add": boardHandler5.add(); break;
          case "/board5/list": boardHandler5.list(); break;
          case "/board6/add": boardHandler6.add(); break;
          case "/board6/list": boardHandler6.list(); break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    Prompt.close();
  }
}
