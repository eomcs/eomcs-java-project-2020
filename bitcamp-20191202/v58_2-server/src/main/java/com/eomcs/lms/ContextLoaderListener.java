package com.eomcs.lms;

import java.lang.reflect.Method;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import com.eomcs.util.RequestHandler;
import com.eomcs.util.RequestMapping;
import com.eomcs.util.RequestMappingHandlerMapping;

// 서블릿 컨테이너가 시작하거나 종료할 때
// 이 클래스의 객체에 대해 메서드를 호출한다.
// 즉 이 클래스는 서블릿 컨테이너의 시작과 종료에 대해 알림을 받는다.
// 조건:
// => javax.servlet.ServletContextListener 인터페이스를 구현해야 한다.
//
@WebListener // 이 애노테이션을 붙이면 서블릿 컨테이너가 이 객체를 관리한다.
public class ContextLoaderListener implements ServletContextListener {

  static Logger logger = LogManager.getLogger(ContextLoaderListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // 서블릿 컨테이너가 실행할 때
    // 실행한 사실을 알기기 위하여 모든 ServletContextListener에 대해
    // 이 메서드를 호출한다.
    // => 보통 이 메서드에서는 웹 애플리케이션이 실행되는 동안 사용할
    // 객체를 준비한다.
    // => 이 프로젝트에서는 Spring IoC 컨테이너를 준비한다.

    try {
      // Spring IoC 컨테이너 준비
      ApplicationContext iocContainer = new AnnotationConfigApplicationContext(//
          // Spring IoC 컨테이너의 설정 정보를 담고 있는 클래스 타입을 지정.
          AppConfig.class);
      printBeans(iocContainer);

      // 준비한 Spring IoC 컨테이너를 ServletContext 보관소에 저장한다.
      // => 이 객체는 웹애플리케이션 설정 정보를 제공한다.
      // => 또한 서블릿들이 공유할 객체를 담는 바구니 역할도 겸한다.
      // => 모든 "웹 컴포넌트(서블릿,리스너,필터,JSP)"들이
      // 공유할 객체를 보관하는 저장소이다.
      ServletContext servletContext = sce.getServletContext();

      // 서블릿이 사용할 수 있게 ServletContext에 담아 둔다.
      // => Spring IoC 컨테이너는 모든 서블릿이 사용해야 하는 객체이다.
      servletContext.setAttribute("iocContainer", iocContainer);

      logger.debug("----------------------------");

      logger.debug("@RequestMapping 메서드를 찾기");

      // @Component 애노테이션이 붙은 객체를 찾는다.
      RequestMappingHandlerMapping handlerMapper = //
          new RequestMappingHandlerMapping();
      String[] beanNames = iocContainer.getBeanNamesForAnnotation(Component.class);
      for (String beanName : beanNames) {
        Object component = iocContainer.getBean(beanName);

        // @RequestHandler가 붙은 메서드를 찾는다.
        Method method = getRequestHandler(component.getClass());
        if (method != null) {
          // 클라이언트 명령을 처리하는 메서드 정보를 준비한다.
          RequestHandler requestHandler = new RequestHandler(method, component);

          // 명령을 처리할 메서드를 찾을 수 있도록
          // 명령 이름으로 메서드 정보를 저장한다.
          handlerMapper.addHandler(requestHandler.getPath(), requestHandler);

          // @RequestMapping 메서드가 들어있는 클래스 이름을 로그로 남긴다.
          logger.debug("==> " + component.getClass().getName());
        }
      }

      // ServerApp 에서 request handler를 사용할 수 있도록 공유한다.
      servletContext.setAttribute("handlerMapper", handlerMapper);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void printBeans(ApplicationContext appCtx) {
    logger.debug("Spring IoC 컨테이너에 들어있는 객체들:");
    String[] beanNames = appCtx.getBeanDefinitionNames();
    for (String beanName : beanNames) {
      logger.debug(String.format("%s =======> %s", //
          beanName, //
          appCtx.getBean(beanName).getClass().getName()));
    }

  }

  private Method getRequestHandler(Class<?> type) {
    // 클라이언트 명령을 처리할 메서드는 public 이기 때문에
    // 클래스에서 public 메서드만 조사한다.
    Method[] methods = type.getMethods();
    for (Method m : methods) {
      // 메서드에 @RequestMapping 애노테이션이 붙었는지 검사한다.
      RequestMapping anno = m.getAnnotation(RequestMapping.class);
      if (anno != null) {
        return m;
      }
    }

    return null;
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // 서블릿 컨테이너가 종료되기 직전에 호출된다.
    // 주로 서블릿이 사용한 자원을 해제시키는 코드를 둔다.
  }
}
