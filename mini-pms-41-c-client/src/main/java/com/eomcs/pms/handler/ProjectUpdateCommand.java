package com.eomcs.pms.handler;

import java.util.Map;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectUpdateCommand implements Command {

  ProjectDao projectDao;
  MemberDao memberDao;

  public ProjectUpdateCommand(ProjectDao projectDao, MemberDao memberDao) {
    this.projectDao = projectDao;
    this.memberDao = memberDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[프로젝트 변경]");
    int no = Prompt.inputInt("번호? ");

    try {
      Project project = projectDao.findByNo(no);
      if (project == null) {
        System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
        return;
      }

      project.setTitle(Prompt.inputString(String.format(
          "프로젝트명(%s)? ", project.getTitle())));
      project.setContent(Prompt.inputString(String.format(
          "내용(%s)? ", project.getContent())));
      project.setStartDate(Prompt.inputDate(String.format(
          "시작일(%s)? ", project.getStartDate())));
      project.setEndDate(Prompt.inputDate(String.format(
          "종료일(%s)? ", project.getEndDate())));

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!response.equalsIgnoreCase("y")) {
        System.out.println("프로젝트 변경을 취소하였습니다.");
        return;
      }

      if (projectDao.update(project) == 0) {
        System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
      } else {
        System.out.println("프로젝트를 변경하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("프로젝트 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
