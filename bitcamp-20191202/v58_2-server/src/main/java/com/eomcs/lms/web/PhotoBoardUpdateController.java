package com.eomcs.lms.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardUpdateController {

  @Autowired
  PhotoBoardService photoBoardService;

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
