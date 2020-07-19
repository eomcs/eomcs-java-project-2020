package com.eomcs.lms.servlet;

import java.io.PrintStream;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.eomcs.util.RequestMapping;

@Component
public class MemberAddFormServlet {

  @RequestMapping("/member/addForm")
  public void service(Map<String, String> params, PrintStream out) throws Exception {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원 입력</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 입력</h1>");
    out.println("<form action='/member/add'>");
    out.println("이름: <input name='name' type='text'><br>");
    out.println("이메일: <input name='email' type='email'><br>");
    out.println("암호: <input name='password' type='password'><br>");
    out.println("사진: <input name='photo' type='text'><br>");
    out.println("전화: <input name='tel' type='tel'><br>");
    out.println("<button>제출</button>");
    out.println("</form>");
    out.println("</body>");
    out.println("</html>");
  }
}
