package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.eomcs.util.Prompt;

public class ClientApp {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("프로그램 사용법:");
      System.out.println("  java -cp ... ClientApp 서버주소 포트번호");
      System.exit(0);
    }

    // 클라이언트가 서버에 stop 명령을 보내면 다음 변수를 true로 변경한다.
    boolean stop = false;

    try (Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

      while (true) {
        String input = Prompt.inputString("명령> ");
        out.println(input);
        out.flush();

        receiveResponse(in);

        if (input.equalsIgnoreCase("quit")) {
          break;
        } else if (input.equalsIgnoreCase("stop")) {
          stop = true;
          break;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    if (stop) {
      // 서버를 멈추기 위해 그냥 접속했다가 끊는다.
      try (Socket socket = new Socket(args[0], Integer.parseInt(args[1]))) {
        // 아무것도 안한다.
        // 서버가 stop 할 기회를 주기 위함이다.
      } catch (Exception e) {
        // 아무것도 안한다.
      }
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
