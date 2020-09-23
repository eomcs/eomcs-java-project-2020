package com.eomcs.pms.handler;

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
  public void execute() {
    System.out.println("[프로젝트 변경]");
    int no = Prompt.inputInt("번호? ");
    Project project = findByNo(no);

    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String title = Prompt.inputString(
        String.format("프로젝트명(%s)? ", project.getTitle()));
    String content = Prompt.inputString(
        String.format("내용(%s)? ", project.getContent()));
    Date startDate = Prompt.inputDate(
        String.format("시작일(%s)? ", project.getStartDate()));
    Date endDate = Prompt.inputDate(
        String.format("종료일(%s)? ", project.getEndDate()));

    String owner = null;
    while (true) {
      String name = Prompt.inputString(
          String.format("만든이(%s)?(취소: 빈 문자열) ", project.getOwner()));
      if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return;
      } else if (memberListCommand.findByName(name) != null) {
        owner = name;
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }

    StringBuilder members = new StringBuilder();
    while (true) {
      String name = Prompt.inputString(
          String.format("팀원(%s)?(완료: 빈 문자열) ", project.getMembers()));
      if (name.length() == 0) {
        break;
      } else if (memberListCommand.findByName(name) != null) {
        if (members.length() > 0) {
          members.append(",");
        }
        members.append(name);
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("프로젝트 변경을 취소하였습니다.");
      return;
    }

    project.setTitle(title);
    project.setContent(content);
    project.setStartDate(startDate);
    project.setEndDate(endDate);
    project.setOwner(owner);
    project.setMembers(members.toString());

    System.out.println("프로젝트를 변경하였습니다.");
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
