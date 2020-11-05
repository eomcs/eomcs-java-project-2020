package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectAddCommand implements Command {

  ProjectDao projectDao;
  MemberDao memberDao;

  public ProjectAddCommand(ProjectDao projectDao, MemberDao memberDao) {
    this.projectDao = projectDao;
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    System.out.println("[프로젝트 등록]");

    try {
      Project project = new Project();
      project.setTitle(Prompt.inputString("프로젝트명? "));
      project.setContent(Prompt.inputString("내용? "));
      project.setStartDate(Prompt.inputDate("시작일? "));
      project.setEndDate(Prompt.inputDate("종료일? "));

      while (true) {
        String name = Prompt.inputString("관리자?(취소: 빈 문자열) ");

        if (name.length() == 0) {
          System.out.println("프로젝트 등록을 취소합니다.");
          return;
        } else {
          Member member = memberDao.findByName(name);
          if (member == null) {
            System.out.println("등록된 회원이 아닙니다.");
            continue;
          }
          project.setOwner(member);
          break;
        }
      }

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

      // 사용자로부터 입력 받은 멤버 정보를 프로젝트에 저장한다.
      project.setMembers(members);

      projectDao.insert(project);

    } catch (Exception e) {
      System.out.println("프로젝트 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
