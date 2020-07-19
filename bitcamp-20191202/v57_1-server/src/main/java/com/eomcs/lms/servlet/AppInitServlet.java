package com.eomcs.lms.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.eomcs.lms.AppConfig;

@WebServlet(value = "/AppInitServlet", loadOnStartup = 1)
public class AppInitServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  static Logger logger = LogManager.getLogger(AppInitServlet.class);

  @Override
  public void init() throws ServletException {
    try {
      ApplicationContext iocContainer = new AnnotationConfigApplicationContext(//
          AppConfig.class);
      printBeans(iocContainer);

      ServletContext servletContext = getServletContext();
      servletContext.setAttribute("iocContainer", iocContainer);
      logger.debug("----------------------------");

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
}
