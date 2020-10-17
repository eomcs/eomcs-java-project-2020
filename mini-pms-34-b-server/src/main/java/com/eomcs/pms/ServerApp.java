package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Stateful 통신
//=> 클라이언트가 연결되면 클라이언트가 보낸 메시지를 그대로 리턴해 준다.
//
public class ServerApp {
  public static void main(String[] args) {
    // 8888 포트에서 클라이언트를 기다리는 서버 소켓을 생성한다.
    // 클라이언트가 연결된 소켓을 리턴 받는다.
    // 소켓을 통해 문자열 메시지를 입출력 할 수 있도록 스트림 객체를 준비한다.
    // 클라이언트가 보낸 메시지를 읽고, 다시 클라이언트에게 리턴해 준다.
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      try (Socket socket = serverSocket.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          PrintWriter out = new PrintWriter(socket.getOutputStream())) {

        String request = in.readLine();

        out.println(request);
        out.flush();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
