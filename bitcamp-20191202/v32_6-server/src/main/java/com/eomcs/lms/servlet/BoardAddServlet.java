package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import com.eomcs.lms.domain.Board;

public class BoardAddServlet implements Servlet {

  List<Board> boards;

  public BoardAddServlet(List<Board> boards) {
    this.boards = boards;
  }

  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Board board = (Board) in.readObject();

    int i = 0;
    for (; i < boards.size(); i++) {
      if (boards.get(i).getNo() == board.getNo()) {
        break;
      }
    }

    if (i == boards.size()) { // 같은 번호의 게시물이 없다면,
      boards.add(board); // 새 게시물을 등록한다.
      out.writeUTF("OK");

    } else {
      out.writeUTF("FAIL");
      out.writeUTF("같은 번호의 게시물이 있습니다.");
    }
  }
}
