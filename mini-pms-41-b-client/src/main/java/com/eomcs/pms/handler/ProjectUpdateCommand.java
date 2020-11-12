package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
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

      // 프로젝트에 참여할 회원 정보를 담는다.
      List<Member> members = new ArrayList<>();
      while (true) {
        String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");
        if (name.length() == 0) {
          break;
        } else {
          Member member = memberDao.findByName(name);
          if (member == null) {
            System.out.println("등록된 회원이 아닙니다.");
            continue;
          }
          members.add(member);
        }
      }
      project.setMembers(members);

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
