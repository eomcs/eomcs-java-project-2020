package com.eomcs.pms.web;

import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

  @Autowired ProjectService projectService;
  @Autowired TaskService taskService;

  @RequestMapping(value="add", method=RequestMethod.GET)
  public ModelAndView addForm(int projectNo) throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.addObject("project", projectService.get(projectNo));
    mv.setViewName("/task/form.jsp");
    return mv;
  }

  @RequestMapping(value="add", method=RequestMethod.POST)
  public String add(Task task, int ownerNo) throws Exception {
    task.setOwner(new Member().setNo(ownerNo));
    taskService.add(task);
    return "redirect:../project/detail?no=" + task.getProjectNo();
  }

  @RequestMapping("delete")
  public String delete(int no, int projectNo) throws Exception {
    if (taskService.delete(no) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + projectNo;
  }

  @RequestMapping("detail")
  public ModelAndView detail(int no) throws Exception {

    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 작업이 없습니다.");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("task", task);
    mv.addObject("project", projectService.get(task.getProjectNo()));
    mv.setViewName("/task/detail.jsp");
    return mv;
  }

  @RequestMapping("update")
  public String update(Task task, int ownerNo) throws Exception {
    task.setOwner(new Member().setNo(ownerNo));
    if (taskService.update(task) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + task.getProjectNo();
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
