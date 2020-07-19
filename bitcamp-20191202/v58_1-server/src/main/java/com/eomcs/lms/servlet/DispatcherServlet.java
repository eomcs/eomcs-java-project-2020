package com.eomcs.lms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/app/*")
@MultipartConfig(maxFileSize = 10000000)
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // GET,POST 요청 모두를 처리해야 한다.
  @Override
  protected void service(//
      HttpServletRequest request, //
      HttpServletResponse response) throws ServletException, IOException {

    // 서블릿 경로(/app) 다음에 오는 자원의 경로를 알아낸다.
    String pathInfo = request.getPathInfo();

    // 해당 서블릿을 실행한다.
    response.setContentType("text/html;charset=UTF-8");

    // 인클루딩 서블릿에서 쿠키 정보를 담을 수 있도록 리스트 객체를 준비한다.
    ArrayList<Cookie> cookies = new ArrayList<>();
    request.setAttribute("cookies", cookies);

    // 클라이언트가 요청한 서블릿을 실행한다.
    request.getRequestDispatcher(pathInfo).include(request, response);

    // 인클루딩 서블릿을 실행한 후에 쿠키가 있는지 조사해서 있다면
    // 응답헤더에 쿠기를 추가한다.
    if (cookies.size() > 0) {
      for (Cookie cookie : cookies) {
        response.addCookie(cookie);
      }
    }

    // 서블릿을 실행한 후 그 결과에 따라
    // JSP를 실행할지 Error 페이지를 출력할 지 결정한다.
    if (request.getAttribute("error") != null) {
      Exception error = (Exception) request.getAttribute("error");
      StringWriter out = new StringWriter();
      error.printStackTrace(new PrintWriter(out));
      request.setAttribute("errorDetail", out.toString());
      request.getRequestDispatcher("/error.jsp").forward(request, response);
      return;
    }

    // 페이지 컨트롤러가 refresh URL을 설정했다면,
    // 응답헤더에 추가한다.
    String refreshUrl = (String) request.getAttribute("refreshUrl");
    if (refreshUrl != null) {
      response.setHeader("Refresh", refreshUrl);
    }

    // 서블릿을 정상적으로 실행했다면 서블릿이 알려준 JSP를 실행한다.
    String viewUrl = (String) request.getAttribute("viewUrl");
    if (viewUrl.startsWith("redirect:")) {
      response.sendRedirect(viewUrl.substring(9));
    } else {
      request.getRequestDispatcher(viewUrl).include(request, response);
    }
  }
}
