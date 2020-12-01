package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@WebServlet("/task/add")
public class TaskAddServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>작업등록</title></head>");
    out.println("<body>");

    try {
      out.println("<h1>작업 등록</h1>");

      Task task = new Task();
      task.setProjectNo(Integer.parseInt(request.getParameter("projectNo")));
      task.setContent(request.getParameter("content"));
      task.setDeadline(Date.valueOf(request.getParameter("deadline")));
      task.setStatus(Integer.parseInt(request.getParameter("status")));
      task.setOwner(
          new Member()
          .setNo(Integer.parseInt(request.getParameter("owner")))
          );

      taskService.add(task);

      out.println("<p>작업을 등록했습니다.</p>");

      // 응답 헤더에 refresh 정보를 등록한다.
      response.setHeader("Refresh",
          "1;url=../project/detail?no=" + request.getParameter("projectNo"));

    } catch (Exception e) {
      out.println("<h2>작업 처리 중 오류 발생!</h2>");
      out.printf("<pre>%s</pre>\n", e.getMessage());

      StringWriter errOut = new StringWriter();
      e.printStackTrace(new PrintWriter(errOut));
      out.println("<h3>상세 오류 내용</h3>");
      out.printf("<pre>%s</pre>\n", errOut.toString());
    }

    out.println("</body>");
    out.println("</html>");
  }
}
