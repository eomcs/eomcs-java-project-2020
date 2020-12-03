package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;

@WebServlet("/project/detail")
public class ProjectDetailServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ServletContext ctx = request.getServletContext();
    ProjectService projectService = (ProjectService) ctx.getAttribute("projectService");
    MemberService memberService = (MemberService) ctx.getAttribute("memberService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트정보</title></head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    try {
      out.println("<h1>프로젝트 정보</h1>");

      int no = Integer.parseInt(request.getParameter("no"));

      Project project = projectService.get(no);

      if (project == null) {
        throw new Exception("해당 번호의 프로젝트가 없습니다.");
      }

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

      // 프로젝트의 작업 목록을 출력하는 서블릿을 include 한다.
      request.getRequestDispatcher("/task/list").include(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error").forward(request, response);
      return;
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
