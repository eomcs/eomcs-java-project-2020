package com.eomcs.pms.service;

import java.util.HashMap;
import java.util.List;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Task;

public class DefaultTaskService implements TaskService {

  TaskDao taskDao;

  public DefaultTaskService(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> listByProject(int projectNo) throws Exception {
    HashMap<String,Object> map = new HashMap<>();
    map.put("projectNo", projectNo);
    return taskDao.findAll(map);
  }
}




