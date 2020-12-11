package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.service.TaskService;

@RequestMapping("/task/delete")
public class TaskDeleteController implements Controller {

  TaskService taskService;

  public TaskDeleteController(TaskService taskService) {
    this.taskService = taskService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    int no = Integer.parseInt(request.getParameter("no"));

    if (taskService.delete(no) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + request.getParameter("projectNo");
  }
}
