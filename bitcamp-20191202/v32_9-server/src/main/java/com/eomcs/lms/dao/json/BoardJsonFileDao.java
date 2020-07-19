package com.eomcs.lms.dao.json;

import java.util.List;
import com.eomcs.lms.domain.Board;

public class BoardJsonFileDao extends AbstractJsonFileDao<Board> {

  public BoardJsonFileDao(String filename) {
    super(filename);
  }

  // 서블릿 객체들이 데이터를 다룰 때 사용할 메서드를 정의한다.
  public int insert(Board board) throws Exception {

    if (indexOf(board.getNo()) > -1) { // 같은 번호의 게시물이 있다면,
      return 0;
    }

    list.add(board); // 새 게시물을 등록한다.
    saveData();
    return 1;
  }

  public List<Board> findAll() throws Exception {
    return list;
  }

  public Board findByNo(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  public int update(Board board) throws Exception {
    int index = indexOf(board.getNo());

    if (index == -1) {
      return 0;
    }

    list.set(index, board); // 기존 객체를 파라미터로 받은 객체로 바꾼다.
    saveData();
    return 1;
  }

  public int delete(int no) throws Exception {
    int index = indexOf(no);
    if (index == -1) {
      return 0;
    }

    list.remove(index);
    saveData();
    return 1;
  }

  @Override
  protected <K> int indexOf(K key) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == (int) key) { // K가 어떤 타입인지 형변환해서 사용하라.
        return i;
      }
    }
    return -1;
  }
}


