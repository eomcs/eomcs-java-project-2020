package com.eomcs.pms.web.listener;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.dao.mariadb.BoardDaoImpl;
import com.eomcs.pms.dao.mariadb.MemberDaoImpl;
import com.eomcs.pms.dao.mariadb.ProjectDaoImpl;
import com.eomcs.pms.dao.mariadb.TaskDaoImpl;
import com.eomcs.pms.service.BoardService;
import com.eomcs.pms.service.DefaultBoardService;
import com.eomcs.pms.service.DefaultMemberService;
import com.eomcs.pms.service.DefaultProjectService;
import com.eomcs.pms.service.DefaultTaskService;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.pms.web.Controller;
import com.eomcs.pms.web.RequestMapping;
import com.eomcs.util.SqlSessionFactoryProxy;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

  // 객체를 담아 두는 보관소
  // => bean?
  //    '자바' 이름은 자바 언어를 만든 팀에서 '자바'라는 커피 이름에서 따왔다고 한다.
  //    그래서 자바 객체를 '커피콩' 으로 비유하여
  //    종종 'bean' 이라는 애칭으로 부른다.
  //
  Map<String,Object> beanContainer = new HashMap<>();

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 시스템에서 사용할 객체를 준비한다.
    try {
      // Mybatis 객체 준비
      SqlSessionFactoryProxy sqlSessionFactory = new SqlSessionFactoryProxy(
          new SqlSessionFactoryBuilder().build(
              Resources.getResourceAsStream("com/eomcs/pms/conf/mybatis-config.xml")));

      // DAO 구현체 생성
      BoardDao boardDao = new BoardDaoImpl(sqlSessionFactory);
      MemberDao memberDao = new MemberDaoImpl(sqlSessionFactory);
      ProjectDao projectDao = new ProjectDaoImpl(sqlSessionFactory);
      TaskDao taskDao = new TaskDaoImpl(sqlSessionFactory);

      // Service 구현체 생성
      BoardService boardService = new DefaultBoardService(boardDao);
      MemberService memberService = new DefaultMemberService(memberDao);
      ProjectService projectService = new DefaultProjectService(taskDao, projectDao, sqlSessionFactory);
      TaskService taskService = new DefaultTaskService(taskDao);

      // 서비스 객체를 빈 컨테이너에 보관해 둔다.
      beanContainer.put("boardService", boardService);
      beanContainer.put("memberService", memberService);
      beanContainer.put("projectService", projectService);
      beanContainer.put("taskService", taskService);

      // 다른 객체가 사용할 수 있도록 context 맵 보관소에 저장해둔다.
      ServletContext ctx = sce.getServletContext();

      // 페이지 컨트롤러가 있는 패키지의 파일 경로를 알아내기
      // => Mybatis 에서 제공하는 클래스의 도움을 받는다.
      File path = Resources.getResourceAsFile("com/eomcs/pms/web");

      // => 파일 경로에 URL 인코딩 문자가 들어 있으면 제거한다.
      String decodedFilePath = URLDecoder.decode(path.getCanonicalPath(), "UTF-8");

      // => URL 디코딩된 파일 경로를 가지고 새로 파일 경로를 만든다.
      File controllerPackagePath = new File(decodedFilePath);

      System.out.println(controllerPackagePath.getCanonicalPath());

      // 해당 패키지에 들어 있는 페이지 컨트롤러 클래스를 찾아 인스턴스를 생성한다.
      Map<String,Controller> controllerMap =
          createControllers(controllerPackagePath, "com.eomcs.pms.web");

      // 페이지 컨트롤러 객체만 모아 놓은 상자를
      // ServletContext 보관소에 담는다.
      ctx.setAttribute("controllerMap", controllerMap);

      // 웹 컴포넌트(필터,리스너,서블릿/JSP)가 서비스 객체를 직접 사용할 수 있도록
      // ServletContext 보관소에 beanContainer를 저장해 둔다.
      ctx.setAttribute("beanContainer", beanContainer);

    } catch (Exception e) {
      System.out.println("Mybatis 및 DAO, 서비스 객체, 컨트롤러 준비 중 오류 발생!");
      e.printStackTrace();
    }
  }

  private Map<String,Controller> createControllers(File packagePath, String packageName) {
    HashMap<String,Controller> controllerMap = new HashMap<>();

    File[] files = packagePath.listFiles((dir, name) -> name.endsWith(".class"));

    for (File f : files) {
      // 파일 정보를 가지고 클래스 이름을 알아낸다.
      String className = String.format("%s.%s",
          packageName,
          f.getName().replace(".class", ""));
      try {
        // 클래스 이름(패키지명 포함)을 사용하여 .class 파일을 로딩한다.
        Class<?> clazz = Class.forName(className);

        // 패키지에서 찾은 클래스가 Controller 인터페이스를 구현한 클래스가 아니라면,
        // 생성자를 찾지 말고 다음 클래스로 이동한다.
        Class<?>[] interfaces = clazz.getInterfaces();

        boolean isController = false;
        for (Class<?> c : interfaces) {
          if (c == Controller.class) {
            isController = true;
            break;
          }
        }

        if (!isController) continue; // 이 클래스는 Controller 구현체가 아니다.

        // 페이지 컨트롤러 클래스에 붙여 놓은
        // @RequestMapping 애노테이션 정보를 가져온다.
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);

        // @RequestMapping 애노테이션이 클래스에 붙어 있지 않다면,
        // 해당 해당 페이지 컨트롤러를 저장할 수 없기 때문에 객체를 생성하지 않는다.
        if (requestMapping == null) continue;

        // 클래스의 생성자 정보를 알아낸다.
        @SuppressWarnings("unchecked")
        Constructor<Controller> constructor =
        (Constructor<Controller>) clazz.getConstructors()[0];

        // 생성자의 파라미터 정보를 알아낸다.
        Parameter[] params = constructor.getParameters();

        // 생성자를 호출할 때 넘겨 줄 파라미터 값을 담을 배열을 준비한다.
        Object[] args = new Object[params.length];

        int i = 0;
        for (Parameter param : params) {
          args[i++] = findDependency(param.getType());
        }

        Controller controller = constructor.newInstance(args);

        // @RequestMapping 애노테이션에 지정된 이름을 가져와서,
        // 페이지 컨트롤러 객체를 저장할 때 key 로 사용한다.
        controllerMap.put(requestMapping.value(), controller);

        System.out.printf("%s => %s 객체 생성\n",
            requestMapping.value(),
            controller.getClass().getName());

      } catch (Exception e) {
        System.out.println(className + " 로딩 중 오류 발생!");
      }
    }
    return controllerMap;
  }

  private Object findDependency(Class<?> type) {
    // beanContainer 맵에서 파라미터로 넘어온 타입의 객체를 찾는다.

    // 1) beanContainer 맵에 보관된 모든 객체를 꺼낸다.
    Collection<?> objs = beanContainer.values();

    // 2) 각 객체가 파라미터로 받은 타입의 인스턴스인지 확인한다.
    for (Object obj : objs) {
      if (type.isInstance(obj)) return obj;
    }
    return null;
  }

}
