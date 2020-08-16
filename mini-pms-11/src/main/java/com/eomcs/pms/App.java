package com.eomcs.pms;

import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.BoardHandler2;
import com.eomcs.pms.handler.BoardHandler3;
import com.eomcs.pms.handler.BoardHandler4;
import com.eomcs.pms.handler.BoardHandler5;
import com.eomcs.pms.handler.BoardHandler6;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

// 1) `/board/add` 명령을 처리한다.
// 2) `/board/list` 명령을 처리한다.
// 3) 새 게시판을 추가한다.
// 4) 새 게시판을 4개 더 추가한다.
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
          case "/board/add": BoardHandler.add(); break;
          case "/board/list": BoardHandler.list(); break;
          case "/board2/add": BoardHandler2.add(); break;
          case "/board2/list": BoardHandler2.list(); break;
          case "/board3/add": BoardHandler3.add(); break;
          case "/board3/list": BoardHandler3.list(); break;
          case "/board4/add": BoardHandler4.add(); break;
          case "/board4/list": BoardHandler4.list(); break;
          case "/board5/add": BoardHandler5.add(); break;
          case "/board5/list": BoardHandler5.list(); break;
          case "/board6/add": BoardHandler6.add(); break;
          case "/board6/list": BoardHandler6.list(); break;
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
