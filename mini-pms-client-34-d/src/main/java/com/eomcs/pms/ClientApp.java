package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.eomcs.util.Prompt;

// Stateful 통신
// => 서버와 연결하여 간단한 메시지 주고 받기
// => 사용자가 입력한 명령을 서버에 전송하기
// => 사용자가 quit 명령을 입력할 때까지 반복한다.
// => 서버가 응답의 끝을 알리는 빈 줄을 보낼 때까지 클라이언트는 계속 읽는다.
//
public class ClientApp {
  public static void main(String[] args) {
    try (Socket socket = new Socket("localhost", 8888);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      while (true) {
        String input = Prompt.inputString("명령> ");
        out.println(input);
        out.flush();

        receiveResponse(in);

        if (input.equalsIgnoreCase("quit"))
          break;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void receiveResponse(BufferedReader in) throws Exception {
    while (true) {
      String response = in.readLine();
      if (response.length() == 0)
        break;
      System.out.println(response);
    }
  }
}
