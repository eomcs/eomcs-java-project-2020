package com.eomcs.pms.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/project/list")
public class ProjectListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService =
        (ProjectService) ctx.getAttribute("projectService");

    response.setContentType("text/html;charset=UTF-8");

    try {
      List<Project> list = null;

      String keyword = request.getParameter("keyword");
      String keywordTitle = request.getParameter("keywordTitle");
      String keywordOwner = request.getParameter("keywordOwner");
      String keywordMember = request.getParameter("keywordMember");

      if (keyword != null) {
        list = projectService.list(keyword);

      } else if (keywordTitle != null) {
        HashMap<String,Object> keywordMap = new HashMap<>();
        keywordMap.put("title", keywordTitle);
        keywordMap.put("owner", keywordOwner);
        keywordMap.put("member", keywordMember);

        list = projectService.list(keywordMap);

      } else {
        list = projectService.list();
      }

      request.setAttribute("list", list);
      request.getRequestDispatcher("/project/list.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}
