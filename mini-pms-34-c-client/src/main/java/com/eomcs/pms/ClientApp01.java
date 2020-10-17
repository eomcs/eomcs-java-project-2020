package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.eomcs.util.Prompt;

// Stateful 통신
// => 서버와 연결하여 간단한 메시지 주고 받기
// => 사용자가 입력한 명령을 서버에 전송하기
//
public class ClientApp01 {
  public static void main(String[] args) {
    try (Socket socket = new Socket("localhost", 8888);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      String input = Prompt.inputString("명령> ");
      out.println(input);
      out.flush();

      String response = in.readLine();
      System.out.println(response);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
