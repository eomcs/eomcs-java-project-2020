package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Task;

public interface TaskService {
  int delete(int no) throws Exception;
  int add(Task task) throws Exception;
  List<Task> list() throws Exception;
  List<Task> listByProject(int projectNo) throws Exception;
  Task get(int no) throws Exception;
  int update(Task task) throws Exception;
}
