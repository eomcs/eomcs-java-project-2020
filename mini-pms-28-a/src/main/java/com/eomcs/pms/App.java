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
import com.eomcs.pms.handler.BoardAddCommand;
import com.eomcs.pms.handler.BoardDeleteCommand;
import com.eomcs.pms.handler.BoardDetailCommand;
import com.eomcs.pms.handler.BoardListCommand;
import com.eomcs.pms.handler.BoardUpdateCommand;
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
import com.eomcs.util.Prompt;

public class App {

  public static void main(String[] args) {

    List<Board> boardList = new ArrayList<>();
    BoardAddCommand boardAddCommand = new BoardAddCommand(boardList);
    BoardListCommand boardListCommand = new BoardListCommand(boardList);
    BoardDetailCommand boardDetailCommand = new BoardDetailCommand(boardList);
    BoardUpdateCommand boardUpdateCommand = new BoardUpdateCommand(boardList);
    BoardDeleteCommand boardDeleteCommand = new BoardDeleteCommand(boardList);

    List<Member> memberList = new LinkedList<>();
    MemberAddCommand memberAddCommand = new MemberAddCommand(memberList);
    MemberListCommand memberListCommand = new MemberListCommand(memberList);
    MemberDetailCommand memberDetailCommand = new MemberDetailCommand(memberList);
    MemberUpdateCommand memberUpdateCommand = new MemberUpdateCommand(memberList);
    MemberDeleteCommand memberDeleteCommand = new MemberDeleteCommand(memberList);

    List<Project> projectList = new LinkedList<>();
    ProjectAddCommand projectAddCommand = new ProjectAddCommand(projectList, memberListCommand);
    ProjectListCommand projectListCommand = new ProjectListCommand(projectList);
    ProjectDetailCommand projectDetailCommand = new ProjectDetailCommand(projectList);
    ProjectUpdateCommand projectUpdateCommand = new ProjectUpdateCommand(projectList, memberListCommand);
    ProjectDeleteCommand projectDeleteCommand = new ProjectDeleteCommand(projectList);

    List<Task> taskList = new ArrayList<>();
    TaskAddCommand taskAddCommand = new TaskAddCommand(taskList, memberListCommand);
    TaskListCommand taskListCommand = new TaskListCommand(taskList);
    TaskDetailCommand taskDetailCommand = new TaskDetailCommand(taskList);
    TaskUpdateCommand taskUpdateCommand = new TaskUpdateCommand(taskList, memberListCommand);
    TaskDeleteCommand taskDeleteCommand = new TaskDeleteCommand(taskList);

    HelloCommand helloCommand = new HelloCommand();

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
          case "/member/add": memberAddCommand.execute(); break;
          case "/member/list": memberListCommand.execute(); break;
          case "/member/detail": memberDetailCommand.execute(); break;
          case "/member/update": memberUpdateCommand.execute(); break;
          case "/member/delete": memberDeleteCommand.execute(); break;
          case "/project/add": projectAddCommand.execute(); break;
          case "/project/list": projectListCommand.execute(); break;
          case "/project/detail": projectDetailCommand.execute(); break;
          case "/project/update": projectUpdateCommand.execute(); break;
          case "/project/delete": projectDeleteCommand.execute(); break;
          case "/task/add": taskAddCommand.execute(); break;
          case "/task/list": taskListCommand.execute(); break;
          case "/task/detail": taskDetailCommand.execute(); break;
          case "/task/update": taskUpdateCommand.execute(); break;
          case "/task/delete": taskDeleteCommand.execute(); break;
          case "/board/add": boardAddCommand.execute(); break;
          case "/board/list": boardListCommand.execute(); break;
          case "/board/detail": boardDetailCommand.execute(); break;
          case "/board/update": boardUpdateCommand.execute(); break;
          case "/board/delete": boardDeleteCommand.execute(); break;
          case "/hello": helloCommand.execute(); break;

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
