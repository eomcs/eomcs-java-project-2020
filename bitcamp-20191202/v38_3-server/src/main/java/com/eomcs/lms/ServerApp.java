// LMS 서버
package com.eomcs.lms;

import java.io.IOException;
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
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.servlet.BoardAddServlet;
import com.eomcs.lms.servlet.BoardDeleteServlet;
import com.eomcs.lms.servlet.BoardDetailServlet;
import com.eomcs.lms.servlet.BoardListServlet;
import com.eomcs.lms.servlet.BoardUpdateServlet;
import com.eomcs.lms.servlet.LessonAddServlet;
import com.eomcs.lms.servlet.LessonDeleteServlet;
import com.eomcs.lms.servlet.LessonDetailServlet;
import com.eomcs.lms.servlet.LessonListServlet;
import com.eomcs.lms.servlet.LessonUpdateServlet;
import com.eomcs.lms.servlet.MemberAddServlet;
import com.eomcs.lms.servlet.MemberDeleteServlet;
import com.eomcs.lms.servlet.MemberDetailServlet;
import com.eomcs.lms.servlet.MemberListServlet;
import com.eomcs.lms.servlet.MemberSearchServlet;
import com.eomcs.lms.servlet.MemberUpdateServlet;
import com.eomcs.lms.servlet.PhotoBoardAddServlet;
import com.eomcs.lms.servlet.PhotoBoardDeleteServlet;
import com.eomcs.lms.servlet.PhotoBoardDetailServlet;
import com.eomcs.lms.servlet.PhotoBoardListServlet;
import com.eomcs.lms.servlet.PhotoBoardUpdateServlet;
import com.eomcs.lms.servlet.Servlet;

public class ServerApp {

  // 옵저버 관련 코드
  Set<ApplicationContextListener> listeners = new HashSet<>();
  Map<String, Object> context = new HashMap<>();

  // 커맨드(예: Servlet 구현체) 디자인 패턴과 관련된 코드
  Map<String, Servlet> servletMap = new HashMap<>();

  // 스레드 풀
  ExecutorService executorService = Executors.newCachedThreadPool();

  // 서버 멈춤 여부 설정 변수
  boolean serverStop = false;

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
    PhotoBoardDao photoBoardDao = (PhotoBoardDao) context.get("photoBoardDao");
    PhotoFileDao photoFileDao = (PhotoFileDao) context.get("photoFileDao");

    // 커맨드 객체 역할을 수행하는 서블릿 객체를 맵에 보관한다.
    servletMap.put("/board/list", new BoardListServlet(boardDao));
    servletMap.put("/board/add", new BoardAddServlet(boardDao));
    servletMap.put("/board/detail", new BoardDetailServlet(boardDao));
    servletMap.put("/board/update", new BoardUpdateServlet(boardDao));
    servletMap.put("/board/delete", new BoardDeleteServlet(boardDao));

    servletMap.put("/lesson/list", new LessonListServlet(lessonDao));
    servletMap.put("/lesson/add", new LessonAddServlet(lessonDao));
    servletMap.put("/lesson/detail", new LessonDetailServlet(lessonDao));
    servletMap.put("/lesson/update", new LessonUpdateServlet(lessonDao));
    servletMap.put("/lesson/delete", new LessonDeleteServlet(lessonDao));

    servletMap.put("/member/list", new MemberListServlet(memberDao));
    servletMap.put("/member/add", new MemberAddServlet(memberDao));
    servletMap.put("/member/detail", new MemberDetailServlet(memberDao));
    servletMap.put("/member/update", new MemberUpdateServlet(memberDao));
    servletMap.put("/member/delete", new MemberDeleteServlet(memberDao));
    servletMap.put("/member/search", new MemberSearchServlet(memberDao));

    servletMap.put("/photoboard/list", new PhotoBoardListServlet( //
        photoBoardDao, lessonDao));
    servletMap.put("/photoboard/detail", new PhotoBoardDetailServlet( //
        photoBoardDao, photoFileDao));
    servletMap.put("/photoboard/add", new PhotoBoardAddServlet( //
        photoBoardDao, lessonDao, photoFileDao));
    servletMap.put("/photoboard/update", new PhotoBoardUpdateServlet( //
        photoBoardDao, photoFileDao));
    servletMap.put("/photoboard/delete", new PhotoBoardDeleteServlet( //
        photoBoardDao, photoFileDao));

    try (ServerSocket serverSocket = new ServerSocket(9999)) {

      System.out.println("클라이언트 연결 대기중...");

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("클라이언트와 연결되었음!");

        executorService.submit(() -> {
          processRequest(socket);
          System.out.println("--------------------------------------");
        });

        // 현재 '서버 멈춤' 상태라면,
        // 다음 클라이언트 요청을 받지 않고 종료한다.
        if (serverStop) {
          break;
        }

      }

    } catch (Exception e) {
      System.out.println("서버 준비 중 오류 발생!");
    }


    // 스레드풀을 다 사용했으면 종료하라고 해야 한다.
    executorService.shutdown();
    // => 스레드풀을 당장 종료시키는 것이 아니다.
    // => 스레드풀에 소속된 스레드들의 작업이 모두 끝나면
    // 스레드풀의 동작을 종료하라는 뜻이다.
    // => 따라서 shutdown()을 호출했다고 해서
    // 모든 스레드가 즉시 작업을 멈추는 것이 아니다.
    // => 즉 스레드풀 종료를 예약한 다음에 바로 리턴한다.

    // 모든 스레드가 끝날 때까지 DB 커넥션을 종료하고 싶지 않다면,
    // 스레드가 끝났는지 검사하며 기다려야 한다.
    while (true) {
      if (executorService.isTerminated()) {
        break;
      }
      try {
        // 0.5초 마다 깨어나서 스레드 종료 여부를 검사한다.
        Thread.sleep(500);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // 클라이언트 요청을 처리하는 스레드가 모두 종료된 후에
    // DB 커넥션을 닫도록 한다.
    notifyApplicationDestroyed();

    System.out.println("서버 종료!");
  } // service()


  void processRequest(Socket clientSocket) {

    try (Socket socket = clientSocket;
        Scanner in = new Scanner(socket.getInputStream());
        PrintStream out = new PrintStream(socket.getOutputStream())) {

      String request = in.nextLine();
      System.out.printf("=> %s\n", request);

      if (request.equalsIgnoreCase("/server/stop")) {
        quit(out);
        return;
      }

      Servlet servlet = servletMap.get(request);

      if (servlet != null) {
        try {
          servlet.service(in, out);

        } catch (Exception e) {
          out.println("요청 처리 중 오류 발생!");
          out.println(e.getMessage());

          System.out.println("클라이언트 요청 처리 중 오류 발생:");
          e.printStackTrace();
        }
      } else {
        notFound(out);
      }
      out.println("!end!");
      out.flush();
      System.out.println("클라이언트에게 응답하였음!");

    } catch (Exception e) {
      System.out.println("예외 발생:");
      e.printStackTrace();
    }
  }

  private void notFound(PrintStream out) throws IOException {
    out.println("요청한 명령을 처리할 수 없습니다.");
  }

  private void quit(PrintStream out) throws IOException {
    serverStop = true;
    out.println("OK");
    out.println("!end!");
    out.flush();
  }

  public static void main(String[] args) {
    System.out.println("서버 수업 관리 시스템입니다.");

    ServerApp app = new ServerApp();
    app.addApplicationContextListener(new DataLoaderListener());
    app.service();
  }
}
