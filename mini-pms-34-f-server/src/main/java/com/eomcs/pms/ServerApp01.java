package com.eomcs.pms;

import java.net.ServerSocket;

//Stateful 통신
//=> 클라이언트가 연결되면 클라이언트가 보낸 메시지를 그대로 리턴해 준다.
//=> 클라이언트의 요청을 반복해서 처리한다.
//=> 클라이언트가 quit 명령을 보내면 응답한 후 연결을 끊는다.
//=> 응답의 끝에는 빈 줄을 보내도록 응답 프로토콜을 정의한다.
//   - 프로토콜이란? 클라이언트/서버 간의 데이터를 주고 받는 형식이다.
//=> 클라이언트 연결이 끊어지면 다음 클라이언트와 연결하는 것을 반복한다.
//=> 클라이언트가 접속하거나 연결을 끊으면 로그를 남긴다.
//=> 다중 클라이언트의 동시 접속을 처리한다.
//   - Runnable 구현체로 스레드 작업을 처리한다.
public class ServerApp01 {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      while (true) {
        //        ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
        //        Thread t = new Thread(clientHandler);
        //        t.start();
        // 외부 클래스 사용
        new Thread(new ClientHandler(serverSocket.accept())).start();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
