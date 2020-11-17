package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Project;

public interface ProjectService {
  int delete(int no) throws Exception;
  int add(Project project) throws Exception;
  List<Project> list(String keyword) throws Exception;
}
