package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.MemberDao;
import com.eomcs.lms.domain.Member;

public class MemberDaoImpl implements MemberDao {

  String jdbcUrl;
  String username;
  String password;

  public MemberDaoImpl(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  @Override
  public int insert(Member member) throws Exception {
    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("insert into lms_member(name, email, pwd, tel, photo) "
          + "values('" + member.getName() + "', '" + member.getEmail() + "', '"
          + member.getPassword() + "', '" + member.getTel() + "', '" + member.getPhoto() + "')");

      return result;
    }
  }

  @Override
  public List<Member> findAll() throws Exception {
    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select member_id, name, email, tel, cdt from lms_member")) {

      ArrayList<Member> list = new ArrayList<>();

      while (rs.next()) {
        Member member = new Member();

        member.setNo(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setTel(rs.getString("tel"));
        member.setRegisteredDate(rs.getDate("cdt"));

        list.add(member);
      }

      return list;
    }
  }

  @Override
  public Member findByNo(int no) throws Exception {
    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select member_id, name, email, pwd, tel, photo" + " from lms_member"
                + " where member_id=" + no)) {

      if (rs.next()) {
        Member member = new Member();
        member.setNo(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("pwd"));
        member.setTel(rs.getString("tel"));
        member.setPhoto(rs.getString("photo"));
        return member;

      } else {
        return null;
      }
    }
  }

  @Override
  public int update(Member member) throws Exception {
    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        Statement stmt = con.createStatement()) {

      int result =
          stmt.executeUpdate("update lms_member set name= '" + member.getName() + "', email='"
              + member.getEmail() + "', pwd='" + member.getPassword() + "', tel='" + member.getTel()
              + "', photo='" + member.getPhoto() + "' where member_id=" + member.getNo());

      return result;
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate("delete from lms_member where member_id=" + no);

      return result;
    }
  }

  @Override
  public List<Member> findByKeyword(String keyword) throws Exception {
    try (Connection con = DriverManager.getConnection(jdbcUrl, username, password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select member_id, name, email, tel, cdt" //
                + " from lms_member" //
                + " where name like '%" + keyword //
                + "%' or email like '%" + keyword //
                + "%' or tel like '%" + keyword + "%'")) {

      ArrayList<Member> list = new ArrayList<>();

      while (rs.next()) {
        Member member = new Member();

        member.setNo(rs.getInt("member_id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setTel(rs.getString("tel"));
        member.setRegisteredDate(rs.getDate("cdt"));

        list.add(member);
      }

      return list;
    }
  }

}
