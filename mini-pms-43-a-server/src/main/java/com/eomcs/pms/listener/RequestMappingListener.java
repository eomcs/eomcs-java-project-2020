package com.eomcs.pms.listener;

import java.util.HashMap;
import java.util.Map;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.handler.BoardAddCommand;
import com.eomcs.pms.handler.BoardDeleteCommand;
import com.eomcs.pms.handler.BoardDetailCommand;
import com.eomcs.pms.handler.BoardListCommand;
import com.eomcs.pms.handler.BoardSearchCommand;
import com.eomcs.pms.handler.BoardUpdateCommand;
import com.eomcs.pms.handler.CalculatorCommand;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.HelloCommand;
import com.eomcs.pms.handler.LoginCommand;
import com.eomcs.pms.handler.LogoutCommand;
import com.eomcs.pms.handler.MemberAddCommand;
import com.eomcs.pms.handler.MemberDeleteCommand;
import com.eomcs.pms.handler.MemberDetailCommand;
import com.eomcs.pms.handler.MemberListCommand;
import com.eomcs.pms.handler.MemberUpdateCommand;
import com.eomcs.pms.handler.ProjectAddCommand;
import com.eomcs.pms.handler.ProjectDeleteCommand;
import com.eomcs.pms.handler.ProjectDetailCommand;
import com.eomcs.pms.handler.ProjectDetailSearchCommand;
import com.eomcs.pms.handler.ProjectListCommand;
import com.eomcs.pms.handler.ProjectSearchCommand;
import com.eomcs.pms.handler.ProjectUpdateCommand;
import com.eomcs.pms.handler.TaskAddCommand;
import com.eomcs.pms.handler.TaskDeleteCommand;
import com.eomcs.pms.handler.TaskDetailCommand;
import com.eomcs.pms.handler.TaskListCommand;
import com.eomcs.pms.handler.TaskUpdateCommand;
import com.eomcs.pms.handler.WhoamiCommand;
import com.eomcs.pms.service.BoardService;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

// 클라이언트 요청을 처리할 커맨드 객체를 준비한다.
public class RequestMappingListener implements ApplicationContextListener {

  @Override
  public void contextInitialized(Map<String,Object> context) {
    try {
      // context 보관소에 저장되어 있는 서비스 객체를 꺼낸다.
      // 왜? Command 객체에 주입하기 위해!
      BoardService boardService = (BoardService) context.get("boardService");
      MemberService memberService = (MemberService) context.get("memberService");
      ProjectService projectService = (ProjectService) context.get("projectService");
      TaskService taskService = (TaskService) context.get("taskService");

      // 테스트 용 로그인 사용자 정보 가져오기
      //Member member = memberService.get("aaa@test.com", "1111");
      //context.put("loginUser", member);

      // Command 구현체 생성 및 commandMap 객체 준비
      Map<String,Command> commandMap = new HashMap<>();

      // 클라이언트의 요청을 처리할 커맨드 객체를 생성한다.
      commandMap.put("/board/add", new BoardAddCommand(boardService));
      commandMap.put("/board/list", new BoardListCommand(boardService));
      commandMap.put("/board/detail", new BoardDetailCommand(boardService));
      commandMap.put("/board/update", new BoardUpdateCommand(boardService));
      commandMap.put("/board/delete", new BoardDeleteCommand(boardService));
      commandMap.put("/board/search", new BoardSearchCommand(boardService));

      commandMap.put("/member/add", new MemberAddCommand(memberService));
      commandMap.put("/member/list", new MemberListCommand(memberService));
      commandMap.put("/member/detail", new MemberDetailCommand(memberService));
      commandMap.put("/member/update", new MemberUpdateCommand(memberService));
      commandMap.put("/member/delete", new MemberDeleteCommand(memberService));

      commandMap.put("/project/add", new ProjectAddCommand(projectService, memberService));
      commandMap.put("/project/list", new ProjectListCommand(projectService));
      commandMap.put("/project/detail", new ProjectDetailCommand(projectService, taskService));
      commandMap.put("/project/update", new ProjectUpdateCommand(projectService));
      commandMap.put("/project/delete", new ProjectDeleteCommand(projectService));
      commandMap.put("/project/search", new ProjectSearchCommand(projectService));
      commandMap.put("/project/detailSearch", new ProjectDetailSearchCommand(projectService));

      commandMap.put("/task/add", new TaskAddCommand(taskService, projectService, memberService));
      commandMap.put("/task/list", new TaskListCommand(taskService));
      commandMap.put("/task/detail", new TaskDetailCommand(taskService));
      commandMap.put("/task/update", new TaskUpdateCommand(taskService, projectService, memberService));
      commandMap.put("/task/delete", new TaskDeleteCommand(taskService));

      commandMap.put("/login", new LoginCommand(memberService));
      commandMap.put("/logout", new LogoutCommand());
      commandMap.put("/whoami", new WhoamiCommand());

      commandMap.put("/hello", new HelloCommand());
      commandMap.put("/calc", new CalculatorCommand());

      // 커맨드 객체만 모아 놓은 상자를 context 맵이라는 큰 상자에 담는다.
      context.put("commandMap", commandMap);

    } catch (Exception e) {
      System.out.println("서비스 객체를 준비하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
  }
}
