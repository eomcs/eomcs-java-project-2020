// LMS 서버
package com.eomcs.lms;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.util.RequestHandler;
import com.eomcs.util.RequestMappingHandlerMapping;

public class ServerApp {

  // log4j의 logger 준비
  static Logger logger = LogManager.getLogger(ServerApp.class);

  // 옵저버 관련 코드
  Set<ApplicationContextListener> listeners = new HashSet<>();
  Map<String, Object> context = new HashMap<>();

  // 스레드 풀
  ExecutorService executorService = Executors.newCachedThreadPool();

  // 서버 멈춤 여부 설정 변수
  boolean serverStop = false;

  // IoC 컨테이너 준비
  ApplicationContext iocContainer;

  // request handler 맵퍼 준비
  RequestMappingHandlerMapping handlerMapper;

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

    // ApplicationContext (IoC 컨테이너)를 꺼낸다.
    iocContainer = (ApplicationContext) context.get("iocContainer");

    // request handler mapper를 꺼낸다.
    handlerMapper = //
        (RequestMappingHandlerMapping) context.get("handlerMapper");

    try (ServerSocket serverSocket = new ServerSocket(9999)) {

      logger.info("클라이언트 연결 대기중...");

      while (true) {
        Socket socket = serverSocket.accept();
        logger.info("클라이언트와 연결되었음!");

        executorService.submit(() -> {
          processRequest(socket);
          logger.info("--------------------------------------");
        });

        // 현재 '서버 멈춤' 상태라면,
        // 다음 클라이언트 요청을 받지 않고 종료한다.
        if (serverStop) {
          break;
        }

      }

    } catch (Exception e) {
      logger.error(String.format("서버 준비 중 오류 발생!: %s", //
          e.getMessage()));
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

    logger.info("서버 종료!");
  } // service()


  void processRequest(Socket clientSocket) {

    try (Socket socket = clientSocket;
        Scanner in = new Scanner(socket.getInputStream());
        PrintStream out = new PrintStream(socket.getOutputStream())) {

      String request = in.nextLine();
      logger.info(String.format("요청 명령 => %s", request));

      if (request.equalsIgnoreCase("/server/stop")) {
        quit(out);
        return;
      }

      RequestHandler requestHandler = handlerMapper.getHandler(request);

      if (requestHandler != null) {
        try {
          requestHandler.getMethod().invoke( //
              requestHandler.getBean(), //
              in, out);

        } catch (Exception e) {
          out.println("요청 처리 중 오류 발생!");
          out.println(e.getMessage());

          logger.info("클라이언트 요청 처리 중 오류 발생");
          logger.info(e.getMessage());
          StringWriter strWriter = new StringWriter();
          e.printStackTrace(new PrintWriter(strWriter));
          logger.debug(strWriter.toString());
        }
      } else {
        notFound(out);
        logger.info("해당 명령을 지원하지 않습니다.");
      }
      out.println("!end!");
      out.flush();
      logger.info("클라이언트에게 응답하였음!");

    } catch (Exception e) {
      logger.error(String.format("예외 발생: %s", e.getMessage()));
      StringWriter strWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(strWriter));
      logger.debug(strWriter.toString());
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
    logger.info("서버 수업 관리 시스템입니다.");

    ServerApp app = new ServerApp();
    app.addApplicationContextListener(new ContextLoaderListener());
    app.service();
  }
}
