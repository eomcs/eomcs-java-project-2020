package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/task/addForm")
public class TaskAddFormServlet extends HttpServlet {
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
    out.println("<title>작업등록</title></head>");
    out.println("<body>");

    try {
      out.println("<h1>작업 등록</h1>");

      // 프로젝트 번호를 가지고 프로젝트 정보를 꺼내온다.
      Project project = projectService.get(
          Integer.parseInt(request.getParameter("projectNo")));

      out.println("<form action='add' method='post'>");
      out.printf("<input type='hidden' name='projectNo' value='%d'>\n",
          project.getNo());
      out.printf("프로젝트명: %s<br>", project.getTitle());
      out.println("작업내용: <textarea name='content' rows='10' cols='60'></textarea><br>");
      out.println("마감일: <input type='date' name='deadline'><br>");
      out.println("담당자: <br>");
      out.println("<p>");

      for (Member m : project.getMembers()) {
        if (m.getState() == 0) continue;

        out.printf("<input type='radio' name='owner' value='%d'>%s, \n",
            m.getNo(),
            m.getName());
      }
      out.println("</p>");

      out.println("작업상태: ");
      out.println("<select name='status'>");
      out.println("  <option value='0'>준비</option>");
      out.println("  <option value='1'>진행중</option>");
      out.println("  <option value='2'>완료</option>");
      out.println("</select><br>");

      out.println("<button>등록</button>");
      out.println("</form>");

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
