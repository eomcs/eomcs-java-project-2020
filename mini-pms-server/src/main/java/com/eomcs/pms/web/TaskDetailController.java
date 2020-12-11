package com.eomcs.pms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@RequestMapping("/task/detail")
public class TaskDetailController implements Controller {

  ProjectService projectService;
  TaskService taskService;

  public TaskDetailController(
      ProjectService projectService,
      TaskService taskService) {
    this.projectService = projectService;
    this.taskService = taskService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=UTF-8");
    int no = Integer.parseInt(request.getParameter("no"));
    Task task = taskService.get(no);
    if (task == null) {
      throw new Exception("해당 작업이 없습니다.");
    }
    request.setAttribute("task", task);
    request.setAttribute("project", projectService.get(task.getProjectNo()));
    return "/task/detail.jsp";
  }
}
