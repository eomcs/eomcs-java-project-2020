// LMS 클라이언트
package com.eomcs.lms;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import com.eomcs.util.Prompt;

public class ClientApp {

  Scanner keyboard = new Scanner(System.in);
  Prompt prompt = new Prompt(keyboard);

  Deque<String> commandStack;
  Queue<String> commandQueue;

  public ClientApp() throws Exception {
    commandStack = new ArrayDeque<>();
    commandQueue = new LinkedList<>();
  }

  public void service() {

    while (true) {
      String command;
      command = prompt.inputString("\n명령> ");

      if (command.length() == 0)
        continue;

      if (command.equals("history")) {
        printCommandHistory(commandStack.iterator());
        continue;
      } else if (command.equals("history2")) {
        printCommandHistory(commandQueue.iterator());
        continue;
      } else if (command.equals("quit")) {
        break;
      }

      commandStack.push(command);
      commandQueue.offer(command);

      processCommand(command);
    }
    keyboard.close();
  }

  private void processCommand(String command) {
    // 명령어 형식을 변경!
    // [기존 방식]
    // => 예) /board/list
    // [새 방식]
    // => 예) bitcamp://서버주소:포트번호/board/list
    //
    String host = null;
    int port = 9999;
    String servletPath = null;

    // 명령어를 분석하여 서버주소와 포트번호, 실행시킬 작업명을 분리한다.
    try {
      if (!command.startsWith("bitcamp://")) {
        throw new Exception("명령어 형식이 옳지 않습니다!");
      }

      // System.out.println(command);
      // command 예) bitcamp://localhost:9999/board/list

      String url = command.substring(10);
      // => localhost:9999/board/list

      // System.out.println(url);

      int index = url.indexOf('/'); // 14
      String[] str = //
          url.substring(0, index) // localhost:9999
              .split(":"); // {"localhost", "9999"}

      host = str[0];
      if (str.length == 2) {
        port = Integer.parseInt(str[1]);
      }
      // System.out.printf("=> %s:%d\n", host, port); // => localhost:9999

      servletPath = url.substring(index);
      // System.out.printf("=> %s\n", servletPath); // => /board/list

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
    }

    // 서버에 연결한다.
    try (Socket socket = new Socket(host, port);
        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream())) {

      // 서버에 명령을 보낸다.
      out.println(servletPath);
      out.flush();

      // 서버의 응답을 읽어서 출력한다.
      while (true) {
        String response = in.nextLine();
        if (response.equals("!end!")) {
          break;
        } else if (response.equals("!{}!")) {
          String input = prompt.inputString("");
          out.println(input);
        } else {
          System.out.println(response);
        }
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      count++;

      if ((count % 5) == 0) {
        String str = prompt.inputString(":");
        if (str.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("클라이언트 수업 관리 시스템입니다.");

    ClientApp app = new ClientApp();
    app.service();
  }
}
