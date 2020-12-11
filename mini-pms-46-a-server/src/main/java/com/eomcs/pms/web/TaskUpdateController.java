package com.eomcs.pms.web;

import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@Controller
public class TaskUpdateController {

  TaskService taskService;

  public TaskUpdateController(TaskService taskService) {
    this.taskService = taskService;
  }

  @RequestMapping("/task/update")
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Task task = new Task();
    task.setNo(Integer.parseInt(request.getParameter("no")));
    task.setContent(request.getParameter("content"));
    task.setDeadline(Date.valueOf(request.getParameter("deadline")));
    task.setStatus(Integer.parseInt(request.getParameter("status")));
    task.setOwner(new Member()
        .setNo(Integer.parseInt(request.getParameter("owner"))));

    if (taskService.update(task) == 0) {
      throw new Exception("해당 작업이 존재하지 않습니다.");
    }
    return "redirect:../project/detail?no=" + request.getParameter("projectNo");
  }
}
