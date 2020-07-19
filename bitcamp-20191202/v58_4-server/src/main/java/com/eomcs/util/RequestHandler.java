package com.eomcs.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterNamesScanner;
import com.eomcs.lms.AppConfig;

public class RequestHandler {

  static Logger logger = LogManager.getLogger(AppConfig.class);

  Object bean;
  String path;
  Method method;

  public RequestHandler() {

  }

  public RequestHandler(Method method, Object bean) {
    this.method = method;
    this.bean = bean;
    this.path = getPath(method);
  }

  private String getPath(Method method) {
    // 메서드에 붙은 @RequestMapping 애노테이션을 추출한다.
    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
    return requestMapping.value();
  }

  public Object getBean() {
    return bean;
  }

  public void setBean(Object bean) {
    this.bean = bean;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Map<String, Object> invoke(HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    // 페이지 컨트롤러가 작업한 결과를 받을 바구니를 준비한다.
    HashMap<String, Object> model = new HashMap<>();

    // 메서드의 파라미터 목록을 꺼낸다.
    Parameter[] params = method.getParameters();
    String viewUrl = (String) method.invoke(//
        bean, // 메서드를 호출할 때 사용하는 인스턴스
        getArguments(params, request, response, model) // 메서드에 넘겨 줄 값들
    );

    // request handler를 호출하면,
    // model 객체에는 request handler가 담은 값이 보관되어 있다.
    // 여기에 request handler의 리턴 값(JSP URL)도 함께 보관한다.
    model.put("viewUrl", viewUrl);

    // request handler의 작업 결과물과 JSP URL을 담은 맵 객체를 프론트 컨트롤러에게 리턴한다.
    return model;
  }

  private List<String> getParameterNames(Class<?> type, Method method) throws Exception {
    // => 컴파일할 때 옵션으로 파라미터 이름을 포함하지 않는 한에는
    // 자바 reflection API로는 알아낼 수 없다.
    // => 그러나 .class 파일에는 분명히 파라미터 이름을 들어 있다.
    // => 그것을 꺼내려면 외부 라이브러리를 사용해야 한다.
    //
    Reflections reflections = new Reflections(type, // 클래스 타입
        new MethodParameterNamesScanner() // 파라미터 이름 탐색 플러그인 장착
    );
    List<String> paramNames = reflections.getMethodParamNames(method);

    logger.debug(String.format("%s.%s(", bean.getClass().getName(), method.getName()));
    for (int i = 0; i < method.getParameterCount(); i++) {
      logger.debug(String.format("   %s,", paramNames.get(i)));
    }
    logger.debug(")");

    return paramNames.subList(0, method.getParameterCount());
  }

  private Object[] getArguments(Parameter[] params, HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model) throws Exception {

    // 파라미터 이름 알아내기
    List<String> paramNames = getParameterNames(bean.getClass(), method);

    // 파라미터 값을 담을 배열을 준비한다.
    Object[] args = new Object[params.length];

    // 각 파라미터에 대한 값을 준비한다.
    for (int i = 0; i < params.length; i++) {
      args[i] = getArgument(paramNames.get(i), params[i], request, response, model);
    }

    return args;
  }

  private Object getArgument(String paramName, Parameter param, HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> model) throws Exception {

    Class<?> paramType = param.getType();
    if (paramType == ServletRequest.class || paramType == HttpServletRequest.class) {
      return request;

    } else if (paramType == ServletResponse.class || paramType == HttpServletResponse.class) {
      return response;

    } else if (paramType == HttpSession.class) {
      return request.getSession();

    } else if (paramType == Map.class) {
      return model;

    } else if (paramType == Part.class) {
      return request.getPart(paramName);

    } else if (paramType == Part[].class) {
      ArrayList<Part> values = new ArrayList<>();
      Collection<Part> parts = request.getParts();
      for (Part part : parts) {
        if (part.getName().equals(paramName)) {
          values.add(part);
        }
      }
      return values.toArray(new Part[values.size()]);

    } else if (isPrimitiveType(paramType)) {
      return getPrimitiveValue(paramName, paramType, request);

    } else {
      return getPojoValue(paramName, paramType, request);
    }
  }

  @SuppressWarnings("unchecked")
  private Object getPojoValue(String paramName, Class<?> paramType, HttpServletRequest request)
      throws Exception {

    // 클라이언트가 보낸 데이터를 담을 POJO 객체를 생성한다.
    // => 기본 생성자를 호출하여 인스턴스를 초기화시킨다.
    Object pojo = paramType.getConstructor().newInstance();

    // 세터 메서드를 추출한다.
    Set<Method> methods = ReflectionUtils.getMethods(paramType, ReflectionUtils.withPrefix("set"));
    logger.debug(String.format("%s 의 세터 메서드:", paramType.getName()));

    // 클라이언트가 보낸 데이터 중에서 세터 메서드에 넘겨줄 데이터가 있다면 호출한다.
    for (Method m : methods) {

      // 메서드 이름에서 프로퍼티 명을 추출한다.
      // => 예: setCreatedDate() => "c" + "reatedDate" = createdDate
      String propName = m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4);
      logger.debug("    " + propName);

      // 세터를 호출할 때 넘겨 줄 값을 담을 변수를 준비.
      Object value = null;

      if (isPrimitiveType(m.getParameters()[0].getType())) {
        // 클라이언트가 보낸 데이터 중에서 프로퍼티 이름과 일치하는 데이터가 있다면 꺼낸다.
        value = getPrimitiveValue(propName, m.getParameters()[0].getType(), request);
      }

      // 세터에 넘겨 줄 값을 준비하지 못했드면 다음 메서드로 넘어간다.
      if (value == null) {
        continue;
      }

      logger.debug(String.format("    %s()", m.getName()));
      m.invoke(pojo, value);
    }

    return pojo;
  }

