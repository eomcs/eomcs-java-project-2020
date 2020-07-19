package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.eomcs.lms.dao.BoardDao;
import com.eomcs.lms.domain.Board;

public class BoardUpdateServlet implements Servlet {

  // DAO 클래스를 구체적으로 지정하기 보다는
  // 인터페이스를 지정함으로써
  // 향후 다른 구현체로 교체하기 쉽도록 한다.
  //
  BoardDao boardDao;

  public BoardUpdateServlet(BoardDao boardDao) {
    this.boardDao = boardDao;
  }


  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    Board board = (Board) in.readObject();

    if (boardDao.update(board) > 0) { // 변경했다면,
      out.writeUTF("OK");

    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 게시물이 없습니다.");
    }
  }
}
