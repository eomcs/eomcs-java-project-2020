package com.eomcs.pms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
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
import com.eomcs.util.Prompt;

public class App01 {

  // main(), saveBoards(), loadBoards() 가 공유하는 필드 
  static List<Board> boardList = new ArrayList<>();
  static File boardFile = new File("./board.csv"); // 게시글을 저장할 파일 정보

  public static void main(String[] args) {

    // 파일에서 데이터 로딩
    loadBoards();

    Map<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddCommand(boardList));
    commandMap.put("/board/list", new BoardListCommand(boardList));
    commandMap.put("/board/detail", new BoardDetailCommand(boardList));
    commandMap.put("/board/update", new BoardUpdateCommand(boardList));
    commandMap.put("/board/delete", new BoardDeleteCommand(boardList));

    List<Member> memberList = new LinkedList<>();
    MemberListCommand memberListCommand = new MemberListCommand(memberList);
    commandMap.put("/member/add", new MemberAddCommand(memberList));
    commandMap.put("/member/list", memberListCommand);
    commandMap.put("/member/detail", new MemberDetailCommand(memberList));
    commandMap.put("/member/update", new MemberUpdateCommand(memberList));
    commandMap.put("/member/delete", new MemberDeleteCommand(memberList));

    List<Project> projectList = new LinkedList<>();
    commandMap.put("/project/add", new ProjectAddCommand(projectList, memberListCommand));
    commandMap.put("/project/list", new ProjectListCommand(projectList));
    commandMap.put("/project/detail", new ProjectDetailCommand(projectList));
    commandMap.put("/project/update", new ProjectUpdateCommand(projectList, memberListCommand));
    commandMap.put("/project/delete", new ProjectDeleteCommand(projectList));

    List<Task> taskList = new ArrayList<>();
    commandMap.put("/task/add", new TaskAddCommand(taskList, memberListCommand));
    commandMap.put("/task/list", new TaskListCommand(taskList));
    commandMap.put("/task/detail", new TaskDetailCommand(taskList));
    commandMap.put("/task/update", new TaskUpdateCommand(taskList, memberListCommand));
    commandMap.put("/task/delete", new TaskDeleteCommand(taskList));

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

    // 데이터를 파일에 저장
    saveBoards();
  }

  static void printCommandHistory(Iterator<String> iterator) {
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

  private static void saveBoards() {
    FileWriter out = null;

    try {
      // 파일로 데이터를 출력할 때 사용할 도구를 준비한다.
      out = new FileWriter(boardFile);
      int count = 0;

      for (Board board : boardList) {
        // 게시글 목록에서 게시글 데이터를 꺼내 CSV 형식의 문자열로 만든다.
        // => 번호, 제목, 내용, 작성자, 등록일, 조회수
        String line = String.format("%d,%s,%s,%s,%s,%d\n", 
            board.getNo(),
            board.getTitle(),
            board.getContent(),
            board.getWriter(),
            board.getRegisteredDate(),
            board.getViewCount());

        out.write(line);
        count++;
      }
      System.out.printf("총 %d 개의 게시글 데이터를 저장했습니다.\n", count);

    } catch (IOException e) {
      System.out.println("게시글 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
        // FileWriter를 닫을 때 발생하는 예외는 무시한다.
      }
    }
  }

  private static void loadBoards() {
    FileReader in = null;
    Scanner dataScan = null;

    try {
      // 파일을 읽을 때 사용할 도구를 준비한다.
      in = new FileReader(boardFile);

      // .csv 파일에서 한 줄 단위로 문자열을 읽는 기능이 필요한데,
      // FileReader에는 그런 기능이 없다.
      // 그래서 FileReader를 그대로 사용할 수 없고,
      // 이 객체에 다른 도구를 연결하여 사용할 것이다.
      //
      dataScan = new Scanner(in);
      int count = 0;

      while (true) {
        try {
          // 파일에서 한 줄을 읽는다.
          String line = dataScan.nextLine();

          // 한 줄을 콤마(,)로 나눈다.
          String[] data = line.split(",");

          // 한 줄에 들어 있던 데이터를 추출하여 Board 객체에 담는다.
          // // => 번호, 제목, 내용, 작성자, 등록일, 조회수
          Board board = new Board();
          board.setNo(Integer.parseInt(data[0]));
          board.setTitle(data[1]);
          board.setContent(data[2]);
          board.setWriter(data[3]);
          board.setRegisteredDate(Date.valueOf(data[4]));
          board.setViewCount(Integer.parseInt(data[5]));

          // 게시글 객체를 Command가 사용하는 목록에 저장한다.
          boardList.add(board);
          count++;

        } catch (Exception e) {
          break;
        }
      }
      System.out.printf("총 %d 개의 게시글 데이터를 로딩했습니다.\n", count);

    } catch (FileNotFoundException e) {
      System.out.println("게시글 파일 읽기 중 오류 발생! - " + e.getMessage());
      // 파일에서 데이터를 읽다가 오류가 발생하더라도
      // 시스템을 멈추지 않고 계속 실행하게 한다.
      // 이것이 예외처리를 하는 이유이다!!!
    } finally {
      // 자원이 서로 연결된 경우에는 다른 자원을 이용하는 객체부터 닫는다.
      try {
        dataScan.close();
      } catch (Exception e) {
        // Scanner 객체 닫다가 오류가 발생하더라도 무시한다.
      }
      try {
        in.close();
      } catch (Exception e) {
        // close() 실행하다가 오류가 발생한 경우 무시한다.
        // 왜? 닫다가 발생한 오류는 특별히 처리할 게 없다.
      }
    }
  }


}
