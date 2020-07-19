package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import com.eomcs.lms.domain.Board;

public class BoardDetailServlet implements Servlet {

  List<Board> boards;

  public BoardDetailServlet(List<Board> boards) {
    this.boards = boards;
  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();

    Board board = null;
    for (Board b : boards) {
      if (b.getNo() == no) {
        board = b;
        break;
      }
    }

    if (board != null) {
      out.writeUTF("OK");
      out.writeObject(board);

    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 게시물이 없습니다.");
    }
  }
}
