package com.eomcs.pms.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskAddCommand implements Command {

  TaskDao taskDao;
  ProjectDao projectDao;
  MemberDao memberDao;

  public TaskAddCommand(TaskDao taskDao, ProjectDao projectDao, MemberDao memberDao) {
    this.taskDao = taskDao;
    this.projectDao = projectDao;
    this.memberDao = memberDao;
  }

  @Override
  public void execute(Map<String,Object> context) {
    System.out.println("[작업 등록]");

    // 작업 정보를 입력 받을 객체 준비
    Task task = new Task();

    // 프로젝트 목록을 가져온다.
    try {
      List<Project> projects = projectDao.findAll();
      if (projects.size() == 0) {
        System.out.println("프로젝트가 없습니다!");
        return;
      }

      ArrayList<Integer> projectNoList = new ArrayList<>();
      for (Project project : projects) {
        System.out.printf("  %d, %s\n", project.getNo(), project.getTitle());
        projectNoList.add(project.getNo());
      }

      // 사용자로부터 프로젝트 번호를 입력 받는다.
      while (true) {
        int no = Prompt.inputInt("프로젝트 번호?(0: 취소) ");
        if (no == 0) {
          System.out.println("작업 등록을 취소합니다.");
          return;
        } else if (projectNoList.contains(no)) {
          task.setProjectNo(no);
          break;
        }
        System.out.println("프로젝트 번호가 맞지 않습니다.");
      }

      // 작업 정보를 입력 받는다.
      task.setContent(Prompt.inputString("내용? "));
      task.setDeadline(Prompt.inputDate("마감일? "));
      task.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> "));

      // 프로젝트의 멤버 중에서 작업을 수행할 담당자를 결정한다.
      List<Member> members = memberDao.findByProjectNo(task.getProjectNo());
      if (members.size() == 0) {
        System.out.println("멤버가 없습니다!");
        return;
      }

      // 멤버 번호를 보관할 컬렉션
      ArrayList<Integer> memberNoList = new ArrayList<>();

      System.out.println("멤버들:");
      for (Member member : members) {
        System.out.printf("  %d, %s\n", member.getNo(), member.getName());
        memberNoList.add(member.getNo());
      }

      // 사용자로부터 멤버 번호를 입력 받는다.
      while (true) {
        int no = Prompt.inputInt("담당자 번호?(0: 취소) ");
        if (no == 0) {
          System.out.println("작업 등록을 취소합니다.");
          return;
        } else if (memberNoList.contains(no)) {
          Member member = new Member();
          member.setNo(no);
          task.setOwner(member);
          break;
        }
        System.out.println("멤버 번호가 맞지 않습니다.");
      }

      // 작업 정보를 입력한다.
      taskDao.insert(task);

      System.out.println("작업을 등록했습니다.");

    } catch (Exception e) {
      System.out.println("작업 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
