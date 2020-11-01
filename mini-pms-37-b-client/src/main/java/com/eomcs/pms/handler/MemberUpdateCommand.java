package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        PreparedStatement stmt = con.prepareStatement(
            "select name, email, photo, tel"
                + " from pms_member"
                + " where no = ?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
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
        PreparedStatement stmt = con.prepareStatement(
            "update pms_member set"
                + " name = ?,"
                + " email = ?,"
                + " password = ?,"
                + " photo = ?,"
                + " tel = ?"
                + " where no = ?")) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPassword());
      stmt.setString(4, member.getPhoto());
      stmt.setString(5, member.getTel());
      stmt.setInt(6, no);
      int count = stmt.executeUpdate();

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
