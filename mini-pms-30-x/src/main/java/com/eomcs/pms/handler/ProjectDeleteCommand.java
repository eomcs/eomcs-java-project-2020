package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectDeleteCommand implements Command {

  List<Project> projectList;

  public ProjectDeleteCommand(List<Project> list) {
    this.projectList = list;
  }

  @Override
  public void execute() {
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
