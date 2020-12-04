package com.eomcs.pms.web;

import java.io.IOException;
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

@WebServlet("/task/update")
public class TaskUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    try {
      Task task = new Task();
      task.setNo(Integer.parseInt(request.getParameter("no")));
      task.setContent(request.getParameter("content"));
      task.setDeadline(Date.valueOf(request.getParameter("deadline")));
      task.setStatus(Integer.parseInt(request.getParameter("status")));
      task.setOwner(new Member()
          .setNo(Integer.parseInt(request.getParameter("owner"))));

      if (taskService.update(task) == 0) {
        throw new Exception("해당 작업이 존재하지 않습니다.");
      }

      response.sendRedirect("../project/detail?no=" +
          request.getParameter("projectNo"));

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
