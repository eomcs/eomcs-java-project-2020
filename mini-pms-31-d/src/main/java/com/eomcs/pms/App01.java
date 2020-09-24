package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
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
import com.eomcs.util.CsvObject;
import com.eomcs.util.ObjectFactory;
import com.eomcs.util.Prompt;

public class App01 {

  public static void main(String[] args) {
    // 스태틱 멤버들이 공유하는 변수가 아니라면 로컬 변수로 만들라.
    List<Board> boardList = new ArrayList<>();
    File boardFile = new File("./board.csv"); // 게시글을 저장할 파일 정보

    List<Member> memberList = new LinkedList<>();
    File memberFile = new File("./member.csv"); // 회원을 저장할 파일 정보

    List<Project> projectList = new LinkedList<>();
    File projectFile = new File("./project.csv"); // 프로젝트를 저장할 파일 정보

    List<Task> taskList = new ArrayList<>();
    File taskFile = new File("./task.csv"); // 작업을 저장할 파일 정보

    // 파일에서 데이터 로딩
    // => loadObjects(Collection<T>, File, ObjectFactory<T>)
    // => 첫 번째 파라미터: ObjectFactory.create()가 만든 객체를 보관하는 컬렉션이다.
    // => 두 번째 파라미터: CSV 문자열이 저장된 파일 정보이다.
    // => 세 번재 파라미터: CSV 문자열을 객체로 만들어주는 create() 메서드를 가진 ObjectFactory 구현체이다.
    // ObjectFactory의 구현체는 따로 만들지 말고 
    // 메서드 레퍼런스를 통해 기존에 존재하는 메서드를 전달한다.
    // 즉 '메서드 레퍼런스' 문법을 이용하여 
    // 기존 도메인 객체에 있던 정의되어 있던 valueOfCsv() 메서드를 전달한다.
    // 단 ObjectFactory.create() 메서드와 
    // valueOfCsv() 메서드의 파라미터와 리턴 타입이 같다는 전제하에서다.
    //
    loadObjects(boardList, boardFile, Board::valueOfCsv);
    loadObjects(memberList, memberFile, Member::valueOfCsv);
    loadObjects(projectList, projectFile, Project::valueOfCsv);
    loadObjects(taskList, taskFile, Task::valueOfCsv);

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
    saveObjects(boardList, boardFile);
    saveObjects(memberList, memberFile);
    saveObjects(projectList, projectFile);
    saveObjects(taskList, taskFile);
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

  private static <T extends CsvObject> void saveObjects(Collection<T> list, File file) {
    BufferedWriter out = null;

    try {
      out = new BufferedWriter(new FileWriter(file));

      for (T csvObject : list) {
        out.write(csvObject.toCsvString());
        out.write("\n");
      }

      out.flush();

      System.out.printf("총 %d 개의 객체를 '%s' 파일에 저장했습니다.\n", 
          list.size(), file.getName());

    } catch (IOException e) {
      System.out.printf("객체를 '%s' 파일에  쓰는 중 오류 발생! - %s\n", 
          file.getName(), e.getMessage());

    } finally {
      try {
        out.close();
      } catch (IOException e) {
      }
    }
  }

  // 파일에서 CSV 문자열을 읽어  객체를 생성한 후 컬렉션에 저장한다.
  private static <T> void loadObjects(
      Collection<T> list, // 객체를 담을 컬렉션 
      File file, // CSV 문자열이 저장된 파일
      ObjectFactory<T> factory // CSV 문자열을 받아, T 타입의 객체를 생성해주는 공장
      ) {
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader(file));

      while (true) {
        String record = in.readLine();
        if (record == null) {
          break;
        }
        list.add(factory.create(record));
      }
      System.out.printf("'%s' 파일에서 총 %d 개의 객체를 로딩했습니다.\n", 
          file.getName(), list.size());

    } catch (Exception e) {
      System.out.printf("'%s' 파일 읽기 중 오류 발생! - %s\n",
          file.getName(), e.getMessage());

    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }
}
