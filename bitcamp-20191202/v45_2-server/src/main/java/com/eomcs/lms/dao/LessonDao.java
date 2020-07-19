package com.eomcs.lms.dao;

import java.util.List;
import java.util.Map;
import com.eomcs.lms.domain.Lesson;

// 데이터를 저장하고 꺼내는 방식(파일, 클라우드저장소, DB 등)에 상관없이
// DAO 사용법을 통일하기 위해
// 메서드 호출 규칙을 정의한다.
//
public interface LessonDao {

  int insert(Lesson lesson) throws Exception;

  List<Lesson> findAll() throws Exception;

  Lesson findByNo(int no) throws Exception;

  int update(Lesson lesson) throws Exception;

  int delete(int no) throws Exception;

  default List<Lesson> findByKeyword(Map<String, Object> params) throws Exception {
    return null;
  }
}


