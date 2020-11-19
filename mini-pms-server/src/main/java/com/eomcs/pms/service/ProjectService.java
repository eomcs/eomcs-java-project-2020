package com.eomcs.pms.service;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Project;

public interface ProjectService {
  int delete(int no) throws Exception;
  int add(Project project) throws Exception;
  List<Project> list() throws Exception;
  List<Project> list(String keyword) throws Exception;
  List<Project> list(Map<String,Object> keywords) throws Exception;
  Project get(int no) throws Exception;
  int update(Project project) throws Exception;
}
