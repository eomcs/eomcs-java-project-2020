package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@WebServlet("/task/list")
public class TaskListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    PrintWriter out = response.getWriter();

    try {
      int projectNo = Integer.parseInt(request.getParameter("no"));

      List<Task> tasks = taskService.listByProject(projectNo);

      out.printf("<a href='../task/add?projectNo=%d'>새 작업</a><br>\n", projectNo);
      out.println("<table border='1'>");
      out.println("<thead><tr>"
          + "<th>번호</th>"
          + "<th>작업</th>"
          + "<th>마감일</th>"
          + "<th>작업자</th>"
          + "<th>상태</th>"
          + "<th></th>"
          + "</tr></thead>");

      out.println("<tbody>");

      for (Task task : tasks) {
        out.printf("<tr>"
            + "<td>%d</td>"
            + "<td><a href='../task/detail?no=%1$d'>%s</a></td>"
            + "<td>%s</td>"
            + "<td>%s</td>"
            + "<td>%s</td>"
            + "<td><a href='../task/delete?no=%1$d&projectNo=%d'>[삭제]</a></td>"
            + "</tr>\n",
            task.getNo(),
            task.getContent(),
            task.getDeadline(),
            task.getOwner().getName(),
            getStatusLabel(task.getStatus()),
            projectNo);
      }
      out.println("</tbody>");
      out.println("</table>");

    } catch (Exception e) {
      out.println("<p>작업 목록 조회 오류!</p>");
      e.printStackTrace();
      return;
    }
  }

  private String getStatusLabel(int status) {
    switch (status) {
      case 1:
        return "진행중";
      case 2:
        return "완료";
      default:
        return "준비";
    }
  }
}
