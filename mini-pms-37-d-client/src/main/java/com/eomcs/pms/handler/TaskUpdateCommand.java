package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.eomcs.pms.domain.Member;
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
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111")) {

      // 기존 작업 정보 가져오기
      try (PreparedStatement stmt = con.prepareStatement(
          "select"
              + " t.content,"
              + " t.deadline,"
              + " t.status,"
              + " m.no owner_no,"
              + " m.name owner_name,"
              + " p.no project_no,"
              + " p.title"
              + " from pms_task t"
              + " inner join pms_member m on t.owner=m.no"
              + " inner join pms_project p on t.project_no=p.no"
              + " where t.no = ?")) {

        stmt.setInt(1, no);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            task.setContent(rs.getString("content"));
            task.setDeadline(rs.getDate("deadline"));
            task.setStatus(rs.getInt("status"));

            Member member = new Member();
            member.setNo(rs.getInt("owner_no"));
            member.setName(rs.getString("owner_name"));
            task.setOwner(member);

            task.setProjectNo(rs.getInt("project_no"));
            task.setProjectTitle(rs.getString("title"));
          } else {
            System.out.println("해당 번호의 작업이 존재하지 않습니다.");
            return;
          }
        }
      }

      // 프로젝트 변경
      System.out.printf("현재 프로젝트: %s\n", task.getProjectTitle());
      try (PreparedStatement stmt = con.prepareStatement(
          "select p.no, p.title"
              + " from pms_project p"
              + " order by p.no desc");
          ResultSet rs = stmt.executeQuery()) {

        ArrayList<Integer> noList = new ArrayList<>();

        System.out.println("프로젝트들:");
        while (rs.next()) {
          System.out.printf("  %d, %s\n",
              rs.getInt("no"),
              rs.getString("title"));
          noList.add(rs.getInt("no"));
        }

        if (noList.size() == 0) {
          System.out.println("프로젝트가 없습니다!");
          return;
        }

        // 사용자로부터 프로젝트 번호를 입력 받는다.
        while (true) {
          int projectNo = Prompt.inputInt("변경할 프로젝트 번호?(0: 취소) ");
          if (projectNo == 0) {
            System.out.println("작업 변경을 취소합니다.");
            return;
          } else if (noList.contains(projectNo)) {
            task.setProjectNo(projectNo);
            break;
          }
          System.out.println("프로젝트 번호가 맞지 않습니다.");
        }
      }

      // 작업 정보 변경
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

      // 작업을 수행할 담당자를 결정한다.
      try (PreparedStatement stmt = con.prepareStatement(
          "select mp.member_no, m.name"
              + " from pms_member_project mp inner join pms_member m"
              + " on mp.member_no=m.no"
              + " where mp.project_no=" + task.getProjectNo()
              + " order by m.name asc");
          ResultSet rs = stmt.executeQuery()) {

        // 멤버 번호를 보관할 컬렉션
        ArrayList<Integer> noList = new ArrayList<>();

        System.out.println("멤버들:");
        while (rs.next()) {
          System.out.printf("  %d, %s\n",
              rs.getInt("member_no"),
              rs.getString("name"));
          noList.add(rs.getInt("member_no"));
        }

        if (noList.size() == 0) {
          System.out.println("멤버가 없습니다!");
          return;
        }

        // 사용자로부터 멤버 번호를 입력 받는다.
        while (true) {
          int memberNo = Prompt.inputInt("변경할 멤버 번호?(0: 취소) ");
          if (memberNo == 0) {
            System.out.println("작업 변경을 취소합니다.");
            return;
          } else if (noList.contains(memberNo)) {
            Member member = new Member();
            member.setNo(memberNo);
            task.setOwner(member);
            break;
          }
          System.out.println("멤버 번호가 맞지 않습니다.");
        }
      }

      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!response.equalsIgnoreCase("y")) {
        System.out.println("작업 변경을 취소하였습니다.");
        return;
      }

      try (PreparedStatement stmt = con.prepareStatement(
          "update pms_task set"
              + " content = ?,"
              + " deadline = ?,"
              + " owner = ?,"
              + " project_no = ?,"
              + " status = ?"
              + " where no = ?")) {

        stmt.setString(1, task.getContent());
        stmt.setDate(2, task.getDeadline());
        stmt.setInt(3, task.getOwner().getNo());
        stmt.setInt(4, task.getProjectNo());
        stmt.setInt(5, task.getStatus());
        stmt.setInt(6, no);
        int count = stmt.executeUpdate();

        if (count == 0) {
          System.out.println("해당 번호의 작업이 존재하지 않습니다.");
        } else {
          System.out.println("작업을 변경하였습니다.");
        }
      }
    } catch (Exception e) {
      System.out.println("작업 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
