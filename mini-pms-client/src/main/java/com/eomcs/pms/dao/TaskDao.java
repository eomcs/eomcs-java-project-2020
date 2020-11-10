package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Task;

public interface TaskDao {
  int insert(Task task) throws Exception;
  int delete(int no) throws Exception;
  int deleteByProjectNo(int projectNo) throws Exception;
  Task findByNo(int no) throws Exception;
  List<Task> findAll() throws Exception;
  int update(Task task) throws Exception;
}
