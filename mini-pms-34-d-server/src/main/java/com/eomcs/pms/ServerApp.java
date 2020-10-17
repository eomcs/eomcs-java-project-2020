package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Stateful 통신
//=> 클라이언트가 연결되면 클라이언트가 보낸 메시지를 그대로 리턴해 준다.
//=> 클라이언트의 요청을 반복해서 처리한다.
//=> 클라이언트가 quit 명령을 보내면 응답한 후 연결을 끊는다.
//=> 응답의 끝에는 빈 줄을 보내도록 응답 프로토콜을 정의한다.
//   - 프로토콜이란? 클라이언트/서버 간의 데이터를 주고 받는 형식이다.
//
public class ServerApp {
  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      try (Socket socket = serverSocket.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(socket.getOutputStream())) {

        while (true) {
          String request = in.readLine();

          sendResponse(out, request);

          if (request.equalsIgnoreCase("quit"))
            break;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void sendResponse(PrintWriter out, String message) {
    out.println(message);
    out.println(); // 응답이 끝났음을 알리는 빈 줄을 보낸다.
    out.flush();
  }
}
