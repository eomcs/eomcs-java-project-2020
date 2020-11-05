package com.eomcs.pms.handler;

import java.util.Map;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectDetailCommand implements Command {
  ProjectDao projectDao;

  public ProjectDetailCommand(ProjectDao projectDao) {
    this.projectDao = projectDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.inputInt("번호? ");

    try {
      Project project = projectDao.findByNo(no);
      if (project == null) {
        System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
        return;
      }

      System.out.printf("프로젝트명: %s\n", project.getTitle());
      System.out.printf("내용: %s\n", project.getContent());
      System.out.printf("기간: %s ~ %s\n",
          project.getStartDate(),
          project.getEndDate());
      System.out.printf("관리자: %s\n", project.getOwner().getName());
      System.out.print("팀원: ");
      project.getMembers().forEach(
          member -> System.out.print(member.getName() + " "));
      System.out.println();

    } catch (Exception e) {
      System.out.println("프로젝트 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
