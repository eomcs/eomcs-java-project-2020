package com.eomcs.pms.service;

import java.util.List;
import java.util.Map;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.SqlSessionFactoryProxy;

public class DefaultProjectService implements ProjectService {

  TaskDao taskDao;
  ProjectDao projectDao;
  SqlSessionFactoryProxy factoryProxy;

  public DefaultProjectService(
      TaskDao taskDao,
      ProjectDao projectDao,
      SqlSessionFactoryProxy factoryProxy) {

    this.projectDao = projectDao;
    this.taskDao = taskDao;
    this.factoryProxy = factoryProxy;
  }

  @Override
  public int delete(int no) throws Exception {
    try {
      factoryProxy.startTransaction();

      taskDao.deleteByProjectNo(no);
      projectDao.deleteMembers(no);
      //if (100 == 100) throw new Exception("일부러 예외 발생!");
      int count = projectDao.delete(no);

      factoryProxy.commit();
      return count;

    } catch (Exception e) {
      factoryProxy.rollback();
      throw e; // 서비스 객체에서 발생한 예외는 호출자에게 전달한다.

    } finally {
      factoryProxy.endTransaction();
    }
  }

  @Override
  public int add(Project project) throws Exception {
    try {
      factoryProxy.startTransaction();

      projectDao.insert(project);

      //if (100 == 100) throw new Exception("일부러 예외 발생!");

      projectDao.insertMembers(project);

      factoryProxy.commit();
      return 1;

    } catch (Exception e) {
      factoryProxy.rollback();
      throw e;

    } finally {
      factoryProxy.endTransaction();
    }
  }

  @Override
  public List<Project> list() throws Exception {
    // 이 메서드를 보면 서비스 객체가 할 일이 없다.
    // 그냥 DAO 객체의 메서드를 호출한 다음에 그 리턴 값을
    // 그대로 리턴해주는 일을 한다.
    // 그럼 왜 이런 메서드를 만들어야 할까?
    // => 프로그래밍의 일관성을 위해서다.
    // => 커맨드 객체가 상황에 따라 Service 객체를 쓰거나 DAO 객체를 써야 한다면,
    //    프로그래밍의 일관성이 없어서 유지보수하기가 어렵다.
    // => 커맨드 객체가 서비스 객체를 사용하기로 했으면
    //    어떤 작업을 수행하던지 간에 일관되게 서비스 객체를 사용하는 것이
    //    유지보수하기에 더 낫다.
    // => 그래서 이런 메서드를 만드는 것이다
    //    즉 서비스 객체의 메서드에서 특별히 할 일이 없다 하더라도
    //    커맨드 객체가 일관성 있게 작업을 수행할 수 있도록
    //    중간에서 DAO 객체의 메서드를 호출해 주는 것이다.
    //
    return projectDao.findAll(null);
  }

  @Override
  public List<Project> list(String keyword) throws Exception {
    return projectDao.findAll(keyword);
  }

  @Override
  public List<Project> list(Map<String, Object> keywords) throws Exception {
    return projectDao.findByDetailKeyword(keywords);
  }

  @Override
  public Project get(int no) throws Exception {
    return projectDao.findByNo(no);
  }

  @Override
  public int update(Project project) throws Exception {
    return projectDao.update(project);
  }
}







