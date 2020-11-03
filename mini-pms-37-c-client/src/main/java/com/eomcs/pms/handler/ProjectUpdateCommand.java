package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectUpdateCommand implements Command {

  MemberListCommand memberListCommand;

  public ProjectUpdateCommand(MemberListCommand memberListCommand) {
    this.memberListCommand = memberListCommand;
  }

  @Override
  public void execute() {
    System.out.println("[프로젝트 변경]");
    int no = Prompt.inputInt("번호? ");

    Project project = new Project();

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select p.title, p.content, p.sdt, p.edt, p.owner, m.name owner_name"
                + " from pms_project p inner join pms_member m on p.owner=m.no"
                + " where p.no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          project.setNo(no);
          project.setTitle(rs.getString("title"));
          project.setContent(rs.getString("content"));
          project.setStartDate(rs.getDate("sdt"));
          project.setEndDate(rs.getDate("edt"));

          // 관리자 정보를 가져와서 프로젝트 객체에 담는다.
          Member owner = new Member();
          owner.setNo(rs.getInt("owner"));
          owner.setName(rs.getString("owner_name"));
          project.setOwner(owner);

        } else {
          System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
          return;
        }
      }
    } catch (Exception e) {
      System.out.println("프로젝트 조회 중 오류 발생!");
      e.printStackTrace();
      return;
    }

    project.setTitle(Prompt.inputString(String.format(
        "프로젝트명(%s)? ", project.getTitle())));
    project.setContent(Prompt.inputString(String.format(
        "내용(%s)? ", project.getContent())));
    project.setStartDate(Prompt.inputDate(String.format(
        "시작일(%s)? ", project.getStartDate())));
    project.setEndDate(Prompt.inputDate(String.format(
        "종료일(%s)? ", project.getEndDate())));

    while (true) {
      String name = Prompt.inputString(String.format(
          "관리자(%s)?(취소: 빈 문자열) ", project.getOwner().getName()));
      if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return;
      } else {
        Member member = memberListCommand.findByName(name);
        if (member == null) {
          System.out.println("등록된 회원이 아닙니다.");
          continue;
        }
        project.setOwner(member);
        break;
      }
    }

    // 프로젝트에 참여할 회원 정보를 담는다.
    List<Member> members = new ArrayList<>();
    while (true) {
      String name = Prompt.inputString("팀원?(완료: 빈 문자열) ");
      if (name.length() == 0) {
        break;
      } else {
        Member member = memberListCommand.findByName(name);
        if (member == null) {
          System.out.println("등록된 회원이 아닙니다.");
          continue;
        }
        members.add(member);
      }
    }
    project.setMembers(members);

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("프로젝트 변경을 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "update pms_project set"
                + " title = ?,"
                + " content = ?,"
                + " sdt = ?,"
                + " edt = ?,"
                + " owner = ?"
                + " where no = ?")) {

      stmt.setString(1, project.getTitle());
      stmt.setString(2, project.getContent());
      stmt.setDate(3, project.getStartDate());
      stmt.setDate(4, project.getEndDate());
      stmt.setInt(5, project.getOwner().getNo());
      stmt.setInt(6, project.getNo());
      int count = stmt.executeUpdate();

      if (count == 0) {
        System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
        return;
      }

      // 프로젝트 팀원 변경한다.
      // => 기존에 설정된 모든 팀원을 삭제한다.
      try (PreparedStatement stmt2 = con.prepareStatement(
          "delete from pms_member_project where project_no=" + project.getNo())) {
        stmt2.executeUpdate();
      }
      // => 새로 팀원을 입력한다.
      try (PreparedStatement stmt2 = con.prepareStatement(
          "insert into pms_member_project(member_no, project_no) values(?,?)")) {
        for (Member member : project.getMembers()) {
          stmt2.setInt(1, member.getNo());
          stmt2.setInt(2, project.getNo());
          stmt2.executeUpdate();
        }
      }

      System.out.println("프로젝트를 변경하였습니다.");

    } catch (Exception e) {
      System.out.println("프로젝트 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
