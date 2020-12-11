package com.eomcs.pms.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.io.Resources;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet(value = "/app/*", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  Map<String,Object> beanContainer;
  Map<String,Controller> controllerMap;

  @SuppressWarnings("unchecked")
  @Override
  public void init() throws ServletException {
    // ContextLoaderListener가 준비한 Service 객체를 사용하여
    // 페이지 컨트롤러를 준비한다.
    beanContainer =
        (Map<String,Object>) this.getServletContext().getAttribute("beanContainer");

    try {
      // 페이지 컨트롤러가 있는 패키지의 파일 경로를 알아내기
      // => Mybatis 에서 제공하는 클래스의 도움을 받는다.
      File path = Resources.getResourceAsFile("com/eomcs/pms/web");

      // => 파일 경로에 URL 인코딩 문자가 들어 있으면 제거한다.
      String decodedFilePath = URLDecoder.decode(path.getCanonicalPath(), "UTF-8");

      // => URL 디코딩된 파일 경로를 가지고 새로 파일 경로를 만든다.
      File controllerPackagePath = new File(decodedFilePath);

      System.out.println(controllerPackagePath.getCanonicalPath());

      // 해당 패키지에 들어 있는 페이지 컨트롤러 클래스를 찾아 인스턴스를 생성한다.
      controllerMap = createControllers(controllerPackagePath, "com.eomcs.pms.web");

    } catch (Exception e) {
      System.out.println("DispatcherServlet 에서 페이지 컨트롤러를 준비하는 중 오류 발생!");
      e.printStackTrace();
    }
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 예) 요청 URL => /app/board/list
    //String servletPath = request.getServletPath(); // => /app
    String controllerPath = request.getPathInfo(); // => /board/list

    // 페이지 컨트롤러에게 위임한다.
    // => 페이지 컨트롤러 맵에서 클라이언트의 요청을 처리할 객체를 꺼낸다.
    Controller controller = controllerMap.get(controllerPath);
    if (controller == null) {
      request.setAttribute("exception", new Exception("요청을 처리할 수 없습니다."));
      request.getRequestDispatcher("/error.jsp").forward(request, response);
      return;
    }

    // => 요청을 처리할 페이지 컨트롤러를 찾았으면 규칙에 따라 메서드를 호출한다.
    try {
      String viewName = controller.execute(request, response);
      if (viewName.startsWith("redirect:")) {
        response.sendRedirect(viewName.substring(9));
        return;
      }
      request.getRequestDispatcher(viewName).forward(request, response);

    } catch (Exception e) {
      request.setAttribute("exception", e);
      request.getRequestDispatcher("/error.jsp").forward(request, response);
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
