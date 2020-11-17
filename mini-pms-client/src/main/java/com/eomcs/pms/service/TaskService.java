package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Task;

public interface TaskService {
  List<Task> listByProject(int projectNo) throws Exception;
}
