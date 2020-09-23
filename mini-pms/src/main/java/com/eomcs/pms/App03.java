package com.eomcs.pms;

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

public class App03 {

  // main(), saveBoards(), loadBoards() 가 공유하는 필드 
  static List<Board> boardList = new ArrayList<>();
  static File boardFile = new File("./board.data"); // 게시글을 저장할 파일 정보

  // main(), saveMembers(), loadMembers() 가 공유하는 필드 
  static List<Member> memberList = new LinkedList<>();
  static File memberFile = new File("./member.data"); // 회원을 저장할 파일 정보

  // main(), saveProjects(), loadProjects() 가 공유하는 필드 
  static List<Project> projectList = new LinkedList<>();
  static File projectFile = new File("./project.data"); // 프로젝트를 저장할 파일 정보

  public static void main(String[] args) {

    // 파일에서 데이터 로딩
    loadBoards();
    loadMembers();
    loadProjects();

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
    saveMembers();
    saveProjects();
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

    try {
      // 파일로 데이터를 출력할 때 사용할 도구를 준비한다.
      out = new FileOutputStream(boardFile);

      // 데이터의 개수를 먼저 출력한다.(4바이트)
      out.write(boardList.size() >> 24);
      out.write(boardList.size() >> 16);
      out.write(boardList.size() >> 8);
      out.write(boardList.size());

      for (Board board : boardList) {
        // 게시글 목록에서 게시글 데이터를 꺼내 바이너리 형식으로 출력한다.
        // => 게시글 번호 출력 (4바이트)
        out.write(board.getNo() >> 24);
        out.write(board.getNo() >> 16);
        out.write(board.getNo() >> 8);
        out.write(board.getNo());

        // => 게시글 제목 출력 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        byte[] bytes = board.getTitle().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 게시글 내용 출력
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = board.getContent().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 게시글 작성자 출력
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = board.getWriter().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 게시글 등록일 출력 (10바이트)
        bytes = board.getRegisteredDate().toString().getBytes("UTF-8");
        out.write(bytes);

        // => 게시글 조회수 출력
        out.write(board.getViewCount() >> 24);
        out.write(board.getViewCount() >> 16);
        out.write(board.getViewCount() >> 8);
        out.write(board.getViewCount());
      }
      System.out.printf("총 %d 개의 게시글 데이터를 저장했습니다.\n", boardList.size());

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
    FileInputStream in = null;

    try {
      // 파일을 읽을 때 사용할 도구를 준비한다.
      in = new FileInputStream(boardFile);

      // 데이터의 개수를 먼저 읽는다. (4바이트)
      int size = in.read() << 24;
      size += in.read() << 16;
      size += in.read() << 8;
      size += in.read();

      for (int i = 0; i < size; i++) {
        Board board = new Board();

        // 출력 형식에 맞춰서 파일에서 데이터를 읽는다.
        // => 게시글 번호 읽기
        int value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        board.setNo(value);

        // 문자열을 읽을 바이트 배열을 준비한다.
        byte[] bytes = new byte[30000];

        // => 게시글 제목 읽기
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        board.setTitle(new String(bytes, 0, len, "UTF-8"));

        // => 게시글 내용 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        board.setContent(new String(bytes, 0, len, "UTF-8"));

        // => 게시글 작성자 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        board.setWriter(new String(bytes, 0, len, "UTF-8"));

        // => 게시글 등록일 읽기
        in.read(bytes, 0, 10);
        board.setRegisteredDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        // => 게시글 조회수 읽기
        value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        board.setViewCount(value);

        // 게시글 객체를 Command가 사용하는 목록에 저장한다.
        boardList.add(board);
      }
      System.out.printf("총 %d 개의 게시글 데이터를 로딩했습니다.\n", boardList.size());

    } catch (Exception e) {
      System.out.println("게시글 파일 읽기 중 오류 발생! - " + e.getMessage());
      // 파일에서 데이터를 읽다가 오류가 발생하더라도
      // 시스템을 멈추지 않고 계속 실행하게 한다.
      // 이것이 예외처리를 하는 이유이다!!!
    } finally {
      try {
        in.close();
      } catch (Exception e) {
        // close() 실행하다가 오류가 발생한 경우 무시한다.
        // 왜? 닫다가 발생한 오류는 특별히 처리할 게 없다.
      }
    }
  }


  private static void saveMembers() {
    FileOutputStream out = null;

    try {
      out = new FileOutputStream(memberFile);

      // 데이터의 개수를 먼저 출력한다.(4바이트)
      out.write(memberList.size() >> 24);
      out.write(memberList.size() >> 16);
      out.write(memberList.size() >> 8);
      out.write(memberList.size());

      for (Member member : memberList) {
        // 회원 목록에서 회원 데이터를 꺼내 바이너리 형식으로 출력한다.
        // => 회원 번호 출력 (4바이트)
        out.write(member.getNo() >> 24);
        out.write(member.getNo() >> 16);
        out.write(member.getNo() >> 8);
        out.write(member.getNo());

        // => 회원 이름 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        byte[] bytes = member.getName().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 이메일 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getEmail().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 암호 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getPassword().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 사진 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getPhoto().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 전화 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = member.getTel().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 회원 등록일(10바이트)
        bytes = member.getRegisteredDate().toString().getBytes("UTF-8");
        out.write(bytes);
      }
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
    FileInputStream in = null;

    try {
      in = new FileInputStream(memberFile);

      // 데이터의 개수를 먼저 읽는다. (4바이트)
      int size = in.read() << 24;
      size += in.read() << 16;
      size += in.read() << 8;
      size += in.read();

      for (int i = 0; i < size; i++) {
        // 데이터를 담을 객체 준비
        Member member = new Member();

        // 출력 형식에 맞춰서 파일에서 데이터를 읽는다.
        // => 회원 번호 읽기
        int value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        member.setNo(value);

        // 문자열을 읽을 바이트 배열을 준비한다.
        byte[] bytes = new byte[30000];

        // => 회원 이름 읽기
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setName(new String(bytes, 0, len, "UTF-8"));

        // => 회원 이메일 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setEmail(new String(bytes, 0, len, "UTF-8"));

        // => 회원 암호 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setPassword(new String(bytes, 0, len, "UTF-8"));

        // => 회원 사진 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setPhoto(new String(bytes, 0, len, "UTF-8"));

        // => 회원 전화 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        member.setTel(new String(bytes, 0, len, "UTF-8"));

        // => 회원 등록일 읽기
        in.read(bytes, 0, 10);
        member.setRegisteredDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        memberList.add(member);
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
    FileOutputStream out = null;

    try {
      out = new FileOutputStream(projectFile);

      // 데이터의 개수를 먼저 출력한다.(4바이트)
      out.write(projectList.size() >> 24);
      out.write(projectList.size() >> 16);
      out.write(projectList.size() >> 8);
      out.write(projectList.size());

      for (Project project : projectList) {
        // 프로젝트 목록에서 프로젝트 데이터를 꺼내 바이너리 형식으로 출력한다.
        // => 프로젝트 번호 출력 (4바이트)
        out.write(project.getNo() >> 24);
        out.write(project.getNo() >> 16);
        out.write(project.getNo() >> 8);
        out.write(project.getNo());

        // => 프로젝트 제목 
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        byte[] bytes = project.getTitle().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 내용
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getContent().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 시작일(10바이트)
        bytes = project.getStartDate().toString().getBytes("UTF-8");
        out.write(bytes);

        // => 프로젝트 종료일(10바이트) 
        bytes = project.getEndDate().toString().getBytes("UTF-8");
        out.write(bytes);

        // => 프로젝트 소유주
        //    문자열의 바이트 길이(2바이트) + 문자열의 바이트 배열
        bytes = project.getOwner().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        // => 프로젝트 멤버들
        bytes = project.getMembers().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);
      }
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
    FileInputStream in = null;

    try {
      in = new FileInputStream(projectFile);

      // 데이터의 개수를 먼저 읽는다. (4바이트)
      int size = in.read() << 24;
      size += in.read() << 16;
      size += in.read() << 8;
      size += in.read();

      for (int i = 0; i < size; i++) {
        // 데이터를 담을 객체 준비
        Project project = new Project();

        // 출력 형식에 맞춰서 파일에서 데이터를 읽는다.
        // => 프로젝트 번호 읽기
        int value = in.read() << 24;
        value += in.read() << 16;
        value += in.read() << 8;
        value += in.read();
        project.setNo(value);

        // 문자열을 읽을 바이트 배열을 준비한다.
        byte[] bytes = new byte[30000];

        // => 프로젝트 제목 읽기
        int len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setTitle(new String(bytes, 0, len, "UTF-8"));

        // => 프로젝트 내용 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setContent(new String(bytes, 0, len, "UTF-8"));

        // => 프로젝트 시작일 읽기
        in.read(bytes, 0, 10);
        project.setStartDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        // => 프로젝트 종료일 읽기
        in.read(bytes, 0, 10);
        project.setEndDate(Date.valueOf(new String(bytes, 0, 10, "UTF-8")));

        // => 프로젝트 소유주 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setOwner(new String(bytes, 0, len, "UTF-8"));

        // => 프로젝트 멤버들 읽기
        len = in.read() << 8 | in.read();
        in.read(bytes, 0, len);
        project.setMembers(new String(bytes, 0, len, "UTF-8"));

        projectList.add(project);
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

}
