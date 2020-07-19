package com.eomcs.lms.service;

import java.util.HashMap;
import java.util.List;
import com.eomcs.lms.domain.Lesson;

public interface LessonService {
  Lesson get(int no) throws Exception;

  int add(Lesson lesson) throws Exception;

  int delete(int no) throws Exception;

  List<Lesson> list() throws Exception;

  List<Lesson> search(HashMap<String, Object> params) throws Exception;

  int update(Lesson lesson) throws Exception;
}
