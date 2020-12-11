package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.service.TaskService;

@Controller
public class TaskDeleteController {

  TaskService taskService;

  public TaskDeleteController(TaskService taskService) {
    this.taskService = taskService;
  }

  @RequestMapping("/task/delete")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    if (taskService.delete(no) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + request.getParameter("projectNo");
  }
}
