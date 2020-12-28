package com.eomcs.pms.dao;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Board;

public interface BoardDao {
  int insert(Board board) throws Exception;
  int delete(int no) throws Exception;
  Board findByNo(int no) throws Exception;
  List<Board> findAll(Map<String,Object> params) throws Exception;
  int count(String keyword) throws Exception;
  int update(Board board) throws Exception;
  int updateViewCount(int no) throws Exception;
}








