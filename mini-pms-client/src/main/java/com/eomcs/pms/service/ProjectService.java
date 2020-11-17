package com.eomcs.pms.service;

import com.eomcs.pms.domain.Project;

public interface ProjectService {
  int delete(int no) throws Exception;
  int add(Project project) throws Exception;
}
