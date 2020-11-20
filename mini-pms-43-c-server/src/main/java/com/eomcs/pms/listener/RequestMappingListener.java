package com.eomcs.pms.listener;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.CommandAnno;

// 클라이언트 요청을 처리할 커맨드 객체를 준비한다.
public class RequestMappingListener implements ApplicationContextListener {

  Map<String,Object> context;

  @Override
  public void contextInitialized(Map<String,Object> context) {
    try {
      // 다른 메서드에서 context 맵 객체를 사용할 수 있도록
      // 인스턴스 필드에 저장한다.
      this.context = context;

      // 커맨드 클래스가 있는 패키지의 파일 경로를 알아내기
      // => Mybatis 에서 제공하는 클래스의 도움을 받는다.
      File path = Resources.getResourceAsFile("com/eomcs/pms/handler");

      // => 파일 경로에 URL 인코딩 문자가 들어 있으면 제거한다.
      String decodedFilePath = URLDecoder.decode(path.getCanonicalPath(), "UTF-8");

      // => URL 디코딩된 파일 경로를 가지고 새로 파일 경로를 만든다.
      File commandPackagePath = new File(decodedFilePath);

      System.out.println(commandPackagePath.getCanonicalPath());

      // 해당 패키지의 있는 커맨드 클래스를 찾아 인스턴스를 생성한다.
      Map<String,Object> commandMap = createCommands(commandPackagePath, "com.eomcs.pms.handler");

      // 커맨드 객체만 모아 놓은 상자를 context 맵이라는 큰 상자에 담는다.
      context.put("commandMap", commandMap);

      // 테스트 용 로그인 사용자 정보 가져오기
      //Member member = memberService.get("aaa@test.com", "1111");
      //context.put("loginUser", member);


    } catch (Exception e) {
      System.out.println("서비스 객체를 준비하는 중에 오류 발생!");
      e.printStackTrace();
    }
  }

  private Map<String,Object> createCommands(File packagePath, String packageName) {
    HashMap<String,Object> commandMap = new HashMap<>();

    File[] files = packagePath.listFiles((dir, name) -> name.endsWith(".class"));

    for (File f : files) {
      // 파일 정보를 가지고 클래스 이름을 알아낸다.
      String className = String.format("%s.%s",
          packageName,
          f.getName().replace(".class", ""));
      try {
        // 클래스 이름(패키지명 포함)을 사용하여 .class 파일을 로딩한다.
        Class<?> clazz = Class.forName(className);

        // 패키지에서 찾은 클래스가 Command 인터페이스를 구현한 클래스가 아니라면,
        // 생성자를 찾지 말고 다음 클래스로 이동한다.
        Class<?>[] interfaces = clazz.getInterfaces();
        boolean isCommand = false;
        for (Class<?> c : interfaces) {
          if (c == Command.class) {
            isCommand = true;
            break;
          }
        }

        if (!isCommand) continue; // 이 클래스는 Command 구현체가 아니다.

        // 커맨드 클래스에 붙여 놓은 @CommandAnno 애노테이션을 정보를 가져온다.
        CommandAnno commandAnno = clazz.getAnnotation(CommandAnno.class);

        // @CommandAnno 애노테이션이 클래스에 붙어 있지 않다면,
        // 해당 커맨드를 저장할 수 없기 때문에 객체를 생성하지 않는다.
        if (commandAnno == null) continue;

        // 클래스의 생성자 정보를 알아낸다.
        Constructor<?> constructor = clazz.getConstructors()[0];

        // 생성자의 파라미터 정보를 알아낸다.
        Parameter[] params = constructor.getParameters();

        // 생성자를 호출할 때 넘겨 줄 파라미터 값을 담을 배열을 준비한다.
        Object[] args = new Object[params.length];

        int i = 0;
        for (Parameter param : params) {
          args[i++] = findDependency(param.getType());
        }

        Object command = constructor.newInstance(args);
        System.out.println(command.getClass().getName() + " 객체 생성 성공!");

        // @CommandAnno 애노테이션에 지정한 커맨드 객체의 이름을 가져와서,
        // 커맨드 객체를 저장할 때 key 로 사용한다.
        commandMap.put(commandAnno.value(), command);

      } catch (Exception e) {
        System.out.println(className + " 로딩 중 오류 발생!");
      }
    }
    return commandMap;
  }

  private Object findDependency(Class<?> type) {
    // context 맵에서 해당 타입의 객체를 찾는다.

    // 1) context 맵에 보관된 모든 객체를 꺼낸다.
    Collection<?> objs = context.values();

    // 2) 각 객체가 파라미터로 받은 타입의 인스턴스인지 확인한다.
    for (Object obj : objs) {
      if (type.isInstance(obj)) return obj;
    }
    return null;
  }

  @Override
  public void contextDestroyed(Map<String,Object> context) {
  }
}
