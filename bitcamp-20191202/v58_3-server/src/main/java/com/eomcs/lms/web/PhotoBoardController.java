package com.eomcs.lms.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardController {

  @Autowired
  PhotoBoardService photoBoardService;

  @Autowired
  LessonService lessonService;

  @RequestMapping("/photoboard/add")
  public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
      Lesson lesson = lessonService.get(lessonNo);
      request.setAttribute("lesson", lesson);
      return "/photoboard/form.jsp";
    }
    int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));

    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      throw new Exception("수업 번호가 유효하지 않습니다.");
    }

    PhotoBoard photoBoard = new PhotoBoard();
    photoBoard.setTitle(request.getParameter("title"));
    photoBoard.setLesson(lesson);

    ArrayList<PhotoFile> photoFiles = new ArrayList<>();
    Collection<Part> parts = request.getParts();
    String dirPath = request.getServletContext().getRealPath("/upload/photoboard");
    for (Part part : parts) {
      if (!part.getName().equals("photo") || //
          part.getSize() <= 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      part.write(dirPath + "/" + filename);
      photoFiles.add(new PhotoFile().setFilepath(filename));
    }

    if (photoFiles.size() == 0) {
      throw new Exception("최소 한 개의 사진 파일을 등록해야 합니다.");
    }

    photoBoard.setFiles(photoFiles);
    photoBoardService.add(photoBoard);

    return "redirect:list?lessonNo=" + lessonNo;

  }

  @RequestMapping("/photoboard/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
    int no = Integer.parseInt(request.getParameter("no"));
    photoBoardService.delete(no);
    return "redirect:list?lessonNo=" + lessonNo;
  }

  @RequestMapping("/photoboard/detail")
  public String detail(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    PhotoBoard photoBoard = photoBoardService.get(no);
    request.setAttribute("photoBoard", photoBoard);
    return "/photoboard/detail.jsp";
  }

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

  @RequestMapping("/photoboard/update")
  public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int no = Integer.parseInt(request.getParameter("no"));
    PhotoBoard photoBoard = photoBoardService.get(no);
    photoBoard.setTitle(request.getParameter("title"));

    ArrayList<PhotoFile> photoFiles = new ArrayList<>();
    Collection<Part> parts = request.getParts();
    String dirPath = request.getServletContext().getRealPath("/upload/photoboard");
    for (Part part : parts) {
      if (!part.getName().equals("photo") || //
          part.getSize() <= 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      part.write(dirPath + "/" + filename);
      photoFiles.add(new PhotoFile().setFilepath(filename));
    }

    if (photoFiles.size() > 0) {
      photoBoard.setFiles(photoFiles);
    } else {
      photoBoard.setFiles(null);
    }

    int lessonNo = photoBoard.getLesson().getNo();
    photoBoardService.update(photoBoard);
    return "redirect:list?lessonNo=" + lessonNo;
  }
}
