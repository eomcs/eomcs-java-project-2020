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

public class TaskUpdateCommand implements Command {

  TaskService taskService;
  ProjectService projectService;
  MemberService memberService;

  public TaskUpdateCommand(
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
      out.println("[작업 변경]");

      int no = Prompt.inputInt("번호? ", out, in);

      Task task = taskService.get(no);
      if (task == null) {
        out.println("해당 번호의 작업이 존재하지 않습니다.");
        return;
      }

      // 프로젝트 변경
      out.printf("현재 프로젝트: %s\n", task.getProjectTitle());

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
        int projectNo = Prompt.inputInt("프로젝트 번호?(0: 취소) ", out, in);
        if (projectNo == 0) {
          out.println("작업 등록을 취소합니다.");
          return;
        } else if (projectNoList.contains(projectNo)) {
          task.setProjectNo(projectNo);
          break;
        }
        out.println("프로젝트 번호가 맞지 않습니다.");
      }

      // 작업 정보 변경
      task.setContent(Prompt.inputString(String.format(
          "내용(%s)? ", task.getContent()), out, in));
      task.setDeadline(Prompt.inputDate(String.format(
          "마감일(%s)? ", task.getDeadline()), out, in));

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
      task.setStatus(Prompt.inputInt(String.format(
          "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", stateLabel), out, in));

      // 프로젝트의 멤버 중에서 작업을 수행할 담당자를 결정한다.
      List<Member> members = memberService.listForProject(task.getProjectNo());
      if (members.size() == 0) {
        out.println("멤버가 없습니다!");
        return;
      }

      // 멤버 번호를 보관할 컬렉션
      ArrayList<Integer> memberNoList = new ArrayList<>();

      out.println("멤버들:");
      for (Member member : members) {
        out.printf("  %d, %s\n", member.getNo(), member.getName());
        memberNoList.add(member.getNo());
      }

      // 사용자로부터 멤버 번호를 입력 받는다.
      while (true) {
        int memberNo = Prompt.inputInt("담당자 번호?(0: 취소) ", out, in);
        if (memberNo == 0) {
          out.println("작업 변경을 취소합니다.");
          return;
        } else if (memberNoList.contains(memberNo)) {
          Member member = new Member();
          member.setNo(memberNo);
          task.setOwner(member);
          break;
        }
        out.println("멤버 번호가 맞지 않습니다.");
      }

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("작업 변경을 취소하였습니다.");
        return;
      }

      if (taskService.update(task) == 0) {
        out.println("해당 번호의 작업이 존재하지 않습니다.");
        return;
      }

      System.out.println("작업을 변경하였습니다.");

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
      e.printStackTrace();
    }
  }
}
