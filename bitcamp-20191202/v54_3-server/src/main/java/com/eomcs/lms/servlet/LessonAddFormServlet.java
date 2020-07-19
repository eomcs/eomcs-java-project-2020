package com.eomcs.lms.servlet;

import java.io.PrintWriter;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.util.RequestMapping;

@Component
public class LessonAddFormServlet {

  @RequestMapping("/lesson/addForm")
  public void service(Map<String, String> params, PrintWriter out) throws Exception {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>강의 입력</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>강의 입력</h1>");
    out.println("<form action='/lesson/add'>");
    out.println("강의명: <input name='title' type='text'><br>");
    out.println("내용:<br>");
    out.println("<textarea name='description' rows='5' cols='60'></textarea><br>");
    out.println("강의 시작일: <input name='startDate' type='date'><br>");
    out.println("강의 종료일: <input name='endDate' type='date'><br>");
    out.println("총 강의시간: <input name='totalHours' type='number'><br>");
    out.println("일 강의시간: <input name='dayHours' type='number'><br>");
    out.println("<button>제출</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }
}
