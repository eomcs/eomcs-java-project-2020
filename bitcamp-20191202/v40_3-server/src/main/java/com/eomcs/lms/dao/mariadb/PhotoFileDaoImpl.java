package com.eomcs.lms.dao.mariadb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoFile;
import com.eomcs.util.ConnectionFactory;

public class PhotoFileDaoImpl implements PhotoFileDao {

  ConnectionFactory conFactory;

  public PhotoFileDaoImpl(ConnectionFactory conFactory) {
    this.conFactory = conFactory;
  }

  @Override
  public int insert(PhotoFile photoFile) throws Exception {
    try (Connection con = conFactory.getConnection(); //
        Statement stmt = con.createStatement()) {

      int result = stmt.executeUpdate( //
          "insert into lms_photo_file(photo_id,file_path) values(" //
              + photoFile.getBoardNo() + ", '" + photoFile.getFilepath() //
              + "')");

      return result;
    }
  }

  @Override
  public List<PhotoFile> findAll(int boardNo) throws Exception {
    try (Connection con = conFactory.getConnection(); //
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery( //
            "select photo_file_id, photo_id, file_path" //
                + " from lms_photo_file" //
                + " where photo_id=" + boardNo //
                + " order by photo_file_id asc")) {

      ArrayList<PhotoFile> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new PhotoFile() //
            .setNo(rs.getInt("photo_file_id")) //
            .setFilepath(rs.getString("file_path")) //
            .setBoardNo(rs.getInt("photo_id")));
      }
      return list;
    }
  }

  @Override
  public int deleteAll(int boardNo) throws Exception {
    try (Connection con = conFactory.getConnection(); //
        Statement stmt = con.createStatement()) {
      int result = stmt.executeUpdate( //
          "delete from lms_photo_file" //
              + " where photo_id=" + boardNo);
      return result;
    }
  }

}
