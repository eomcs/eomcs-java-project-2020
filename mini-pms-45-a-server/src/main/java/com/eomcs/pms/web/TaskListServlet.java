package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.service.TaskService;

@WebServlet("/task/list")
public class TaskListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    try {
      int projectNo = Integer.parseInt(request.getParameter("no"));
      request.setAttribute("tasks", taskService.listByProject(projectNo));

    } catch (Exception e) {
      request.setAttribute("exception", e);
    }
    request.getRequestDispatcher("/task/list.jsp").include(request, response);
  }
}
