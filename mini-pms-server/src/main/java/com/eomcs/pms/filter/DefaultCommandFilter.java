package com.eomcs.pms.filter;

import java.io.PrintWriter;
import java.util.Map;
import com.eomcs.pms.web.Command;
import com.eomcs.pms.web.Request;

// 필터 역할:
// - Command 객체를 찾아 실행한다.
//
public class DefaultCommandFilter implements CommandFilter {
  @SuppressWarnings("unchecked")
  @Override
  public void doFilter(Request request, FilterChain next) throws Exception {
    // Request 보관소에서 context 맵 객체를 꺼낸다.
    Map<String,Object> context = request.getContext();

    // context 맵에서 커맨드 객체가 들어 있는 맵을 꺼낸다.
    Map<String,Command> commandMap = (Map<String,Command>) context.get("commandMap");

    // 사용자가 입력한 명령에 따라 커맨드 객체를 실행한다.
    Command command = commandMap.get(request.getCommandPath());

    PrintWriter out = request.getWriter();

    if (command != null) {
      try {
        command.execute(request);

      } catch (Exception e) {
        out.println("--------------------------------------------------------------");
        out.printf("명령어 실행 중 오류 발생: %s\n", e);
        out.println("--------------------------------------------------------------");
      }
    } else {
      out.println("실행할 수 없는 명령입니다.");
    }

  }
}
