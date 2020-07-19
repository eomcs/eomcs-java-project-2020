package com.eomcs.lms.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardListController {

  @Autowired
  PhotoBoardService photoBoardService;

  @Autowired
  LessonService lessonService;

  @RequestMapping("/photoboard/list")
  public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      throw new Exception("수업 번호가 유효하지 않습니다.");
    }
    request.setAttribute("lesson", lesson);

    List<PhotoBoard> photoBoards = photoBoardService.listLessonPhoto(lessonNo);
    request.setAttribute("list", photoBoards);
    return "/photoboard/list.jsp";
  }
}
