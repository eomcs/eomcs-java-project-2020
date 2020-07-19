package com.eomcs.util;

import java.util.HashMap;

public class RequestMappingHandlerMapping {

  HashMap<String, RequestHandler> handlerMap = new HashMap<>();

  public void addHandler(String name, RequestHandler requestHandler) {
    handlerMap.put(name, requestHandler);
  }

  public RequestHandler getHandler(String name) {
    return handlerMap.get(name);
  }
}
