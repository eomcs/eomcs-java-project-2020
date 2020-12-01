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
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@WebServlet("/project/detail")
public class ProjectDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService = (ProjectService) ctx.getAttribute("projectService");
    TaskService taskService = (TaskService) ctx.getAttribute("taskService");
    MemberService memberService = (MemberService) ctx.getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트정보</title></head>");
    out.println("<body>");

    try {
      out.println("<h1>프로젝트 정보</h1>");

      int no = Integer.parseInt(request.getParameter("no"));

      Project project = projectService.get(no);

      if (project == null) {
        out.println("<p>해당 번호의 프로젝트가 없습니다.</p>");

      } else {
        out.println("<form action='update' method='post'>");
        out.printf("<input type='hidden' name='no' value='%d'>\n",
            project.getNo());
        out.printf("프로젝트명: <input type='text' name='title' value='%s'><br>\n",
            project.getTitle());
        out.printf("내용: <textarea name='content' rows='10' cols='70'>%s</textarea><br>\n",
            project.getContent());
        out.printf("기간: <input type='date' name='startDate' value='%s'> ~ "
            + "<input type='date' name='endDate' value='%s'><br>\n",
            project.getStartDate(),
            project.getEndDate());
        out.printf("관리자: %s<br>\n", project.getOwner().getName());
        out.println("팀원: * 는 비활성 상태의 멤버<br>");

        List<Member> members = project.getMembers();

        for (Member m : memberService.list()) {
          out.printf("<input type='checkbox' name='members' value='%d' %s>%s%s, \n",
              m.getNo(),
              checkMember(members, m),
              m.getName(),
              inactiveMember(members, m));
        }
        out.println("<br>");
        out.println("<button>변경</button>");
        out.printf("<a href='delete?no=%d'>[삭제]</a>\n", project.getNo());
        out.println("<a href='list'>[목록]</a>");
        out.println("</form>");
        out.println("<hr>");

        out.println("작업:<br>");

        out.printf("<a href='../task/addForm?projectNo=%d'>새 작업</a><br>\n",
            project.getNo());

        List<Task> tasks = taskService.listByProject(no);
        out.println("<table border='1'>");
        out.println("<thead><tr>"
            + "<th>번호</th>"
            + "<th>작업</th>"
            + "<th>마감일</th>"
            + "<th>작업자</th>"
            + "<th>상태</th>"
            + "<th></th>"
            + "</tr></thead>");

        out.println("<tbody>");

        for (Task task : tasks) {
          String stateLabel = null;
          switch (task.getStatus()) {
            case 1:
              stateLabel = "진행중";
              break;
            case 2:
              stateLabel = "완료";
              break;
            default:
              stateLabel = "신규";
          }
          out.printf("<tr>"
              + "<td>%d</td>"
              + "<td><a href='../task/detail?no=%1$d'>%s</a></td>"
              + "<td>%s</td>"
              + "<td>%s</td>"
              + "<td>%s</td>"
              + "<td><a href='../task/delete?no=%1$d&projectNo=%d'>[삭제]</a></td>"
              + "</tr>\n",
              task.getNo(),
              task.getContent(),
              task.getDeadline(),
              task.getOwner().getName(),
              stateLabel,
              project.getNo());
        }
        out.println("</tbody>");
        out.println("</table>");
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

  private String checkMember(List<Member> projectMembers, Member member) {
    for (Member projectMember : projectMembers) {
      if (member.getNo() == projectMember.getNo() && projectMember.getState() == 1) {
        return "checked";
      }
    }
    return "";
  }

  private String inactiveMember(List<Member> projectMembers, Member member) {
    for (Member projectMember : projectMembers) {
      if (member.getNo() == projectMember.getNo() && projectMember.getState() == 0) {
        return "*";
      }
    }
    return "";
  }
}
