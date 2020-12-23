package com.eomcs.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

// 트랜잭션 다루기 방법1:
// => TransactionManager 를 사용하여 코드로 직접 트랜잭션 관리하기
//
//@Service
public class DefaultProjectService01 implements ProjectService {

  TaskDao taskDao;
  ProjectDao projectDao;
  PlatformTransactionManager txManager;

  public DefaultProjectService01(
      TaskDao taskDao,
      ProjectDao projectDao,
      PlatformTransactionManager txManager) {

    this.projectDao = projectDao;
    this.taskDao = taskDao;
    this.txManager = txManager;
  }

  @Override
  public int delete(int no) throws Exception {
    // 트랜잭션을 어떻게 다룰 지 정의한다.
    //    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    //    def.setName("tx1");
    //    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 트랜잭션 실행 정책에 따라 트랜잭션 관리자를 준비한다.
    //    TransactionStatus status = txManager.getTransaction(def);

    try {

      // 프로젝트를 삭제:
      // 1) 프로젝트 데이터를 삭제하기
      // => 이 방식의 문제는 사용자가 자신의 프로젝트 수행 내역을 조회할 때
      //    삭제된 프로젝트는 조회할 수 없다는 것이다.
      // => 그래서 실무에서는 실제 데이터를 지우지 않고 감추는 방식을 사용한다.
      //    그래야 사용자가 프로젝트 내역을 조회할 때 모든 정보를 출력할 수 있다.
      //
      //      taskDao.deleteByProjectNo(no);
      //      projectDao.deleteMembers(no);
      //      int count = projectDao.delete(no);
      //
      // 정상적으로 삭제 작업이 수행되었다면 실제 테이블에 반영하라고 서버에 명령한다.
      //      txManager.commit(status);

      // 2) 프로젝트를 비활성 상태로 바꾸기
      // => 이 경우 업데이트가 한 번만 수행되기 때문에 트랜잭션으로 다룰 필요가 없다.
      return projectDao.inactive(no);

    } catch (Exception e) {
      // 삭제 작업 중 오류가 발생하면 마지막 커밋 상태로 돌린다.
      // 즉 작업한 모든 결과를 취소한다.
      //      txManager.rollback(status);

      throw e; // 서비스 객체에서 발생한 예외는 호출자에게 전달한다.
    }
  }

  @Override
  public int add(Project project) throws Exception {
    // 트랜잭션을 어떻게 다룰 지 정의한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    // => 이 메서드를 호출할 때 트랜잭션을 진행 중인 상태라면
    //    그 트랜잭션에 이 메서드의 작업도 포함시킨다.
    // => 만약 이 메서드를 호출할 때 트랜잭션이 진행 중이 아니라면,
    //    이 메서드에서 새 트랜잭션을 진행한다.
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 트랜잭션 실행 정책에 따라 트랜잭션 관리자를 준비한다.
    TransactionStatus status = txManager.getTransaction(def);

    try {
      projectDao.insert(project);

      //if (100 == 100) throw new Exception("일부러 예외 발생!");

      projectDao.insertMembers(project);

      txManager.commit(status);

      return 1;

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    TransactionStatus status = txManager.getTransaction(def);

    try {
      // 프로젝트 정보를 변경한다.
      int count = projectDao.update(project);

      // 프로젝트 멤버를 변경한다.
      // 1) 기존 멤버를 비활성화시킨다.
      //    삭제하지 않고 왜?
      //    - 삭제하면 그 회원이 했던 작업도 모두 삭제되기 때문이다.
      //    - 그러면 프로젝트에서 수행한 작업 기록이 사라진다.
      //
      //    - 이전 프로젝트 정보(멤버 목록 포함)를 가져온다.
      Project oldProject = projectDao.findByNo(project.getNo());

      //    - 이전 프로젝트의 전체 멤버를 비활성 상태로 만든다.
      if (oldProject.getMembers().size() > 0) {
        projectDao.updateInactiveMembers(oldProject);
      }

      // 2) 변경한 프로젝트의 멤버를 활성 상태로 만든다.
      if (project.getMembers().size() > 0) {
        projectDao.updateActiveMembers(project);
      }

      // 3) 프로젝트에 추가한 멤버를 등록한다.
      List<Member> addMembers = minusMembers(
          project.getMembers(),
          oldProject.getMembers());

      if (addMembers.size() > 0) {
        // 파라미터로 받은 프로젝트 객체를 변경하지 않기 위해
        // 새 프로젝트 객체를 만들어 사용한다.
        // => 파라미터 값은 가능한 변경하지 말라!
        Project updateMembersProject = new Project();
        updateMembersProject.setNo(project.getNo());
        updateMembersProject.setMembers(addMembers);

        projectDao.insertMembers(updateMembersProject);
      }

      txManager.commit(status);
      return count;

    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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







