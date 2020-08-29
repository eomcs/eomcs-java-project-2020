package com.eomcs.pms.handler;

import com.eomcs.pms.domain.Project;
import com.eomcs.util.ArrayList;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  //Project 객체 목록을 저장할 ArrayList 객체를 준비한다.
  // 제네릭 문법으로 항목의 타입을 지정한다.
  ArrayList<Project> projectList = new ArrayList<>();
  MemberHandler memberHandler;

  public ProjectHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  public void add() {
    System.out.println("[프로젝트 등록]");

    Project project = new Project();
    project.setNo(Prompt.inputInt("번호? "));
    project.setTitle(Prompt.inputString("프로젝트명? "));
    project.setContent(Prompt.inputString("내용? "));
    project.setStartDate(Prompt.inputDate("시작일? "));
    project.setEndDate(Prompt.inputDate("종료일? "));

    while (true) {
      String name = Prompt.inputString("만든이?(취소: 빈 문자열) ");

      if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return;
      } else if (memberHandler.findByName(name) != null) {
        project.setOwner(name);
        break;
      }

      System.out.println("등록된 회원이 아닙니다.");
    }

    StringBuilder members = new StringBuilder();
    while (true) {
      String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");

      if (name.length() == 0) {
        break;
      } else if (memberHandler.findByName(name) != null) {
        if (members.length() > 0) {
          members.append(",");
        }
        members.append(name);
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }
    project.setMembers(members.toString());

    // 제네릭 문법에 따라 add()를 호출할 때 넘겨줄 수 있는 값은 
    // Project 또는 그 하위 타입의 인스턴스만 가능하다.
    // 다른 타입은 불가능하다.
    projectList.add(project);
  }

  public void list() {
    System.out.println("[프로젝트 목록]");

    // 제네릭 문법에 따라 리턴 타입이 'Project[]' 이기 때문에
    // 따로 형변환 할 필요가 없다.
    // 대신 Project[] 배열을 리턴해 달라는 의미로 배열의 타입 정보를 넘긴다.
    Project[] projects = projectList.toArray(Project[].class);

    for (Project project : projects) {
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          project.getNo(),
          project.getTitle(),
          project.getStartDate(),
          project.getEndDate(),
          project.getOwner(),
          project.getMembers());
    }
  }
}
