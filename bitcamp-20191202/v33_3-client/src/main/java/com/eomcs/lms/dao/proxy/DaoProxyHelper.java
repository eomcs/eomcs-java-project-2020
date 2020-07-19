package com.eomcs.lms.dao.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// 역할:
// - DaoProxy가 서버와 통신할 때 필요한 준비 작업을 수행한다.
// - 즉 서버와 연결한 후 입,출력 스트림을 준비하는 일을 한다.
// - 그런 후 파라미터로 넘겨 받은 작업 객체를 실행한다.
//
public class DaoProxyHelper {
  String host;
  int port;

  public DaoProxyHelper(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public Object request(Worker worker) throws Exception {
    try (Socket socket = new Socket(host, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
      return worker.execute(in, out);
    }
  }
}
