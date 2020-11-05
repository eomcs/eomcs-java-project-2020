package com.eomcs.pms.dao.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;

public class TaskDaoImpl implements com.eomcs.pms.dao.TaskDao {

  Connection con;

  public TaskDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public int insert(Task task) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into pms_task(content,deadline,owner,project_no,status)"
            + " values(?,?,?,?,?)")) {

      stmt.setString(1, task.getContent());
      stmt.setDate(2, task.getDeadline());
      stmt.setInt(3, task.getOwner().getNo());
      stmt.setInt(4, task.getProjectNo());
      stmt.setInt(5, task.getStatus());
      return stmt.executeUpdate();
    }
  }

  @Override
  public int delete(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from pms_task where no=?")) {

      stmt.setInt(1, no);
      return stmt.executeUpdate();
    }
  }

  @Override
  public Task findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " t.no,"
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
          Task task = new Task();
          task.setNo(rs.getInt("no"));
          task.setContent(rs.getString("content"));
          task.setDeadline(rs.getDate("deadline"));
          task.setStatus(rs.getInt("status"));

          Member member = new Member();
          member.setNo(rs.getInt("owner_no"));
          member.setName(rs.getString("owner_name"));
          task.setOwner(member);

          task.setProjectNo(rs.getInt("project_no"));
          task.setProjectTitle(rs.getString("title"));
          return task;
        } else {
          return null;
        }
      }
    }
  }

  @Override
  public List<Task> findAll() throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select t.no, t.content, t.deadline, t.status, m.no owner_no, m.name owner_name"
            + " from pms_task t inner join pms_member m on t.owner=m.no"
            + " order by t.deadline asc")) {

      try (ResultSet rs = stmt.executeQuery()) {
        ArrayList<Task> tasks = new ArrayList<>();

        while (rs.next()) {
          Task task = new Task();
          task.setNo(rs.getInt("no"));
          task.setContent(rs.getString("content"));
          task.setDeadline(rs.getDate("deadline"));

          Member member = new Member();
          member.setNo(rs.getInt("owner_no"));
          member.setName(rs.getString("owner_name"));
          task.setOwner(member);

          task.setStatus(rs.getInt("status"));

          tasks.add(task);
        }
        return tasks;
      }
    }
  }

  @Override
  public int update(Task task) throws Exception {
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
      stmt.setInt(6, task.getNo());
      return stmt.executeUpdate();
    }
  }
}
