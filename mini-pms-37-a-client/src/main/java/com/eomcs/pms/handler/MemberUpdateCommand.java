package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[회원 변경]");
    int no = Prompt.inputInt("번호? ");
    Member member = new Member();

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "select name, email, photo, tel"
              + " from pms_member"
              + " where no = %d",
              no);
      try (ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
          member.setName(rs.getString("name"));
          member.setEmail(rs.getString("email"));
          member.setPhoto(rs.getString("photo"));
          member.setTel(rs.getString("tel"));

        } else {
          System.out.println("해당 번호의 회원이 존재하지 않습니다.");
          return;
        }
      }
    } catch (Exception e) {
      System.out.println("회원 조회 중 오류 발생!");
      e.printStackTrace();
      return;
    }

    member.setName(Prompt.inputString(String.format(
        "이름(%s)? ", member.getName())));
    member.setEmail(Prompt.inputString(String.format(
        "이메일(%s)? ", member.getEmail())));
    member.setPassword(Prompt.inputString("암호? "));
    member.setPhoto(Prompt.inputString(String.format(
        "사진(%s)? ", member.getPhoto())));
    member.setTel(Prompt.inputString(String.format(
        "전화(%s)? ", member.getTel())));

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("회원 변경을 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "update pms_member set"
              + " name = '%s',"
              + " email = '%s',"
              + " password = '%s',"
              + " photo = '%s',"
              + " tel = '%s'"
              + " where no = %d",
              member.getName(),
              member.getEmail(),
              member.getPassword(),
              member.getPhoto(),
              member.getTel(),
              no);
      int count = stmt.executeUpdate(sql);

      if (count == 0) {
        System.out.println("해당 번호의 회원이 존재하지 않습니다.");
      } else {
        System.out.println("회원을 변경하였습니다.");
      }
    } catch (Exception e) {
      System.out.println("회원 변경 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
