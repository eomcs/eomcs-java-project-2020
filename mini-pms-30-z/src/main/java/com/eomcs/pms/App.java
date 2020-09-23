package com.eomcs.pms;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import com.eomcs.util.CsvObject;
import com.eomcs.util.CsvObjectFactory;
import com.eomcs.util.Prompt;

public class App {

  // main(), saveBoards(), loadBoards() 가 공유하는 필드 
  static List<Board> boardList = new ArrayList<>();
  static File boardFile = new File("./board.csv"); // 게시글을 저장할 파일 정보

  // main(), saveMembers(), loadMembers() 가 공유하는 필드 
  static List<Member> memberList = new LinkedList<>();
  static File memberFile = new File("./member.csv"); // 회원을 저장할 파일 정보

  // main(), saveProjects(), loadProjects() 가 공유하는 필드 
  static List<Project> projectList = new LinkedList<>();
  static File projectFile = new File("./project.csv"); // 프로젝트를 저장할 파일 정보

  // main(), saveTasks(), loadTasks() 가 공유하는 필드 
  static List<Task> taskList = new ArrayList<>();
  static File taskFile = new File("./task.csv"); // 작업을 저장할 파일 정보

  public static void main(String[] args) {

    // 파일에서 데이터 로딩
    loadObjects(boardFile, boardList, Board::new);
    loadObjects(memberFile, memberList, Member::new);
    loadObjects(projectFile, projectList, Project::new);
    loadObjects(taskFile, taskList, Task::new);

    Map<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddCommand(boardList));
    commandMap.put("/board/list", new BoardListCommand(boardList));
    commandMap.put("/board/detail", new BoardDetailCommand(boardList));
    commandMap.put("/board/update", new BoardUpdateCommand(boardList));
    commandMap.put("/board/delete", new BoardDeleteCommand(boardList));

    MemberListCommand memberListCommand = new MemberListCommand(memberList);
    commandMap.put("/member/add", new MemberAddCommand(memberList));
    commandMap.put("/member/list", memberListCommand);
    commandMap.put("/member/detail", new MemberDetailCommand(memberList));
    commandMap.put("/member/update", new MemberUpdateCommand(memberList));
    commandMap.put("/member/delete", new MemberDeleteCommand(memberList));

    commandMap.put("/project/add", new ProjectAddCommand(projectList, memberListCommand));
    commandMap.put("/project/list", new ProjectListCommand(projectList));
    commandMap.put("/project/detail", new ProjectDetailCommand(projectList));
    commandMap.put("/project/update", new ProjectUpdateCommand(projectList, memberListCommand));
    commandMap.put("/project/delete", new ProjectDeleteCommand(projectList));

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
    saveObjects(boardFile, boardList);
    saveObjects(memberFile, memberList);
    saveObjects(projectFile, projectList);
    saveObjects(taskFile, taskList);
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

  //CsvObject 구현체 목록을 파일에 저장하는 메서드이다.
  // 이전에 각 도메인 별로 만들었던 메서드를 다음과 같이 한 메서드로 통합한다.
  private static <T extends CsvObject> void saveObjects(File file, List<T> list) {
    FileWriter out = null;

    try {
      out = new FileWriter(file);
      int count = 0;

      for (CsvObject csvData : list) {
        out.write(csvData.toCsvString());
        count++;
      }
      System.out.printf("%s => 총 %d 개의 데이터를 저장했습니다.\n", 
          file.getName(), 
          count);

    } catch (IOException e) {
      System.out.printf("%s => 데이터의 파일 쓰기 중 오류 발생! - %s\n", 
          file.getName(), 
          e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }


  private static <T> void loadObjects(File file, List<T> list, CsvObjectFactory<T> factory) {
    FileReader in = null;
    Scanner dataScan = null;

    try {
      in = new FileReader(file);
      dataScan = new Scanner(in);
      int count = 0;

      while (true) {
        try {
          list.add(factory.create(dataScan.nextLine()));
          count++;
        } catch (Exception e) {
          break;
        }
      }
      System.out.printf("%s => 총 %d 개의 데이터를 로딩했습니다.\n", file.getName(), count);

    } catch (Exception e) {
      System.out.printf("%s => 파일 읽기 중 오류 발생! - %s\n", file.getName(), e.getMessage());
    } finally {
      try {
        dataScan.close();
      } catch (Exception e) {
      }
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }
}
