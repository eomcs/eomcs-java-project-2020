package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.service.TaskService;

@WebServlet("/task/delete")
public class TaskDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      if (taskService.delete(no) == 0) {
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
