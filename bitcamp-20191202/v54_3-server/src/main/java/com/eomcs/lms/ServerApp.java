// LMS 서버
package com.eomcs.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ConnectionClosedException;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ExceptionListener;
import org.apache.hc.core5.http.HttpConnection;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.util.TimeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.util.RequestHandler;
import com.eomcs.util.RequestMappingHandlerMapping;

public class ServerApp //
    implements ExceptionListener, HttpRequestHandler {

  // log4j의 logger 준비
  static Logger logger = LogManager.getLogger(ServerApp.class);

  // 옵저버 관련 코드
  Set<ApplicationContextListener> listeners = new HashSet<>();
  Map<String, Object> context = new HashMap<>();

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

  public void service() throws Exception {
    notifyApplicationInitialized();

    // ApplicationContext (IoC 컨테이너)를 꺼낸다.
    iocContainer = (ApplicationContext) context.get("iocContainer");

    // request handler mapper를 꺼낸다.
    handlerMapper = //
        (RequestMappingHandlerMapping) context.get("handlerMapper");

    // 소켓 동작 설정
    SocketConfig socketConfig = SocketConfig.custom() //
        .setSoTimeout(15, TimeUnit.SECONDS)//
        .setTcpNoDelay(true) //
        .build();

    // HTTP 서버 준비
    HttpServer server = ServerBootstrap.bootstrap()//
        .setListenerPort(9999) // 웹서버 포트 번호 설정
        .setSocketConfig(socketConfig) // 기본 소켓 동작 설정
        .setSslContext(null) // SSL 설정
        .setExceptionListener(this) // 예외 처리자 설정
        .register("*", this) // 요청 처리자 설정
        .create(); // 웹서버 객체 생성

    // 웹서버를 시작시킨다.
    server.start();

    // 웹서버를 종료시키는 스레드를 등록한다.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        notifyApplicationDestroyed();
        logger.info("서버 종료!");
        server.close(CloseMode.GRACEFUL);
      }
    });

    logger.info("비트서버 시작(9999)!");

    server.awaitTermination(TimeValue.MAX_VALUE);
  } // service()

  private void notFound(PrintWriter out) throws IOException {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>실행 오류!</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>실행 오류!</h1>");
    out.println("<p>요청한 명령을 처리할 수 없습니다.</p>");
    out.println("</body>");
    out.println("</html>");
  }

  private void error(PrintWriter out, Exception ex) throws IOException {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>실행 오류!</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>실행 오류!</h1>");
    out.printf("<p>%s</p>\n", ex.getMessage());
    out.println("</body>");
    out.println("</html>");
  }

  private String getServletPath(String requestUri) {
    // requestUri => /member/add?email=aaa@test.com&name=aaa&password=1111
    return requestUri.split("\\?")[0]; // 예) /member/add
  }

  private Map<String, String> getParameters(String requestUri) throws Exception {
    // 데이터(Query String)는 따로 저장
    // => /member/list?email=aaa@test.com&name=aaa&password=1111
    Map<String, String> params = new HashMap<>();
    String[] items = requestUri.split("\\?");
    if (items.length > 1) {
      logger.debug(String.format("query string => %s", items[1]));
      String[] entries = items[1].split("&");
      for (String entry : entries) {
        logger.debug(String.format("parameter => %s", entry));
        String[] kv = entry.split("=");

        if (kv.length > 1) {
          // 웹브라우저가 URL 인코딩하여 보낸 데이터를
          // 디코딩하여 String 객체로 만든다.
          String value = URLDecoder.decode(kv[1], "UTF-8");

          params.put(kv[0], value);
        } else {
          params.put(kv[0], "");
        }
      }
    }
    return params;
  }

  // ExceptionListener 인터페이스 구현
  @Override
  public void onError(final Exception ex) {
    ex.printStackTrace();
  }

  @Override
  public void onError(final HttpConnection conn, final Exception ex) {
    if (ex instanceof SocketTimeoutException) {
      System.err.println("Connection timed out");
    } else if (ex instanceof ConnectionClosedException) {
      System.err.println(ex.getMessage());
    } else {
      ex.printStackTrace();
    }
  }
  // ExceptionListener 인터페이스 구현 - 끝

  // HttpRequestHandler 인터페이스 구현
  @Override
  public void handle( //
      final ClassicHttpRequest request, // 클라이언트 요청처리 도구
      final ClassicHttpResponse response, // 클라이언트 응답처리 도구
      final HttpContext context // HTTP 설정 도구
  ) throws HttpException, IOException {

    logger.info("--------------------------------------");
    logger.info("클라이언트의 요청이 들어옴!");

    // 클라이언트로 콘텐트 출력할 때 사용할 도구 준비
    // => 이 출력 스트림을 사용하여 출력하는 모든 데이터는
    // 메모리에 임시 보관된다.
    StringWriter outBuffer = new StringWriter();
    PrintWriter out = new PrintWriter(outBuffer);

    String method = request.getMethod(); // 예) GET|POST|HEAD|PUT|...
    String requestUri = request.getPath(); // 예) /photoboard/list?lessonNo=100
    logger.info("{} {}", method, requestUri);

    try {
      String servletPath = getServletPath(requestUri);
      logger.debug(String.format("servlet path => %s", servletPath));

      Map<String, String> params = getParameters(requestUri);
      RequestHandler requestHandler = handlerMapper.getHandler(servletPath);

      if (requestHandler != null) {
        // Request Handler의 메서드 호출
        requestHandler.getMethod().invoke( //
            requestHandler.getBean(), //
            params, out);

      } else {
        notFound(out);
        logger.info("해당 명령을 지원하지 않습니다.");
      }
    } catch (Exception e) {
      error(out, e);

      logger.info("클라이언트 요청 처리 중 오류 발생");
      logger.info(e.getMessage());
      StringWriter strWriter = new StringWriter();
      e.printStackTrace(new PrintWriter(strWriter));
      logger.debug(strWriter.toString());
    }

    // 작업한 결과를 클라이언트로 보낸다.
    response.setCode(HttpStatus.SC_OK); // => HTTP/1.1 200 OK
    response.setEntity(new StringEntity(//
        outBuffer.toString(), //
        ContentType.create("text/html", Charset.forName("UTF-8"))));
    logger.info("클라이언트에게 응답하였음!");
  }
  // HttpRequestHandler 인터페이스 구현 - 끝

  public static void main(String[] args) throws Exception {
    logger.info("서버 수업 관리 시스템입니다.");

    ServerApp app = new ServerApp();
    app.addApplicationContextListener(new ContextLoaderListener());
    app.service();
  }
}
