package com.eomcs.lms.web;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.LessonService;
import com.eomcs.lms.service.PhotoBoardService;

@Controller
@RequestMapping("/photoboard")
public class PhotoBoardController {

  @Autowired
  ServletContext servletContext;

  @Autowired
  PhotoBoardService photoBoardService;

  @Autowired
  LessonService lessonService;

  @GetMapping("form")
  public void form(int lessonNo, Model model) throws Exception {
    model.addAttribute("lesson", lessonService.get(lessonNo));
  }

  @PostMapping("add")
  public String add(//
      int lessonNo, //
      String title, //
      MultipartFile[] photoFiles) throws Exception {
    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      throw new Exception("수업 번호가 유효하지 않습니다.");
    }

    PhotoBoard photoBoard = new PhotoBoard();
    photoBoard.setTitle(title);
    photoBoard.setLesson(lesson);

    ArrayList<PhotoFile> photos = new ArrayList<>();
    String dirPath = servletContext.getRealPath("/upload/photoboard");
    for (MultipartFile photoFile : photoFiles) {
      if (photoFile.getSize() <= 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      photoFile.transferTo(new File(dirPath + "/" + filename));
      photos.add(new PhotoFile().setFilepath(filename));
    }

    if (photos.size() == 0) {
      throw new Exception("최소 한 개의 사진 파일을 등록해야 합니다.");
    }

    photoBoard.setFiles(photos);
    photoBoardService.add(photoBoard);

    return "redirect:list?lessonNo=" + lessonNo;

  }

  @GetMapping("delete")
  public String delete(int no, int lessonNo) throws Exception {
    photoBoardService.delete(no);
    return "redirect:list?lessonNo=" + lessonNo;
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {
    model.addAttribute("photoBoard", photoBoardService.get(no));
  }

  @GetMapping("list")
  public void list(int lessonNo, Model model) throws Exception {
    Lesson lesson = lessonService.get(lessonNo);
    if (lesson == null) {
      throw new Exception("수업 번호가 유효하지 않습니다.");
    }
    model.addAttribute("lesson", lesson);
    model.addAttribute("list", photoBoardService.listLessonPhoto(lessonNo));
  }

  @PostMapping("update")
  public String update(//
      int no, //
      String title, //
      MultipartFile[] photoFiles) throws Exception {

    PhotoBoard photoBoard = photoBoardService.get(no);
    photoBoard.setTitle(title);

    ArrayList<PhotoFile> photos = new ArrayList<>();
    String dirPath = servletContext.getRealPath("/upload/photoboard");
    for (MultipartFile photoFile : photoFiles) {
      if (photoFile.getSize() <= 0) {
        continue;
      }
      String filename = UUID.randomUUID().toString();
      photoFile.transferTo(new File(dirPath + "/" + filename));
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
