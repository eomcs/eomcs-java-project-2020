package com.eomcs.pms.web;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@Controller
@RequestMapping("/project")
public class ProjectController {

  @Autowired ProjectService projectService;
  @Autowired MemberService memberService;
  @Autowired TaskService taskService;

  @RequestMapping("form")
  public ModelAndView form() throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.addObject("members", memberService.list());
    mv.setViewName("/project/form.jsp");
    return mv;
  }

  @RequestMapping("add")
  public String add(
      Project project,
      int[] memberNo,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    project.setOwner(loginUser);

    List<Member> memberList = new ArrayList<>();
    if (memberNo != null) {
      for (int no : memberNo) {
        memberList.add(new Member().setNo(no));
      }
    }
    project.setMembers(memberList);

    projectService.add(project);
    return "redirect:list";
  }

  @RequestMapping("delete")
  public String delete(int no) throws Exception {

    if (projectService.delete(no) == 0) {
      throw new Exception("해당 프로젝트가 없습니다.");
    }
    return "redirect:list";
  }

  @RequestMapping("detail")
  public ModelAndView detail(int no) throws Exception {

    Project project = projectService.get(no);
    if (project == null) {
      throw new Exception("해당 프로젝트가 없습니다!");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("project", project);
    mv.addObject("members", memberService.list());
    mv.addObject("tasks", taskService.listByProject(no));
    mv.setViewName("/project/detail.jsp");

    return mv;
  }

  @RequestMapping("list")
  public ModelAndView list(
      String keyword,
      String keywordTitle,
      String keywordOwner,
      String keywordMember) throws Exception {

    ModelAndView mv = new ModelAndView();

    if (keyword != null) {
      mv.addObject("list", projectService.list(keyword));

    } else if (keywordTitle != null) {
      HashMap<String,Object> keywordMap = new HashMap<>();
      keywordMap.put("title", keywordTitle);
      keywordMap.put("owner", keywordOwner);
      keywordMap.put("member", keywordMember);

      mv.addObject("list", projectService.list(keywordMap));

    } else {
      mv.addObject("list", projectService.list());
    }

    mv.setViewName("/project/list.jsp");
    return mv;
  }

  @RequestMapping("update")
  public String update(
      Project project,
      int[] memberNo) throws Exception {

    List<Member> memberList = new ArrayList<>();
    if (memberNo != null) {
      for (int no : memberNo) {
        memberList.add(new Member().setNo(no));
      }
    }
    project.setMembers(memberList);

    if (projectService.update(project) == 0) {
      throw new Exception("해당 프로젝트가 존재하지 않습니다.");
    }
    return "redirect:list";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    // String ===> Date 프로퍼티 에디터 준비
    DatePropertyEditor propEditor = new DatePropertyEditor();

    // WebDataBinder에 프로퍼티 에디터 등록하기
    binder.registerCustomEditor(
        java.util.Date.class, // String을 Date 타입으로 바꾸는 에디터임을 지정한다.
        propEditor // 바꿔주는 일을 하는 프로퍼티 에디터를 등록한다.
        );
  }

  class DatePropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
      try {
        // 클라이언트가 텍스트로 보낸 날짜 값을 java.sql.Date 객체로 만들어 보관한다.
        setValue(java.sql.Date.valueOf(text));
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    }
  }
}
