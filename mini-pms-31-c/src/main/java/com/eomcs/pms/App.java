package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    loadBoards();
    loadMembers();
    loadProjects();
    loadTasks();

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
    saveBoards();
    saveMembers();
    saveProjects();
    saveTasks();
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
    BufferedWriter out = null;

    try {
      out = new BufferedWriter(new FileWriter(boardFile));

      for (Board board : boardList) {
        out.write(board.toCsvString());
        out.write("\n");
      }

      out.flush();

      System.out.printf("총 %d 개의 게시글 데이터를 저장했습니다.\n", boardList.size());

    } catch (IOException e) {
      System.out.println("게시글 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadBoards() {
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader(boardFile));

      while (true) {
        String record = in.readLine();
        if (record == null) {
          break;
        }
        boardList.add(Board.valueOfCsv(record));
      }
      System.out.printf("총 %d 개의 게시글 데이터를 로딩했습니다.\n", boardList.size());

    } catch (Exception e) {
      System.out.println("게시글 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }


  private static void saveMembers() {
    BufferedWriter out = null;

    try {
      out = new BufferedWriter(new FileWriter(memberFile));

      for (Member member : memberList) {
        out.write(member.toCsvString());
        out.write("\n");
      }

      out.flush();

      System.out.printf("총 %d 개의 회원 데이터를 저장했습니다.\n", memberList.size());

    } catch (IOException e) {
      System.out.println("회원 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadMembers() {
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader(memberFile));

      while (true) {
        String record = in.readLine();
        if (record == null) {
          break;
        }
        memberList.add(Member.valueOfCsv(record));
      }
      System.out.printf("총 %d 개의 회원 데이터를 로딩했습니다.\n", memberList.size());

    } catch (Exception e) {
      System.out.println("회원 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  private static void saveProjects() {
    BufferedWriter out = null;

    try {
      out = new BufferedWriter(new FileWriter(projectFile));

      for (Project project : projectList) {
        out.write(project.toCsvString());
        out.write("\n");
      }

      out.flush();

      System.out.printf("총 %d 개의 프로젝트 데이터를 저장했습니다.\n", projectList.size());

    } catch (IOException e) {
      System.out.println("프로젝트 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadProjects() {
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader(projectFile));

      while (true) {
        String record = in.readLine();
        if (record == null) {
          break;
        }
        projectList.add(Project.valueOfCsv(record));
      }
      System.out.printf("총 %d 개의 프로젝트 데이터를 로딩했습니다.\n", projectList.size());

    } catch (Exception e) {
      System.out.println("프로젝트 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  private static void saveTasks() {
    BufferedWriter out = null;

    try {
      out = new BufferedWriter(new FileWriter(taskFile));

      for (Task task : taskList) {
        out.write(task.toCsvString());
        out.write("\n");
      }

      out.flush();

      System.out.printf("총 %d 개의 작업 데이터를 저장했습니다.\n", taskList.size());

    } catch (IOException e) {
      System.out.println("작업 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadTasks() {
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader(taskFile));

      while (true) {
        String record = in.readLine();
        if (record == null) {
          break;
        }
        taskList.add(Task.valueOfCsv(record));
      }
      System.out.printf("총 %d 개의 작업 데이터를 로딩했습니다.\n", taskList.size());

    } catch (Exception e) {
      System.out.println("작업 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }
}
