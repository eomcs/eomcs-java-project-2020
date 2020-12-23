package com.eomcs.pms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

// 트랜잭션 다루기 방법3:
// => @Transactional 애노테이션을 사용하여 트랜잭션으로 묶어 작업해야 하는 메서드를 표시하기.
//    - 단 @Transactional 애노테이션을 처리할 객체를 IoC 컨테이너에 등록해야 한다.
//
//
@Service
public class DefaultProjectService implements ProjectService {

  TaskDao taskDao;
  ProjectDao projectDao;

  public DefaultProjectService(
      TaskDao taskDao,
      ProjectDao projectDao) {
    this.projectDao = projectDao;
    this.taskDao = taskDao;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int delete(int no) throws Exception {

    // 트랜잭션으로 다뤄야 할 작업을 이 메서드에 작성한다.
    //          taskDao.deleteByProjectNo(no);
    //          projectDao.deleteMembers(no);
    //          return projectDao.delete(no);

    return projectDao.inactive(no);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int add(Project project) throws Exception {
    projectDao.insert(project);
    //    if (100 == 100) throw new Exception("일부러 예외 발생!");
    projectDao.insertMembers(project);
    return 1;
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
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public int update(Project project) throws Exception {
    //1) 프로젝트 정보 변경
    int count = projectDao.update(project);

    //2) 기존 멤버 비활성화
    Project oldProject = projectDao.findByNo(project.getNo());
    if (oldProject.getMembers().size() > 0) {
      projectDao.updateInactiveMembers(oldProject);
    }

    //3) 선택한 멤버 활성화
    if (project.getMembers().size() > 0) {
      projectDao.updateActiveMembers(project);
    }

    //4) 새로 선택한 멤버 추가
    List<Member> addMembers = minusMembers(
        project.getMembers(),
        oldProject.getMembers());
    if (addMembers.size() > 0) {
      Project updateMembersProject = new Project();
      updateMembersProject.setNo(project.getNo());
      updateMembersProject.setMembers(addMembers);
      projectDao.insertMembers(updateMembersProject);
    }

    return count;
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







