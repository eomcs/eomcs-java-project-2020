package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@WebServlet("/task/detail")
public class TaskDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService = (ProjectService) ctx.getAttribute("projectService");
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    response.setContentType("text/html;charset=UTF-8");
    try {
      int no = Integer.parseInt(request.getParameter("no"));
      Task task = taskService.get(no);
      if (task == null) {
        throw new Exception("해당 작업이 없습니다.");
      }
      request.setAttribute("task", task);
      request.setAttribute("project", projectService.get(task.getProjectNo()));
      request.getRequestDispatcher("/task/detail.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
