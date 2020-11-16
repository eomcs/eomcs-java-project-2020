package com.eomcs.pms.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class ProjectDetailCommand implements Command {
  ProjectDao projectDao;
  TaskDao taskDao;

  public ProjectDetailCommand(ProjectDao projectDao, TaskDao taskDao) {
    this.projectDao = projectDao;
    this.taskDao = taskDao;
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

      System.out.println("작업:");
      System.out.println("--------------------------------");

      HashMap<String,Object> map = new HashMap<>();
      map.put("projectNo", project.getNo());

      List<Task> tasks = taskDao.findAll(map);

      System.out.println("번호, 작업내용, 마감일, 작업자, 상태");
      for (Task task : tasks) {
        String stateLabel = null;
        switch (task.getStatus()) {
          case 1:
            stateLabel = "진행중";
            break;
          case 2:
            stateLabel = "완료";
            break;
          default:
            stateLabel = "신규";
        }
        System.out.printf("%d, %s, %s, %s, %s\n",
            task.getNo(),
            task.getContent(),
            task.getDeadline(),
            task.getOwner().getName(),
            stateLabel);
      }

    } catch (Exception e) {
      System.out.println("프로젝트 조회 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
