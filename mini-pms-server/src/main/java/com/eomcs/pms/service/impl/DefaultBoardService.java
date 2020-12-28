package com.eomcs.pms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@Service
public class DefaultBoardService implements BoardService {

  BoardDao boardDao;

  public DefaultBoardService(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public int delete(int no) throws Exception {
    return boardDao.delete(no);
  }

  @Override
  public int add(Board board) throws Exception {
    return boardDao.insert(board);
  }

  @Override
  public List<Board> list() throws Exception {
    return boardDao.findAll(null);
  }

  @Override
  public List<Board> list(String keyword, int pageNo, int pageSize) throws Exception {
    Map<String,Object> params = new HashMap<>();
    if (keyword != null) {
      params.put("keyword", keyword);
    }
    params.put("startRowNo", (pageNo - 1) * pageSize);
    params.put("pageSize", pageSize);

    return boardDao.findAll(params);
  }

  @Override
  public int size(String keyword) throws Exception {
    return boardDao.count(keyword);
  }

  @Override
  public Board get(int no) throws Exception {
    Board board = boardDao.findByNo(no);
    if (board != null) {
      boardDao.updateViewCount(no);
    }
    return board;
  }

  @Override
  public int update(Board board) throws Exception {
    return boardDao.update(board);
  }

}
