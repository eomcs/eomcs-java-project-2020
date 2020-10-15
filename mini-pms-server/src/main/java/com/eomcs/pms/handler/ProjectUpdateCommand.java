package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectUpdateCommand implements Command {

  List<Project> projectList;
  MemberListCommand memberListCommand;

  public ProjectUpdateCommand(List<Project> list, MemberListCommand memberListCommand) {
    this.projectList = list;
    this.memberListCommand = memberListCommand;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[프로젝트 변경]");
      int no = Prompt.inputInt("번호? ", out, in);
      Project project = findByNo(no);

      if (project == null) {
        out.println("해당 번호의 프로젝트가 없습니다.");
        return;
      }

      String title = Prompt.inputString(
          String.format("프로젝트명(%s)? ", project.getTitle()), out, in);
      String content = Prompt.inputString(
          String.format("내용(%s)? ", project.getContent()), out, in);
      Date startDate = Prompt.inputDate(
          String.format("시작일(%s)? ", project.getStartDate()), out, in);
      Date endDate = Prompt.inputDate(
          String.format("종료일(%s)? ", project.getEndDate()), out, in);

      String owner = null;
      while (true) {
        String name = Prompt.inputString(
            String.format("만든이(%s)?(취소: 빈 문자열) ", project.getOwner()), out, in);
        if (name.length() == 0) {
          out.println("프로젝트 등록을 취소합니다.");
          return;
        } else if (memberListCommand.findByName(name) != null) {
          owner = name;
          break;
        }
        out.println("등록된 회원이 아닙니다.");
      }

      StringBuilder members = new StringBuilder();
      while (true) {
        String name = Prompt.inputString(
            String.format("팀원(%s)?(완료: 빈 문자열) ", project.getMembers()), out, in);
        if (name.length() == 0) {
          break;
        } else if (memberListCommand.findByName(name) != null) {
          if (members.length() > 0) {
            members.append(",");
          }
          members.append(name);
        } else {
          out.println("등록된 회원이 아닙니다.");
        }
      }

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("프로젝트 변경을 취소하였습니다.");
        return;
      }

      project.setTitle(title);
      project.setContent(content);
      project.setStartDate(startDate);
      project.setEndDate(endDate);
      project.setOwner(owner);
      project.setMembers(members.toString());

      out.println("프로젝트를 변경하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }

  private Project findByNo(int no) {
    for (int i = 0; i < projectList.size(); i++) {
      Project project = projectList.get(i);
      if (project.getNo() == no) {
        return project;
      }
    }
    return null;
  }
}
