package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.util.Prompt;

public class ProjectAddCommand implements Command {

  ProjectService projectService;
  MemberService memberService;

  public ProjectAddCommand(
      ProjectService projectService,
      MemberService memberService) {
    this.projectService = projectService;
    this.memberService = memberService;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[프로젝트 등록]");

    try {
      Project project = new Project();
      project.setTitle(Prompt.inputString("프로젝트명? "));
      project.setContent(Prompt.inputString("내용? "));
      project.setStartDate(Prompt.inputDate("시작일? "));
      project.setEndDate(Prompt.inputDate("종료일? "));

      Member loginUser = (Member) context.get("loginUser");
      project.setOwner(loginUser);

      // 프로젝트에 참여할 회원 정보를 담는다.
      List<Member> members = new ArrayList<>();
      while (true) {
        String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");
        if (name.length() == 0) {
          break;
        } else {
          List<Member> list = memberService.list(name);
          if (list.size() == 0) {
            System.out.println("등록된 회원이 아닙니다.");
            continue;
          }
          members.add(list.get(0));
        }
      }
      project.setMembers(members);

      projectService.add(project);
      System.out.println("프로젝트가 등록되었습니다!");

    } catch (Exception e) {
      System.out.println("프로젝트 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
