package com.eomcs.lms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eomcs.lms.service.PhotoBoardService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardDeleteController {

  @Autowired
  PhotoBoardService photoBoardService;

  @RequestMapping("/photoboard/delete")
  public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int lessonNo = Integer.parseInt(request.getParameter("lessonNo"));
    int no = Integer.parseInt(request.getParameter("no"));
    photoBoardService.delete(no);
    return "redirect:list?lessonNo=" + lessonNo;
  }
}
