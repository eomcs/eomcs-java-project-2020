package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "select title, content, sdt, edt, owner, members"
              + " from pms_project"
              + " where no = %d", no);

      try (ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
          project.setTitle(rs.getString("title"));
          project.setContent(rs.getString("content"));
          project.setStartDate(rs.getDate("sdt"));
          project.setEndDate(rs.getDate("edt"));
          project.setOwner(rs.getString("owner"));
          project.setMembers(rs.getString("members"));
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
          "만든이(%s)?(취소: 빈 문자열) ", project.getOwner()));
      if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return;
      } else if (memberListCommand.findByName(name) != null) {
        project.setOwner(name);
        break;
      }
      System.out.println("등록된 회원이 아닙니다.");
    }

    StringBuilder members = new StringBuilder();
    while (true) {
      String name = Prompt.inputString(String.format(
          "팀원(%s)?(완료: 빈 문자열) ", project.getMembers()));
      if (name.length() == 0) {
        project.setMembers(members.toString());
        break;
      } else if (memberListCommand.findByName(name) != null) {
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

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update pms_project set"
              + " title = '%s',"
              + " content = '%s',"
              + " sdt = '%s',"
              + " edt = '%s',"
              + " owner = '%s',"
              + " members = '%s'"
              + " where no = %d",
              project.getTitle(),
              project.getContent(),
              project.getStartDate(),
              project.getEndDate(),
              project.getOwner(),
              project.getMembers(),
              no);
      int count = stmt.executeUpdate(sql);

      if (count == 0) {
        System.out.println("해당 번호의 프로젝트가 존재하지 않습니다.");
      } else {
        System.out.println("프로젝트를 변경하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("프로젝트 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
