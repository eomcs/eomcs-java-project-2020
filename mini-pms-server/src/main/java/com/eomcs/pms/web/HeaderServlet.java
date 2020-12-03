package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    String contextPath = getServletContext().getContextPath();

    out.println("<style>");
    out.println("  #menubar {");
    out.println("    background-color:gray;");
    out.println("    color:white;");
    out.println("    height: 30px;");
    out.println("    padding: 5px;");
    out.println("  }");
    out.println("  #menubar a {");
    out.println("    color:white; ");
    out.println("    text-decoration: none;");
    out.println("  }");
    out.println("  #menubar a:visited {");
    out.println("    color:white; ");
    out.println("  }");
    out.println("  #menubar a:hover {");
    out.println("    text-decoration: underline;");
    out.println("  }");
    out.println("</style>");
    out.println("<div id='menubar'>");
    out.printf("  <a href='%s/board/list'>게시글</a>\n", contextPath);
    out.printf("  <a href='%s/member/list'>회원</a>\n", contextPath);
    out.printf("  <a href='%s/project/list'>프로젝트</a>\n", contextPath);
    out.printf("  <a href='%s/auth/login'>로그인</a>\n", contextPath);
    out.printf("  <a href='%s/auth/logout'>로그아웃</a>\n", contextPath);
    out.println("</div>");
  }
}
