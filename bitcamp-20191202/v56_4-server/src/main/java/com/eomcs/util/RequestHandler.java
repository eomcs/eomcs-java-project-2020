package com.eomcs.util;

import java.lang.reflect.Method;

public class RequestHandler {
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
}
