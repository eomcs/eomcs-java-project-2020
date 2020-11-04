package com.eomcs.pms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import com.eomcs.pms.domain.Task;

public class TaskDao {
  public int insert(Task task) throws Exception {
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
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

  public int delete(int no) throws Exception {
    return 0;
  }

  public Task findByNo(int no) throws Exception {
    return null;
  }

  public List<Task> findAll() throws Exception {
    return null;
  }

  public int update(Task task) throws Exception {
    return 0;
  }
}
