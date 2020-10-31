package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberAddCommand implements Command {

  @Override
  public void execute() {
    System.out.println("[회원 등록]");

    Member member = new Member();
    member.setName(Prompt.inputString("이름? "));
    member.setEmail(Prompt.inputString("이메일? "));
    member.setPassword(Prompt.inputString("암호? "));
    member.setPhoto(Prompt.inputString("사진? "));
    member.setTel(Prompt.inputString("전화? "));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        Statement stmt = con.createStatement()) {

      String sql = String.format(
          "insert into pms_member(name,email,password,photo,tel)"
              + " values('%s','%s','%s','%s','%s')",
              member.getName(),
              member.getEmail(),
              member.getPassword(),
              member.getPhoto(),
              member.getTel());
      stmt.executeUpdate(sql);

      System.out.println("회원을 등록하였습니다.");

    } catch (Exception e) {
      System.out.println("회원 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
