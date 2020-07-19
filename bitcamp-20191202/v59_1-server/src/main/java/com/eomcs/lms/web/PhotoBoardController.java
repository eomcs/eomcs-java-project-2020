package com.eomcs.lms.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;

@Controller
public class PhotoBoardController {

  @Autowired
  PhotoBoardService photoBoardService;

  @Autowired
  LessonService lessonService;

  @RequestMapping("/photoboard/form")
  public String form(int lessonNo, Map<String, Object> model) throws Exception {
    model.put("lesson", lessonService.get(lessonNo));
    return "/photoboard/form.jsp";
  }

  @RequestMapping("/photoboard/add")
  public String add(//
      int lessonNo, String title, Part[] photoFiles, //
      HttpServletRequest request) throws Exception {
    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      throw new Exception("수업 번호가 유효하지 않습니다.");
    }

    PhotoBoard photoBoard = new PhotoBoard();
    photoBoard.setTitle(title);
    photoBoard.setLesson(lesson);

    ArrayList<PhotoFile> photos = new ArrayList<>();
    String dirPath = request.getServletContext().getRealPath("/upload/photoboard");
    for (Part photoFile : photoFiles) {
      if (photoFile.getSize() <= 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      photoFile.write(dirPath + "/" + filename);
      photos.add(new PhotoFile().setFilepath(filename));
    }

    if (photos.size() == 0) {
      throw new Exception("최소 한 개의 사진 파일을 등록해야 합니다.");
    }

    photoBoard.setFiles(photos);
    photoBoardService.add(photoBoard);

    return "redirect:list?lessonNo=" + lessonNo;

  }

  @RequestMapping("/photoboard/delete")
  public String delete(int no, int lessonNo) throws Exception {
    photoBoardService.delete(no);
    return "redirect:list?lessonNo=" + lessonNo;
  }

  @RequestMapping("/photoboard/detail")
  public String detail(int no, Map<String, Object> model) throws Exception {
    model.put("photoBoard", photoBoardService.get(no));
    return "/photoboard/detail.jsp";
  }

  @RequestMapping("/photoboard/list")
  public String list(int lessonNo, Map<String, Object> model) throws Exception {
    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      throw new Exception("수업 번호가 유효하지 않습니다.");
    }
    model.put("lesson", lesson);

    List<PhotoBoard> photoBoards = photoBoardService.listLessonPhoto(lessonNo);
    model.put("list", photoBoards);
    return "/photoboard/list.jsp";
  }

  @RequestMapping("/photoboard/update")
  public String update(//
      int no, String title, Part[] photoFiles, //
      HttpServletRequest request) throws Exception {

    PhotoBoard photoBoard = photoBoardService.get(no);
    photoBoard.setTitle(title);

    ArrayList<PhotoFile> photos = new ArrayList<>();
    String dirPath = request.getServletContext().getRealPath("/upload/photoboard");
    for (Part photoFile : photoFiles) {
      if (photoFile.getSize() <= 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      photoFile.write(dirPath + "/" + filename);
      photos.add(new PhotoFile().setFilepath(filename));
    }

    if (photos.size() > 0) {
      photoBoard.setFiles(photos);
    } else {
      photoBoard.setFiles(null);
    }

    int lessonNo = photoBoard.getLesson().getNo();
    photoBoardService.update(photoBoard);
    return "redirect:list?lessonNo=" + lessonNo;
  }
}
