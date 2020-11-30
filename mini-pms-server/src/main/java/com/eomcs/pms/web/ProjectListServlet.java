package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
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
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트목록</title></head>");
    out.println("<body>");

    try {
      out.println("<h1>프로젝트 목록</h1>");

      out.println("<a href='form'>새 프로젝트</a><br>");

      List<Project> list = projectService.list();

      out.println("<table border='1'>");
      out.println("<thead><tr>"
          + "<th>번호</th>"
          + "<th>프로젝트명</th>"
          + "<th>시작일 ~ 종료일</th>"
          + "<th>관리자</th>"
          + "<th>팀원</th>"
          + "</tr></thead>");

      out.println("<tbody>");

      for (Project project : list) {
        StringBuilder members = new StringBuilder();
        for (Member member : project.getMembers()) {
          if (members.length() > 0) {
            members.append(",");
          }
          members.append(member.getName());
        }

        out.printf("<tr>"
            + "<td>%d</td>"
            + "<td><a href='detail?no=%1$d'>%s</a></td>"
            + "<td>%s ~ %s</td>"
            + "<td>%s</td>"
            + "<td>%s</td>"
            + "</tr>\n",
            project.getNo(),
            project.getTitle(),
            project.getStartDate(),
            project.getEndDate(),
            project.getOwner().getName(),
            members.toString());
      }

      out.println("</tbody>");
      out.println("</table>");

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
