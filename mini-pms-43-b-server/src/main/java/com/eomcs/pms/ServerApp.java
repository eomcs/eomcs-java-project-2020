package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.filter.FilterChain;
import com.eomcs.pms.handler.Request;
import com.eomcs.pms.listener.AppInitListener;
import com.eomcs.pms.listener.DataHandlerListener;
import com.eomcs.pms.listener.RequestMappingListener;

public class ServerApp {

  // 클라이언트가 "stop" 명령을 보내면 이 값이 true로 변경된다.
  // - 이 값이 true 이면 다음 클라이언트 접속할 때 서버를 종료한다.
  static boolean stop = false;

  // 스레드풀 준비
  ExecutorService threadPool = Executors.newCachedThreadPool();

  // 옵저버와 공유할 맵 객체
  static Map<String,Object> context = new Hashtable<>();

  // 옵저버를 보관할 컬렉션 객체
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 옵저버를 등록하는 메서드
  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  // 옵저버를 제거하는 메서드
  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  // 옵저버에게 통지한다.
  private void notifyApplicationContextListenerOnServiceStarted() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(context);
    }
  }

  // 옵저버에게 통지한다.
  private void notifyApplicationContextListenerOnServiceStopped() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(context);
    }
  }

  public void service(int port) {

    notifyApplicationContextListenerOnServiceStarted();

    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket clientSocket = serverSocket.accept();

        if (stop) {
          break;
        }
        // 직접 스레드를 생성하는 것이 아니라 스레드풀에 작업을 맡긴다.
        threadPool.execute(() -> handleClient(clientSocket));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    notifyApplicationContextListenerOnServiceStopped();

    // 스레드풀을 종료한다.
    threadPool.shutdown();

    try {
      // 스레드풀의 모든 스레드가 종료될 때까지 기다린다.
      if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
        System.out.println("아직 종료 안된 작업이 있다.");
        System.out.println("남아 있는 작업의 강제 종료를 시도하겠다.");
        // => 만약 10초가 경과될 때까지 종료되지 않으면,
        //    수행 중인 작업을 강제 종료하라고 지시하고,
        //    대기 중인 작업은 취소한다.
        threadPool.shutdownNow();

        // 그리고 다시 작업이 종료될 때까지 기다린다.
        if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
          System.out.println("스레드풀의 강제 종료를 완료하지 못했다.");
        } else {
          System.out.println("모든 작업을 강제 종료했다.");
        }
      }
    } catch (Exception e) {
      System.out.println("스레드풀 종료 중 오류 발생!");
    }
    System.out.println("서버 종료!");
  }

  public static void main(String[] args) {
    ServerApp server = new ServerApp();

    // 리스너(옵저버/구독자) 등록
    server.addApplicationContextListener(new AppInitListener());
    server.addApplicationContextListener(new DataHandlerListener());
    server.addApplicationContextListener(new RequestMappingListener());

    server.service(8888);
  }

  private static void handleClient(Socket clientSocket) {
    InetAddress address = clientSocket.getInetAddress();
    System.out.printf("클라이언트(%s)가 연결되었습니다.\n",
        address.getHostAddress());

    try (Socket socket = clientSocket; // try 블록을 떠날 때 close()가 자동 호출된다.
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream())) {

      // 클라이언트가 보낸 세션 아이디에 따라 세션 보관소를 준비한다.
      String sessionId = prepareSession(in.readLine());

      // 클라이언트가 보낸 요청을 읽는다.
      String requestLine = in.readLine();

      if (requestLine.equalsIgnoreCase("stop")) {
        stop = true; // 서버의 상태를 멈추라는 의미로 true로 설정한다.
        out.println("SessionID=xxx");
        out.println("서버를 종료하는 중입니다!");
        out.println();
        out.flush();
        return;
      }

      // 커맨드나 필터가 사용할 객체를 준비한다.
      Request request = new Request(requestLine, context, out, in, sessionId);

      // context 맵에 보관된 필터 체인을 꺼낸다.
      FilterChain filterChain = (FilterChain) context.get("filterChain");

      // 필터들의 체인을 실행한다.
      // => 필터 체인을 따라가면서 중간에 삽입된 필터가 있다면 실행할 것이다.
      // => 마지막 필터에서는 클라이언트가 요청한 명령을 처리할 것이다.
      if (filterChain != null) {
        // 클라이언트 응답 첫 줄에 세션 ID를 출력한다.
        out.printf("SessionID=%s\n", sessionId);
        filterChain.doFilter(request);
      }

      // 응답의 끝을 알리는 빈 문자열을 보낸다.
      out.println();
      out.flush();

    } catch (Exception e) {
      System.out.println("클라이언트와의 통신 오류!");
      e.printStackTrace();
    }

    System.out.printf("클라이언트(%s)와의 연결을 끊었습니다.\n",
        address.getHostAddress());
  }

  private static String prepareSession(String sessionInfo) {

    String[] values = sessionInfo.split("=");

    // 클라이언트에서 자신의 세션 아이디를 보내왔다면,
    if (values.length == 2 && context.get(values[1]) != null) {
      // 기존에 서버에서 발급한 세션 아이디를 그대로 리턴한다.
      return values[1];
    }

    // 세션 아이디가 없다면,
    // 클라이언트에게 새 세션 아이디를 부여한다.
    String sessionId = UUID.randomUUID().toString();

    // 새 세션을 위한 보관소를 생성한다.
    HashMap<String,Object> sessionMap = new HashMap<>();

    // 필터나 커맨드가 사용할 수 있도록 context 맵에 저장한다.
    context.put(sessionId, sessionMap);

    return sessionId;
  }
}