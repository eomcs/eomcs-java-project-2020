package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

// 클라이언트 요청을 처리하는 일을 한다.
public class ClientHandler implements Runnable {
  Socket socket;

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {

    InetAddress address = this.socket.getInetAddress();
    System.out.printf("클라이언트(%s)가 연결되었습니다.\n",
        address.getHostAddress());

    try (Socket socket = this.socket; // try 블록을 떠날 때 close()가 자동 호출된다.
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream())) {

      while (true) {
        String request = in.readLine();

        sendResponse(out, request);

        if (request.equalsIgnoreCase("quit"))
          break;
      }


    } catch (Exception e) {
      System.out.println("클라이언트와의 통신 오류!");
    }

    System.out.printf("클라이언트(%s)와의 연결을 끊었습니다.\n",
        address.getHostAddress());
  }

  private void sendResponse(PrintWriter out, String message) {
    out.println(message);
    out.println(); // 응답이 끝났음을 알리는 빈 줄을 보낸다.
    out.flush();
  }
}
