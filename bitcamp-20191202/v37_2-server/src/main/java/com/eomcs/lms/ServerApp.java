// LMS 서버
package com.eomcs.lms;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.dao.LessonDao;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.servlet.BoardListServlet;
import com.eomcs.lms.servlet.LessonListServlet;
import com.eomcs.lms.servlet.MemberListServlet;
import com.eomcs.lms.servlet.Servlet;

public class ServerApp {

  // 옵저버 관련 코드
  Set<ApplicationContextListener> listeners = new HashSet<>();
  Map<String, Object> context = new HashMap<>();

  // 커맨드(예: Servlet 구현체) 디자인 패턴과 관련된 코드
  Map<String, Servlet> servletMap = new HashMap<>();

  // 스레드 풀
  ExecutorService executorService = Executors.newCachedThreadPool();

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  private void notifyApplicationInitialized() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(context);
    }
  }

  private void notifyApplicationDestroyed() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(context);
    }
  }
  // 옵저버 관련코드 끝!

  public void service() {

    notifyApplicationInitialized();

    // DataLoaderListener가 준비한 DAO 객체를 꺼내 변수에 저장한다.
    BoardDao boardDao = (BoardDao) context.get("boardDao");
    LessonDao lessonDao = (LessonDao) context.get("lessonDao");
    MemberDao memberDao = (MemberDao) context.get("memberDao");

    // 커맨드 객체 역할을 수행하는 서블릿 객체를 맵에 보관한다.
    servletMap.put("/board/list", new BoardListServlet(boardDao));
    // servletMap.put("/board/add", new BoardAddServlet(boardDao));
    // servletMap.put("/board/detail", new BoardDetailServlet(boardDao));
    // servletMap.put("/board/update", new BoardUpdateServlet(boardDao));
    // servletMap.put("/board/delete", new BoardDeleteServlet(boardDao));
    //
    servletMap.put("/lesson/list", new LessonListServlet(lessonDao));
    // servletMap.put("/lesson/add", new LessonAddServlet(lessonDao));
    // servletMap.put("/lesson/detail", new LessonDetailServlet(lessonDao));
    // servletMap.put("/lesson/update", new LessonUpdateServlet(lessonDao));
    // servletMap.put("/lesson/delete", new LessonDeleteServlet(lessonDao));
    //
    servletMap.put("/member/list", new MemberListServlet(memberDao));
    // servletMap.put("/member/add", new MemberAddServlet(memberDao));
    // servletMap.put("/member/detail", new MemberDetailServlet(memberDao));
    // servletMap.put("/member/update", new MemberUpdateServlet(memberDao));
    // servletMap.put("/member/delete", new MemberDeleteServlet(memberDao));

    try (ServerSocket serverSocket = new ServerSocket(9999)) {

      System.out.println("클라이언트 연결 대기중...");

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("클라이언트와 연결되었음!");

        executorService.submit(() -> {
          processRequest(socket);
          System.out.println("--------------------------------------");
        });
      }

    } catch (Exception e) {
      System.out.println("서버 준비 중 오류 발생!");
    }

    notifyApplicationDestroyed();

    // 스레드풀을 다 사용했으면 종료하라고 해야 한다.
    executorService.shutdown();
    // => 스레드풀을 당장 종료시키는 것이 아니다.
    // => 스레드풀에 소속된 스레드들의 작업이 모두 끝나면 종료하는 뜻이다.

  } // service()


  int processRequest(Socket clientSocket) {

    try (Socket socket = clientSocket;
        Scanner in = new Scanner(socket.getInputStream());
        PrintStream out = new PrintStream(socket.getOutputStream())) {

      // 클라이언트가 보낸 명령을 읽는다.
      String request = in.nextLine();
      System.out.printf("=> %s\n", request);

      // 클라이언트에게 응답한다.
      // if (request.equalsIgnoreCase("/server/stop")) {
      // return 9; // 서버를 종료한다. }
      // }

      // 클라이언트의 요청을 처리할 객체를 찾는다.
      Servlet servlet = servletMap.get(request);

      if (servlet != null) {
        try {
          // 클라이언트 요청을 처리할 객체를 찾았으면 작업을 실행시킨다.
          servlet.service(in, out);

        } catch (Exception e) {
          // 요청한 작업을 수행하다가 오류 발생할 경우
          // 그 이유를 간단히 응답한다.
          out.println("요청 처리 중 오류 발생!");
          out.println(e.getMessage());

          // 서버쪽 화면에는 더 자세하게 오류 내용을 출력한다.
          System.out.println("클라이언트 요청 처리 중 오류 발생:");
          e.printStackTrace();
        }
      } else { // 없다면? 간단한 안내 메시지를 응답한다.
        notFound(out);
      }
      out.println("!end!");
      out.flush();
      System.out.println("클라이언트에게 응답하였음!");

      return 0;

    } catch (Exception e) {
      System.out.println("예외 발생:");
      e.printStackTrace();
      return -1;
    }
  }

  private void notFound(PrintStream out) throws IOException {
    out.println("요청한 명령을 처리할 수 없습니다.");
  }

  private void quit(ObjectOutputStream out) throws IOException {
    out.writeUTF("OK");
    out.flush();
  }

  public static void main(String[] args) {
    System.out.println("서버 수업 관리 시스템입니다.");

    ServerApp app = new ServerApp();
    app.addApplicationContextListener(new DataLoaderListener());
    app.service();
  }
}
