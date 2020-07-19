package com.eomcs.lms.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import com.eomcs.lms.dao.PhotoBoardDao;
import com.eomcs.lms.dao.PhotoFileDao;
import com.eomcs.lms.domain.PhotoBoard;
import com.eomcs.lms.service.PhotoBoardService;

@Component
public class PhotoBoardServiceImpl implements PhotoBoardService {

  TransactionTemplate transactionTemplate;
  PhotoBoardDao photoBoardDao;
  PhotoFileDao photoFileDao;

  public PhotoBoardServiceImpl( //
      PlatformTransactionManager txManager, //
      PhotoBoardDao photoBoardDao, //
      PhotoFileDao photoFileDao) {
    this.transactionTemplate = new TransactionTemplate(txManager);
    this.photoBoardDao = photoBoardDao;
    this.photoFileDao = photoFileDao;
  }

  // @Transactional
  // => 메서드 전체를 트랜잭션으로 묶는다.
  // => 예외 없이 실행하면 자동으로 commit() 한다.
  // => 예외가 발생하면 자동으로 rollback() 한다.
  @Transactional
  @Override
  public void add(PhotoBoard photoBoard) throws Exception {
    if (photoBoardDao.insert(photoBoard) == 0) {
      throw new Exception("사진 게시글 등록에 실패했습니다.");
    }
    photoFileDao.insert(photoBoard);
  }

  @Override
  public List<PhotoBoard> listLessonPhoto(int lessonNo) throws Exception {
    return photoBoardDao.findAllByLessonNo(lessonNo);
  }

  @Override
  public PhotoBoard get(int no) throws Exception {
    return photoBoardDao.findByNo(no);
  }

  @Transactional
  @Override
  public void update(PhotoBoard photoBoard) throws Exception {
    if (photoBoardDao.update(photoBoard) == 0) {
      throw new Exception("사진 게시글 변경에 실패했습니다.");
    }
    if (photoBoard.getFiles() != null) {
      photoFileDao.deleteAll(photoBoard.getNo());
      photoFileDao.insert(photoBoard);
    }
  }

  @Transactional
  @Override
  public void delete(int no) throws Exception {
    photoFileDao.deleteAll(no);
    if (photoBoardDao.delete(no) == 0) {
      throw new Exception("해당 번호의 사진 게시글이 없습니다.");
    }
  }
}