  private Object getPrimitiveValue(String paramName, Class<?> paramType,
      HttpServletRequest request) {

    // 자바 원시 타입일 경우 요청 파라미터에서 값을 찾는다.
    String value = request.getParameter(paramName);
    if (value == null) {
      return null;
    }

    if (paramType == byte.class || paramType == Byte.class) {
      try {
        return Byte.parseByte(value);
      } catch (Exception e) {
        return (byte) 0;
      }
    } else if (paramType == short.class || paramType == Short.class) {
      try {
        return Short.parseShort(value);
      } catch (Exception e) {
        return (short) 0;
      }
    } else if (paramType == int.class || paramType == Integer.class) {
      try {
        return Integer.parseInt(value);
      } catch (Exception e) {
        return 0;
      }
    } else if (paramType == long.class || paramType == Long.class) {
      try {
        return Long.parseLong(value);
      } catch (Exception e) {
        return (long) 0;
      }
    } else if (paramType == float.class || paramType == Float.class) {
      try {
        return Float.parseFloat(value);
      } catch (Exception e) {
        return 0.0f;
      }
    } else if (paramType == double.class || paramType == Double.class) {
      try {
        return Double.parseDouble(value);
      } catch (Exception e) {
        return 0.0;
      }
    } else if (paramType == char.class || paramType == Character.class) {
      try {
        return value.charAt(0);
      } catch (Exception e) {
        return (char) 0;
      }
    } else if (paramType == boolean.class || paramType == Boolean.class) {
      try {
        return Boolean.parseBoolean(value);
      } catch (Exception e) {
        return false;
      }
    } else if (paramType == java.util.Date.class || paramType == java.sql.Date.class) {
      try {
        return java.sql.Date.valueOf(value); // 문자열 형식: "yyyy-MM-dd" 이어야 한다.
      } catch (Exception e) {
        return null;
      }
    }
    return value;
  }

  private boolean isPrimitiveType(Class<?> paramType) {
    if (paramType == byte.class || paramType == Byte.class || paramType == short.class
        || paramType == Short.class || paramType == int.class || paramType == Integer.class
        || paramType == long.class || paramType == Long.class || paramType == float.class
        || paramType == Float.class || paramType == double.class || paramType == Double.class
        || paramType == char.class || paramType == Character.class || paramType == boolean.class
        || paramType == Boolean.class || paramType == String.class
        || paramType == java.util.Date.class || paramType == java.sql.Date.class) {
      return true;
    }
    return false;
  }
}
