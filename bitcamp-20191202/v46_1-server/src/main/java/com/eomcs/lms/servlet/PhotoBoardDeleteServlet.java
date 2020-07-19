package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Scanner;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.Component;
import com.eomcs.util.Prompt;

@Component("/photoboard/delete")
public class PhotoBoardDeleteServlet implements Servlet {

  PhotoBoardService photoBoardService;

  public PhotoBoardDeleteServlet(PhotoBoardService photoBoardService) {
    this.photoBoardService = photoBoardService;
  }

  @Override
  public void service(Scanner in, PrintStream out) throws Exception {
    int no = Prompt.getInt(in, out, "번호? ");
    photoBoardService.delete(no);
    out.println("사진 게시글을 삭제했습니다.");
  }
}
