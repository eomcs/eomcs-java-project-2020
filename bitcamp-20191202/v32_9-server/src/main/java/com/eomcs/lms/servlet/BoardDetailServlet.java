package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.eomcs.lms.dao.json.BoardJsonFileDao;
import com.eomcs.lms.domain.Board;

public class BoardDetailServlet implements Servlet {

  BoardJsonFileDao boardDao;

  public BoardDetailServlet(BoardJsonFileDao boardDao) {
    this.boardDao = boardDao;
  }


  @Override
  public void service(ObjectInputStream in, ObjectOutputStream out) throws Exception {
    int no = in.readInt();

    Board board = boardDao.findByNo(no);

    if (board != null) {
      out.writeUTF("OK");
      out.writeObject(board);

    } else {
      out.writeUTF("FAIL");
      out.writeUTF("해당 번호의 게시물이 없습니다.");
    }
  }
}
