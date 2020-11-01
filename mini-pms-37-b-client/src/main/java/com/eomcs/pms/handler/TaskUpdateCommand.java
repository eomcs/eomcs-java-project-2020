package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Task;
import com.eomcs.util.Prompt;

public class TaskUpdateCommand implements Command {

  MemberListCommand memberListCommand;

  public TaskUpdateCommand(MemberListCommand memberListCommand) {
    this.memberListCommand = memberListCommand;
  }

  @Override
  public void execute() {
    System.out.println("[작업 변경]");
    int no = Prompt.inputInt("번호? ");

    Task task = new Task();

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select content, deadline, owner, status"
                + " from pms_task"
                + " where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          task.setContent(rs.getString("content"));
          task.setDeadline(rs.getDate("deadline"));
          task.setOwner(rs.getString("owner"));
          task.setStatus(rs.getInt("status"));
        } else {
          System.out.println("해당 번호의 작업이 존재하지 않습니다.");
          return;
        }
      }
    } catch (Exception e) {
      System.out.println("작업 조회 중 오류 발생!");
      e.printStackTrace();
      return;
    }

    task.setContent(Prompt.inputString(String.format(
        "내용(%s)? ", task.getContent())));
    task.setDeadline(Prompt.inputDate(String.format(
        "마감일(%s)? ", task.getDeadline())));

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
        "상태(%s)?\n0: 신규\n1: 진행중\n2: 완료\n> ", stateLabel)));

    while (true) {
      String name = Prompt.inputString(
          String.format("담당자(%s)?(취소: 빈 문자열) ", task.getOwner()));

      if (name.length() == 0) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } else if (memberListCommand.findByName(name) != null) {
        task.setOwner(name);
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("작업 변경을 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "update pms_task set"
                + " content = ?,"
                + " deadline = ?,"
                + " owner = ?,"
                + " status = ?"
                + " where no = ?")) {

      stmt.setString(1, task.getContent());
      stmt.setDate(2, task.getDeadline());
      stmt.setString(3, task.getOwner());
      stmt.setInt(4, task.getStatus());
      stmt.setInt(5, no);
      int count = stmt.executeUpdate();

      if (count == 0) {
        System.out.println("해당 번호의 작업이 존재하지 않습니다.");
      } else {
        System.out.println("작업을 변경하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("작업 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
