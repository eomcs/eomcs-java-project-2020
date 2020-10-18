package com.eomcs.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskAddCommand implements Command {

  List<Task> taskList;
  MemberListCommand memberListCommand;

  public TaskAddCommand(List<Task> list, MemberListCommand memberListCommand) {
    this.taskList = list;
    this.memberListCommand = memberListCommand;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    try {
      out.println("[작업 등록]");

      Task task = new Task();
      task.setNo(Prompt.inputInt("번호? ", out, in));
      task.setContent(Prompt.inputString("내용? ", out, in));
      task.setDeadline(Prompt.inputDate("마감일? ", out, in));
      task.setStatus(Prompt.inputInt("상태?\n0: 신규\n1: 진행중\n2: 완료\n> ", out, in));

      while (true) {
        String name = Prompt.inputString("담당자?(취소: 빈 문자열) ", out, in);

        if (name.length() == 0) {
          out.println("작업 등록을 취소합니다.");
          return;
        } else if (memberListCommand.findByName(name) != null) {
          task.setOwner(name);
          break;
        }

        out.println("등록된 회원이 아닙니다.");
      }

      taskList.add(task);

    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생! - %s\n", e.getMessage());
    }
  }
}
