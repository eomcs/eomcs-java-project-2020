package com.eomcs.pms;

import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.handler.BoardHandler;
import com.eomcs.pms.handler.MemberHandler;
import com.eomcs.pms.handler.ProjectHandler;
import com.eomcs.pms.handler.TaskHandler;
import com.eomcs.util.ArrayList;
import com.eomcs.util.LinkedList;
import com.eomcs.util.Prompt;
import com.eomcs.util.Queue;
import com.eomcs.util.Stack;

public class App {

  public static void main(String[] args) {

    // 단지 유지보수를 좋게 하기 위해 ArrayList와 LinkedList의 공통 분모를 뽑아서
    // 만든 클래스가 List이다.
    // List는 클래스는 실제 작업을 하는 클래스가 아니다.
    // 그럼에도 불구하고 개발자가 다음과 같이 List 객체를 사용하려 한다면 막을 수 없다.
    // => BoardHandler의 경우 아무런 작업을 수행하지 않을 것이다.
    // => 왜? List 클래스에 정의된 메서드는 아무것도 하지 않는다.
    //
    //    List<Board> boardList = new List<>();

    // 해결책?
    // => 이렇게 generalization을 통해 만든 클래스의 경우
    //    서브 클래스에게 공통 분모를 물려주기 위한 용도로 사용된다.
    // => 이런 류의 클래스는 직접 인스턴스를 생성하지 못하도록 해서
    //    직접 사용하는 것을 막아야 한다.
    // => 이런 용도로 사용하는 문법이 "추상 클래스(abstract class)"이다.
    //
    // List 클래스(AbstractList로 이름 변경함)를 추상 클래스로 만들면,
    // 다음과 같이 인스턴스를 생성할 수 없다.
    // 아예 인스턴스 생성을 원천적으로 차단하는 효과가 있다.
    //
    //    AbstractList<Board> boardList = new AbstractList<>(); // 컴파일 오류!
    //

    // 받드시 AbstractList의 일반 하위 객체를 정의해야 한다.
    //
    ArrayList<Board> boardList = new ArrayList<>();
    BoardHandler boardHandler = new BoardHandler(boardList);

    LinkedList<Member> memberList = new LinkedList<>();
    MemberHandler memberHandler = new MemberHandler(memberList);

    LinkedList<Project> projectList = new LinkedList<>();
    ProjectHandler projectHandler = new ProjectHandler(projectList, memberHandler);

    ArrayList<Task> taskList = new ArrayList<>();
    TaskHandler taskHandler = new TaskHandler(taskList, memberHandler);

    Stack<String> commandStack = new Stack<>();
    Queue<String> commandQueue = new Queue<>();

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
          case "history": printCommandHistory(commandStack); break;
          // history2 명령을 처리한다.
          case "history2": printCommandHistory2(commandQueue); break;
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

  static void printCommandHistory(Stack<String> commandStack) {
    try {
      // 스택은 한 번 pop() 하면 데이터가 제거된다.
      // 따라서 복제본을 만들어 사용한다.
      // 또한 clone() 메서드는 복제 작업 중 오류가 발생하면 예외를 발생시키기 때문에
      // try...catch... 블록으로 처리한다.
      Stack<String> history = commandStack.clone();

      int count = 0;
      while (!history.empty()) {
        System.out.println(history.pop());
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

  static void printCommandHistory2(Queue<String> commandQueue) {
    try {
      // Queue는 한 번 poll() 하면 데이터가 제거된다.
      // 따라서 복제본을 만들어 사용한다.
      // 또한 clone() 메서드는 복제 작업 중 오류가 발생하면 예외를 발생시키기 때문에
      // try...catch... 블록으로 처리한다.
      Queue<String> history = commandQueue.clone();

      int count = 0;
      while (history.size() > 0) {
        System.out.println(history.poll());
        count++;

        // 5개 출력할 때 마다 계속 출력할지 묻는다.
        if ((count % 5) == 0 && Prompt.inputString(":").equalsIgnoreCase("q")) {
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("history2 명령 처리 중 오류 발생!");
    }
  }
}
