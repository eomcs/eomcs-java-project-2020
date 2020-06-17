package com.eomcs.pms;

import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    loop:
      while (true) {
        String command = Prompt.inputString("명령> ");

        switch (command) {
          case "/member/add": MemberHandler.add(); break;
          case "/member/list": MemberHandler.list(); break;
          case "/project/add": ProjectHandler.add(); break;
          case "/project/list": ProjectHandler.list(); break;
          case "/task/add": TaskHandler.add(); break;
          case "/task/list": TaskHandler.list(); break;
          case "quit":
          case "exit":
            System.out.println("안녕!");
            break loop;
          default:
            System.out.println("실행할 수 없는 명령입니다.");
        }
        System.out.println();
      }

  Prompt.close();
  }
}
