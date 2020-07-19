package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardUpdateServlet {

  PhotoBoardService photoBoardService;

  public PhotoBoardUpdateServlet(PhotoBoardService photoBoardService) {
    this.photoBoardService = photoBoardService;
  }

  @RequestMapping("/photoboard/update")
  public void service(Map<String, String> params, PrintStream out) throws Exception {

    int no = Integer.parseInt(params.get("no"));
    PhotoBoard photoBoard = photoBoardService.get(no);
    photoBoard.setTitle(params.get("title"));

    ArrayList<PhotoFile> photoFiles = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      String filepath = params.get("photo" + i);
      if (filepath.length() > 0) {
        photoFiles.add(new PhotoFile().setFilepath(filepath));
      }
    }

    if (photoFiles.size() > 0) {
      photoBoard.setFiles(photoFiles);
    } else {
      photoBoard.setFiles(null);
    }

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<meta http-equiv='refresh' content='2;url=/photoboard/list?lessonNo=%d'>", //
        photoBoard.getLesson().getNo());
    out.println("<title>사진 변경</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>사진 변경 결과</h1>");

    try {
      photoBoardService.update(photoBoard);
      out.println("<p>사진을 변경했습니다.</p>");
    } catch (Exception e) {
      out.println("<p>해당 사진 게시물이 존재하지 않습니다.</p>");
    }

    out.println("</body>");
    out.println("</html>");
  }
}
