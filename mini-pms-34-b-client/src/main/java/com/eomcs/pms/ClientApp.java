package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Stateful 통신
// => 서버와 연결하여 간단한 메시지 주고 받기
//
public class ClientApp {
  public static void main(String[] args) {
    // 서버주소: localhost
    // 서버포트: 8888

    try (Socket socket = new Socket("localhost", 8888);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      out.println("Hello!");
      out.flush();

      String response = in.readLine();
      System.out.println(response);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
