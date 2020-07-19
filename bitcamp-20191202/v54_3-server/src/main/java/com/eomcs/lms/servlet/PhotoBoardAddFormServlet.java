package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.service.LessonService;
import com.eomcs.util.RequestMapping;

@Component
public class PhotoBoardAddFormServlet {

  LessonService lessonService;

  public PhotoBoardAddFormServlet(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  @RequestMapping("/photoboard/addForm")
  public void service(Map<String, String> params, PrintWriter out) throws Exception {
    int lessonNo = Integer.parseInt(params.get("lessonNo"));
    Lesson lesson = lessonService.get(lessonNo);

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>사진 입력</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>사진 입력</h1>");
    out.println("<form action='/photoboard/add'>");
    out.printf("강의번호: <input name='lessonNo' type='text' value='%d' readonly><br>\n", //
        lesson.getNo());
    out.printf("강의명: %s<br>\n", lesson.getTitle());
    out.println("내용:<br>");
    out.println("<textarea name='title' rows='5' cols='60'></textarea><br>");
    out.println("<hr>");
    out.println("사진: <input name='photo1' type='file'><br>");
    out.println("사진: <input name='photo2' type='file'><br>");
    out.println("사진: <input name='photo3' type='file'><br>");
    out.println("사진: <input name='photo4' type='file'><br>");
    out.println("사진: <input name='photo5' type='file'><br>");
    out.println("<button>제출</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }
}
