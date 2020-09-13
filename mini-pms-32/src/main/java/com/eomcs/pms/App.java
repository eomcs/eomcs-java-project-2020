package com.eomcs.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
  static File boardFile = new File("./board.data"); // 게시글을 저장할 파일 정보

  // main(), saveMembers(), loadMembers() 가 공유하는 필드 
  static List<Member> memberList = new LinkedList<>();
  static File memberFile = new File("./member.data"); // 회원을 저장할 파일 정보

  // main(), saveProjects(), loadProjects() 가 공유하는 필드 
  static List<Project> projectList = new LinkedList<>();
  static File projectFile = new File("./project.data"); // 프로젝트를 저장할 파일 정보

  // main(), saveTasks(), loadTasks() 가 공유하는 필드 
  static List<Task> taskList = new ArrayList<>();
  static File taskFile = new File("./task.data"); // 작업을 저장할 파일 정보

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
    FileOutputStream out = null;
    DataOutputStream out2 = null;

    try {
      out = new FileOutputStream(boardFile);
      out2 = new DataOutputStream(out);

      // 몇개의 데이터를 저장할 것인지 그 개수를 먼저 출력한다.
      // => 파일을 읽을 때 사용하기 위함이다.
      // => 해당 개수 만큼만 읽을 것이다.
      out2.writeInt(boardList.size());

      for (Board board : boardList) {
        out2.writeInt(board.getNo());
        out2.writeUTF(board.getTitle());
        out2.writeUTF(board.getContent());
        out2.writeUTF(board.getWriter());
        out2.writeUTF(board.getRegisteredDate().toString());
        out2.writeInt(board.getViewCount());
      }
      System.out.printf("총 %d 개의 게시글 데이터를 저장했습니다.\n", boardList.size());

    } catch (IOException e) {
      System.out.println("게시글 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out2.close();
      } catch (IOException e) {
      }
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadBoards() {
    FileInputStream in = null;
    DataInputStream in2 = null;

    try {
      in = new FileInputStream(boardFile);
      in2 = new DataInputStream(in);

      int size = in2.readInt();

      // 파일에 출력한 개수 만큼 객체 값을 읽어야 한다.
      for (int i = 0; i < size; i++) {
        Board board = new Board();

        // 파일에 출력한 순서대로 필드 값을 읽어야 한다.
        board.setNo(in2.readInt());
        board.setTitle(in2.readUTF());
        board.setContent(in2.readUTF());
        board.setWriter(in2.readUTF());
        board.setRegisteredDate(Date.valueOf(in2.readUTF()));
        board.setViewCount(in2.readInt());

        boardList.add(board);
      }
      System.out.printf("총 %d 개의 게시글 데이터를 로딩했습니다.\n", boardList.size());

    } catch (Exception e) {
      System.out.println("게시글 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in2.close();
      } catch (Exception e) {
      }
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  private static void saveMembers() {
    FileOutputStream out = null;
    DataOutputStream out2 = null;

    try {
      out = new FileOutputStream(memberFile);
      out2 = new DataOutputStream(out);

      // 몇개의 데이터를 저장할 것인지 그 개수를 먼저 출력한다.
      // => 파일을 읽을 때 사용하기 위함이다.
      // => 해당 개수 만큼만 읽을 것이다.
      out2.writeInt(memberList.size());

      for (Member member : memberList) {
        out2.writeInt(member.getNo());
        out2.writeUTF(member.getName());
        out2.writeUTF(member.getEmail());
        out2.writeUTF(member.getPassword());
        out2.writeUTF(member.getPhoto());
        out2.writeUTF(member.getTel());
        out2.writeUTF(member.getRegisteredDate().toString());
      }
      System.out.printf("총 %d 개의 회원 데이터를 저장했습니다.\n", memberList.size());

    } catch (IOException e) {
      System.out.println("회원 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out2.close();
      } catch (IOException e) {
      }
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadMembers() {
    FileInputStream in = null;
    DataInputStream in2 = null;

    try {
      in = new FileInputStream(memberFile);
      in2 = new DataInputStream(in);

      int size = in2.readInt();

      // 파일에 출력한 개수 만큼 객체 값을 읽어야 한다.
      for (int i = 0; i < size; i++) {
        Member member = new Member();

        // 파일에 출력한 순서대로 필드 값을 읽어야 한다.
        member.setNo(in2.readInt());
        member.setName(in2.readUTF());
        member.setEmail(in2.readUTF());
        member.setPassword(in2.readUTF());
        member.setPhoto(in2.readUTF());
        member.setTel(in2.readUTF());
        member.setRegisteredDate(Date.valueOf(in2.readUTF()));

        memberList.add(member);
      }
      System.out.printf("총 %d 개의 회원 데이터를 로딩했습니다.\n", memberList.size());

    } catch (Exception e) {
      System.out.println("회원 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in2.close();
      } catch (Exception e) {
      }
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  private static void saveProjects() {
    FileOutputStream out = null;
    DataOutputStream out2 = null;

    try {
      out = new FileOutputStream(projectFile);
      out2 = new DataOutputStream(out);

      // 몇개의 데이터를 저장할 것인지 그 개수를 먼저 출력한다.
      // => 파일을 읽을 때 사용하기 위함이다.
      // => 해당 개수 만큼만 읽을 것이다.
      out2.writeInt(projectList.size());

      for (Project project : projectList) {
        out2.writeInt(project.getNo());
        out2.writeUTF(project.getTitle());
        out2.writeUTF(project.getContent());
        out2.writeUTF(project.getStartDate().toString());
        out2.writeUTF(project.getEndDate().toString());
        out2.writeUTF(project.getOwner());
        out2.writeUTF(project.getMembers());
      }
      System.out.printf("총 %d 개의 프로젝트 데이터를 저장했습니다.\n", projectList.size());

    } catch (IOException e) {
      System.out.println("프로젝트 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out2.close();
      } catch (IOException e) {
      }
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadProjects() {
    FileInputStream in = null;
    DataInputStream in2 = null;

    try {
      in = new FileInputStream(projectFile);
      in2 = new DataInputStream(in);

      int size = in2.readInt();

      // 파일에 출력한 개수 만큼 객체 값을 읽어야 한다.
      for (int i = 0; i < size; i++) {
        Project project = new Project();

        // 파일에 출력한 순서대로 필드 값을 읽어야 한다.
        project.setNo(in2.readInt());
        project.setTitle(in2.readUTF());
        project.setContent(in2.readUTF());
        project.setStartDate(Date.valueOf(in2.readUTF()));
        project.setEndDate(Date.valueOf(in2.readUTF()));
        project.setOwner(in2.readUTF());
        project.setMembers(in2.readUTF());

        projectList.add(project);
      }
      System.out.printf("총 %d 개의 프로젝트 데이터를 로딩했습니다.\n", projectList.size());

    } catch (Exception e) {
      System.out.println("프로젝트 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in2.close();
      } catch (Exception e) {
      }
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  private static void saveTasks() {
    FileOutputStream out = null;
    DataOutputStream out2 = null;

    try {
      out = new FileOutputStream(taskFile);
      out2 = new DataOutputStream(out);

      // 몇개의 데이터를 저장할 것인지 그 개수를 먼저 출력한다.
      // => 파일을 읽을 때 사용하기 위함이다.
      // => 해당 개수 만큼만 읽을 것이다.
      out2.writeInt(taskList.size());

      for (Task task : taskList) {
        out2.writeInt(task.getNo());
        out2.writeUTF(task.getContent());
        out2.writeUTF(task.getDeadline().toString());
        out2.writeInt(task.getStatus());
        out2.writeUTF(task.getOwner());
      }
      System.out.printf("총 %d 개의 작업 데이터를 저장했습니다.\n", taskList.size());

    } catch (IOException e) {
      System.out.println("작업 데이터의 파일 쓰기 중 오류 발생! - " + e.getMessage());

    } finally {
      try {
        out2.close();
      } catch (IOException e) {
      }
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  private static void loadTasks() {
    FileInputStream in = null;
    DataInputStream in2 = null;

    try {
      in = new FileInputStream(taskFile);
      in2 = new DataInputStream(in);

      int size = in2.readInt();

      // 파일에 출력한 개수 만큼 객체 값을 읽어야 한다.
      for (int i = 0; i < size; i++) {
        Task task = new Task();

        // 파일에 출력한 순서대로 필드 값을 읽어야 한다.
        task.setNo(in2.readInt());
        task.setContent(in2.readUTF());
        task.setDeadline(Date.valueOf(in2.readUTF()));
        task.setStatus(in2.readInt());
        task.setOwner(in2.readUTF());

        taskList.add(task);
      }
      System.out.printf("총 %d 개의 작업 데이터를 로딩했습니다.\n", taskList.size());

    } catch (Exception e) {
      System.out.println("작업 파일 읽기 중 오류 발생! - " + e.getMessage());
    } finally {
      try {
        in2.close();
      } catch (Exception e) {
      }
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }
}
