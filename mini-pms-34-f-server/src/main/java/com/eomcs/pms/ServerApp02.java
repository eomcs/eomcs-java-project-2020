package com.eomcs.pms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//Stateful 통신
//=> 클라이언트가 연결되면 클라이언트가 보낸 메시지를 그대로 리턴해 준다.
//=> 클라이언트의 요청을 반복해서 처리한다.
//=> 클라이언트가 quit 명령을 보내면 응답한 후 연결을 끊는다.
//=> 응답의 끝에는 빈 줄을 보내도록 응답 프로토콜을 정의한다.
//   - 프로토콜이란? 클라이언트/서버 간의 데이터를 주고 받는 형식이다.
//=> 클라이언트 연결이 끊어지면 다음 클라이언트와 연결하는 것을 반복한다.
//=> 클라이언트가 접속하거나 연결을 끊으면 로그를 남긴다.
//=> 다중 클라이언트의 동시 접속을 처리한다.
//=> ClientHandler 클래스를 ServerApp의 스태틱 중첩 클래스로 선언한다.
//
public class ServerApp02 {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(8888)) {
      System.out.println("서버 실행 중...");

      while (true) {
        //        ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
        //        Thread t = new Thread(clientHandler);
        //        t.start();
        // 스태틱 중첩 클래스 사용
        new Thread(new ClientHandler(serverSocket.accept())).start();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //클라이언트 요청을 처리하는 일을 한다.
  //- 다른 클래스가 사용할 일이 없기 때문에 private 으로 선언한다.
  //- 바깥 클래스의 특정 인스턴스 멤버를 사용할 일이 없기 때문에
  //  static nested class 로 선언한다.
  //
  private static class ClientHandler implements Runnable {
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
}