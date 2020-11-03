package com.eomcs.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Member;

public class MemberDao {

  public Member findByName(String name) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no, name, email, photo, tel, cdt"
                + " from pms_member"
                + " where name = ?")) {

      stmt.setString(1, name);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Member member = new Member();
          member.setNo(rs.getInt("no"));
          member.setName(rs.getString("name"));
          member.setEmail(rs.getString("email"));
          member.setPhoto(rs.getString("photo"));
          member.setTel(rs.getString("tel"));
          member.setRegisteredDate(rs.getDate("cdt"));
          return member;
        } else {
          return null;
        }
      }
    }
  }
}
