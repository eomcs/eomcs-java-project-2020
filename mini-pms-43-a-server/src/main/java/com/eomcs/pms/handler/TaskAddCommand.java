package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.util.Prompt;

public class TaskAddCommand implements Command {

  TaskService taskService;
  ProjectService projectService;
  MemberService memberService;

  public TaskAddCommand(
      TaskService taskService,
      ProjectService projectService,
      MemberService memberService) {
    this.taskService = taskService;
    this.projectService = projectService;
    this.memberService = memberService;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in, Map<String,Object> context) {
    try {
      out.println("[작업 등록]");

      // 작업 정보를 입력 받을 객체 준비
      Task task = new Task();

      // 프로젝트 목록을 가져온다.
      List<Project> projects = projectService.list();
      if (projects.size() == 0) {
        out.println("프로젝트가 없습니다!");
        return;
      }

      ArrayList<Integer> projectNoList = new ArrayList<>();
      for (Project project : projects) {
        out.printf("  %d, %s\n", project.getNo(), project.getTitle());
        projectNoList.add(project.getNo());
      }

      // 사용자로부터 프로젝트 번호를 입력 받는다.
      while (true) {
        int no = Prompt.inputInt("프로젝트 번호?(0: 취소) ", out, in);
        if (no == 0) {
          out.println("작업 등록을 취소합니다.");
          return;
        } else if (projectNoList.contains(no)) {
          task.setProjectNo(no);
          break;
        }
        out.println("프로젝트 번호가 맞지 않습니다.");
      }

      // 작업 정보를 입력 받는다.
      task.setContent(Prompt.inputString("내용? ", out, in));
      task.setDeadline(Prompt.inputDate("마감일? ", out, in));
      task.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ", out, in));

      // 프로젝트의 멤버 중에서 작업을 수행할 담당자를 결정한다.
      List<Member> members = memberService.listForProject(task.getProjectNo());
      if (members.size() == 0) {
        out.println("멤버가 없습니다!");
        return;
      }

      // 멤버 번호를 보관할 컬렉션
      ArrayList<Integer> memberNoList = new ArrayList<>();

      System.out.println("멤버들:");
      for (Member member : members) {
        out.printf("  %d, %s\n", member.getNo(), member.getName());
        memberNoList.add(member.getNo());
      }

      // 사용자로부터 멤버 번호를 입력 받는다.
      while (true) {
        int no = Prompt.inputInt("담당자 번호?(0: 취소) ", out, in);
        if (no == 0) {
          out.println("작업 등록을 취소합니다.");
          return;
        } else if (memberNoList.contains(no)) {
          Member member = new Member();
          member.setNo(no);
          task.setOwner(member);
          break;
        }
        out.println("멤버 번호가 맞지 않습니다.");
      }

      // 작업 정보를 입력한다.
      taskService.add(task);

      out.println("작업을 등록했습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
