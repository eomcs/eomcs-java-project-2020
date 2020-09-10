package com.eomcs.pms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    List<Board> boardList = new ArrayList<>();
    BoardHandler boardHandler = new BoardHandler(boardList);

    List<Member> memberList = new LinkedList<>();
    MemberHandler memberHandler = new MemberHandler(memberList);

    List<Project> projectList = new LinkedList<>();
    ProjectHandler projectHandler = new ProjectHandler(projectList, memberHandler);

    List<Task> taskList = new ArrayList<>();
    TaskHandler taskHandler = new TaskHandler(taskList, memberHandler);

    // 자바에서는 stack 알고리즘(LIFO)에 대한 인터페이스로 Deque 를 제공한다.
    Deque<String> commandStack = new ArrayDeque<>();

    // 자바에서 제공하는 LinkedList 클래스는 Queue 구현체이기도 하다.
    Queue<String> commandQueue = new LinkedList<>();

    loop:
      while (true) {
        String command = Prompt.inputString("명령> ");

        // 사용자가 입력한 명령을 보관한다.
        commandStack.push(command);
        commandQueue.offer(command);

        switch (command) {
          case "/member/add": memberHandler.add(); break;
          case "/member/list": memberHandler.list(); break;
          case "/member/detail": memberHandler.detail(); break;
          case "/member/update": memberHandler.update(); break;
          case "/member/delete": memberHandler.delete(); break;
          case "/project/add": projectHandler.add(); break;
          case "/project/list": projectHandler.list(); break;
          case "/project/detail": projectHandler.detail(); break;
          case "/project/update": projectHandler.update(); break;
          case "/project/delete": projectHandler.delete(); break;
          case "/task/add": taskHandler.add(); break;
          case "/task/list": taskHandler.list(); break;
          case "/task/detail": taskHandler.detail(); break;
          case "/task/update": taskHandler.update(); break;
          case "/task/delete": taskHandler.delete(); break;
          case "/board/add": boardHandler.add(); break;
          case "/board/list": boardHandler.list(); break;
          case "/board/detail": boardHandler.detail(); break;
          case "/board/update": boardHandler.update(); break;
          case "/board/delete": boardHandler.delete(); break;

          // Iterator 패턴을 이용하면,
          // 자료 구조와 상관없이 일관된 방법으로 목록의 값을 조회할 수 있다.
          case "history": printCommandHistory(commandStack.iterator()); break;
          case "history2": printCommandHistory(commandQueue.iterator()); break;

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

  static void printCommandHistory(Iterator<String> iterator) {
    try {
      int count = 0;
      while (iterator.hasNext()) {
        System.out.println(iterator.next());
        count++;

        // 5개 출력할 때 마다 계속 출력할지 묻는다.
        if ((count % 5) == 0 && Prompt.inputString(":").equalsIgnoreCase("q")) {
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("history 명령 처리 중 오류 발생!");
    }
  }
}
