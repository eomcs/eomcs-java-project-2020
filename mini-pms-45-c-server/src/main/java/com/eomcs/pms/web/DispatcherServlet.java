package com.eomcs.pms.web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 예) 요청 URL => /app/board/list
    //String servletPath = request.getServletPath(); // => /app
    String controllerPath = request.getPathInfo(); // => /board/list
    System.out.println("===> " + controllerPath);
    // 페이지 컨트롤러에게 위임한다.
    RequestDispatcher rd = request.getRequestDispatcher(controllerPath);
    rd.include(request, response);

    Exception exception = (Exception) request.getAttribute("exception");
    if (exception != null) {
      request.getRequestDispatcher("/error.jsp").forward(request, response);
      return;
    }

    String viewName = (String) request.getAttribute("viewName");
    if (viewName != null) {
      request.getRequestDispatcher(viewName).forward(request, response);
      return;
    }

    String redirect = (String) request.getAttribute("redirect");
    if (redirect != null) {
      response.sendRedirect(redirect);
      return;
    }

  }
}
