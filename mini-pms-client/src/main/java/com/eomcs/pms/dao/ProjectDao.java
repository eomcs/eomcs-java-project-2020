package com.eomcs.pms.dao;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Project;

public interface ProjectDao {
  int insert(Project project) throws Exception;
  int delete(int no) throws Exception;
  Project findByNo(int no) throws Exception;
  List<Project> findAll(String keyword) throws Exception;
  List<Project> findByDetailKeyword(Map<String,Object> keywords) throws Exception;
  int update(Project project) throws Exception;

  int deleteMembers(int projectNo) throws Exception;
  int insertMembers(Project project) throws Exception;
}
