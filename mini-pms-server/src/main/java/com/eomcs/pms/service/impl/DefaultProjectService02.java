package com.eomcs.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

// 트랜잭션 다루기 방법2:
// => TransactionTemplate 을 사용하여 코드로 직접 트랜잭션 관리하기
//    - 트랜잭션의 실행 정책을 정의하고 commit/rollback 하는 것을 캡슐화한 클래스다.
//    - 그래서 TransactionManager를 직접 사용하는 것 보다 더 적은 코드를 작성한다.
//
//@Service
public class DefaultProjectService02 implements ProjectService {

  TaskDao taskDao;
  ProjectDao projectDao;
  TransactionTemplate transactionTemplate;

  public DefaultProjectService02(
      TaskDao taskDao,
      ProjectDao projectDao,
      PlatformTransactionManager txManager) {

    this.projectDao = projectDao;
    this.taskDao = taskDao;
    this.transactionTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int delete(int no) throws Exception {

    // 트랜잭션으로 다뤄야 할 코드를 TransactionCallback 구현체에 담는다.
    //
    //    class DeleteWork implements TransactionCallback<Integer> {
    //      @Override
    //      public Integer doInTransaction(TransactionStatus status) {
    //        try {
    //          // 트랜잭션으로 다뤄야 할 작업을 이 메서드에 작성한다.
    //          taskDao.deleteByProjectNo(no);
    //          projectDao.deleteMembers(no);
    //          return projectDao.delete(no);
    //        } catch (Exception e) {
    //          status.setRollbackOnly();
    //          return 0;
    //        }
    //      }
    //    }

    // 트랜잭션 템플릿은 트랜잭션 매니저를 통해 트랜잭션을 실행하고
    // TransactionCallback 에 담긴 코드를 실행한다.
    // 정상적으로 실행했으면 내부에서 commit()을 호출할 것이고,
    // 실행 도중에 오류가 발생했으면 내부에서 rollback()을 호출할 것이다.
    // 이런 일을 하는게 TransactionTemplate 객체이다.
    //
    //    return transactionTemplate.execute(new DeleteWork());

    return projectDao.inactive(no);
  }

  @Override
  public int add(Project project) throws Exception {

    return transactionTemplate.execute(new TransactionCallback<Integer>() {
      @Override
      public Integer doInTransaction(TransactionStatus status) {
        try {
          projectDao.insert(project);
          //          if (100 == 100) throw new Exception("일부러 예외 발생!");
          projectDao.insertMembers(project);
          return 1;

        } catch (Exception e) {
          status.setRollbackOnly(); // rollback()을 호출하라고 지정할 수 있다.
          return 0;
        }
      }
    });
  }

  @Override
  public List<Project> list() throws Exception {
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
    return transactionTemplate.execute(status -> {
      try {
        //1) 프로젝트 정보 변경
        int count = projectDao.update(project);
        Project oldProject = projectDao.findByNo(project.getNo());
        //2) 기존 멤버 비활성화
        if (oldProject.getMembers().size() > 0) {
          projectDao.updateInactiveMembers(oldProject);
        }
        //3) 선택한 멤버 활성화
        if (project.getMembers().size() > 0) {
          projectDao.updateActiveMembers(project);
        }
        List<Member> addMembers = minusMembers(
            project.getMembers(),
            oldProject.getMembers());
        //4) 새로 선택한 멤버 추가
        if (addMembers.size() > 0) {
          Project updateMembersProject = new Project();
          updateMembersProject.setNo(project.getNo());
          updateMembersProject.setMembers(addMembers);
          projectDao.insertMembers(updateMembersProject);
        }
        return count;
      } catch (Exception e) {
        // RuntimeException을 던지면 호출한 쪽에서 rollback()을 호출할 것이다.
        throw new RuntimeException(e);
      }
    });

  }

  private List<Member> minusMembers(List<Member> g1, List<Member> g2) {
    ArrayList<Member> result = new ArrayList<>();
    outerLoop:
      for (Member m : g1) {
        for (Member m2 : g2) {
          if (m.getNo() == m2.getNo()) {
            continue outerLoop;
          }
        }
        result.add(m);
      }
    return result;
  }
}







