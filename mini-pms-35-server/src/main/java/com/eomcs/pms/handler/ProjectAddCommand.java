package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectAddCommand implements Command {

  List<Project> projectList;
  MemberListCommand memberListCommand;

  public ProjectAddCommand(List<Project> list, MemberListCommand memberListCommand) {
    this.projectList = list;
    this.memberListCommand = memberListCommand;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[프로젝트 등록]");

      Project project = new Project();
      project.setNo(Prompt.inputInt("번호? ", out, in));
      project.setTitle(Prompt.inputString("프로젝트명? ", out, in));
      project.setContent(Prompt.inputString("내용? ", out, in));
      project.setStartDate(Prompt.inputDate("시작일? ", out, in));
      project.setEndDate(Prompt.inputDate("종료일? ", out, in));

      while (true) {
        String name = Prompt.inputString("만든이?(취소: 빈 문자열) ", out, in);

        if (name.length() == 0) {
          out.println("프로젝트 등록을 취소합니다.");
          return;
        } else if (memberListCommand.findByName(name) != null) {
          project.setOwner(name);
          break;
        }

        out.println("등록된 회원이 아닙니다.");
      }

      StringBuilder members = new StringBuilder();
      while (true) {
        String name = Prompt.inputString("팀원?(완료: 빈 문자열) ", out, in);

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
      project.setMembers(members.toString());

      projectList.add(project);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
