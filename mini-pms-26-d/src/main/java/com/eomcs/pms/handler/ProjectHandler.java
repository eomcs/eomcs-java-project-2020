package com.eomcs.pms.handler;

import java.sql.Date;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Iterator;
import com.eomcs.util.List;
import com.eomcs.util.Prompt;

public class ProjectHandler {

  // 목록을 다루는 객체를 지정할 때,
  // => 특정 클래스(예: AbstractList, LinkedList, ArrayList)를 지정하는 대신에,
  // => 사용 규칙(예: List)을 지정함으로써
  //    더 다양한 타입의 객체로 교체할 수 있게 만든다.
  // => `List` 규칙을 따르는 객체라면 어떤 클래스의 객체든지 사용할 수 있다.
  //    결국 유지보수를 더 유연하게 하기 위함이다.
  List<Project> projectList;
  MemberHandler memberHandler;

  public ProjectHandler(List<Project> list, MemberHandler memberHandler) {
    this.projectList = list;
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

    projectList.add(project);
  }

  public void list() {
    System.out.println("[프로젝트 목록]");

    // 전체 목록을 조회할 때 `Iterator` 객체를 사용한다.
    // 만약 목록의 일부만 조회하면다면 인덱스를 직접 다루는 이전 방식을 사용해야 한다.
    Iterator<Project> iterator = projectList.iterator();

    while (iterator.hasNext()) {
      Project project = iterator.next();
      System.out.printf("%d, %s, %s, %s, %s, [%s]\n",
          project.getNo(),
          project.getTitle(),
          project.getStartDate(),
          project.getEndDate(),
          project.getOwner(),
          project.getMembers());
    }
  }

  public void detail() {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.inputInt("번호? ");
    Project project = findByNo(no);

    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    System.out.printf("프로젝트명: %s\n", project.getTitle());
    System.out.printf("내용: %s\n", project.getContent());
    System.out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
    System.out.printf("만든이: %s\n", project.getOwner());
    System.out.printf("팀원: %s\n", project.getMembers());
  }

  public void update() {
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
      } else if (memberHandler.findByName(name) != null) {
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
      } else if (memberHandler.findByName(name) != null) {
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

  public void delete() {
    System.out.println("[프로젝트 삭제]");
    int no = Prompt.inputInt("번호? ");
    int index = indexOf(no);

    if (index == -1) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }

    String response = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }

    projectList.remove(index);
    System.out.println("프로젝트를 삭제하였습니다.");
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

  private int indexOf(int no) {
    for (int i = 0; i < projectList.size(); i++) {
      Project project = projectList.get(i);
      if (project.getNo() == no) {
        return i;
      }
    }
    return -1;
  }
}
