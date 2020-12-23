package com.eomcs.pms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

  @Autowired ProjectService projectService;
  @Autowired TaskService taskService;

  @GetMapping("add")
  public void addForm(int projectNo, Model model) throws Exception {
    model.addAttribute("project", projectService.get(projectNo));
  }

  @PostMapping("add")
  public String add(Task task, int ownerNo) throws Exception {
    task.setOwner(new Member().setNo(ownerNo));
    taskService.add(task);
    return "redirect:../project/detail?no=" + task.getProjectNo();
  }

  @GetMapping("delete")
  public String delete(int no, int projectNo) throws Exception {
    if (taskService.delete(no) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + projectNo;
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {

    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 작업이 없습니다.");
    }

    model.addAttribute("task", task);
    model.addAttribute("project", projectService.get(task.getProjectNo()));
  }

  @PostMapping("update")
  public String update(Task task, int ownerNo) throws Exception {
    task.setOwner(new Member().setNo(ownerNo));
    if (taskService.update(task) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + task.getProjectNo();
  }
}
