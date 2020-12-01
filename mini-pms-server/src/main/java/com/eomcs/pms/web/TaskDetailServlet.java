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
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>작업정보</title></head>");
    out.println("<body>");

    try {
      out.println("<h1>작업 정보</h1>");

      int no = Integer.parseInt(request.getParameter("no"));

      Task task = taskService.get(no);

      if (task == null) {
        out.println("<p>해당 작업이 없습니다.</p>");

      } else {
        out.println("<form action='update' method='post'>");
        out.printf("<input type='hidden' name='projectNo' value='%d'>\n",
            task.getProjectNo());
        out.printf("<input type='hidden' name='no' value='%d'>\n",
            task.getNo());
        out.printf("작업내용: <textarea name='content' rows='10' cols='70'>%s</textarea><br>\n",
            task.getContent());
        out.printf("마감일: <input type='date' name='deadline' value='%s'><br>\n",
            task.getDeadline());

        Project project = projectService.get(task.getProjectNo());

        out.println("담당자: ");
        out.println("<select name='owner'>");
        for (Member m : project.getMembers()) {
          if (m.getState() == 0) continue;

          out.printf("<option value='%d' %s>%s</option>\n",
              m.getNo(),
              m.getNo() == task.getOwner().getNo() ? "selected" : "",
                  m.getName());
        }
        out.println("</select><br>");

        String[] stateLabels = {"준비", "진행중", "완료"};
        out.println("작업상태: ");
        out.println("<select name='status'>");
        for (int i = 0; i < 3; i++) {
          out.printf("<option value='%d' %s>%s</option>\n",
              i,
              i == task.getStatus() ? "selected" : "",
                  stateLabels[i]);
        }
        out.println("</select><br>");
        out.println("<button>변경</button>");
        out.printf("<a href='delete?no=%d&projectNo=%d'>[삭제]</a>\n",
            task.getNo(),
            project.getNo());
        out.printf("<a href='../project/detail?no=%d'>[목록]</a>\n", project.getNo());
        out.println("</form>");
      }

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
