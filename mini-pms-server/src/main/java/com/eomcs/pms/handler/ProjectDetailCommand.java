package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectDetailCommand implements Command {

  List<Project> projectList;

  public ProjectDetailCommand(List<Project> list) {
    this.projectList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[프로젝트 상세보기]");
      int no = Prompt.inputInt("번호? ", out, in);
      Project project = findByNo(no);

      if (project == null) {
        out.println("해당 번호의 프로젝트가 없습니다.");
        return;
      }

      out.printf("프로젝트명: %s\n", project.getTitle());
      out.printf("내용: %s\n", project.getContent());
      out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
      out.printf("만든이: %s\n", project.getOwner());
      out.printf("팀원: %s\n", project.getMembers());

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
