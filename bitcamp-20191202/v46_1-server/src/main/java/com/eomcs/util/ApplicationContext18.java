package com.eomcs.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.ibatis.io.Resources;

// 역할:
// - 클래스를 찾아 객체를 생성한다.
// - 객체가 일을 하는데 필요로하는 의존 객체를 주입한다.
// - 객체를 생성과 소멸을 관리한다.
//
public class ApplicationContext18 {

  // concrete class를 담을 저장소
  ArrayList<Class<?>> concreteClasses = new ArrayList<>();

  // 객체 저장소
  HashMap<String, Object> objPool = new HashMap<>();

  public ApplicationContext18(String packageName) throws Exception {
    File path = Resources.getResourceAsFile(packageName.replace('.', '/'));

    findClasses(path, packageName);

    for (Class<?> clazz : concreteClasses) {
      try {
        createObject(clazz);
      } catch (Exception e) {
        System.out.printf("%s 클래스의 객체를 생성할 수 없습니다.\n", //
            clazz.getName());
      }
    }
  }

  private Object createObject(Class<?> clazz) throws Exception {
    Constructor<?> constructor = clazz.getConstructors()[0];
    Parameter[] params = constructor.getParameters();

    // 생성자의 파라미터 값 준비한다.
    System.out.printf("%s()\n", clazz.getName());
    Object[] paramValues = getParameterValues(params);

    // 객체를 생성한다.
    Object obj = constructor.newInstance(paramValues);

    // 객체풀에 보관한다.
    objPool.put(clazz.getName(), obj);
    System.out.println(clazz.getName() + " 객체 생성!");

    return obj;
  }

  private Object[] getParameterValues(Parameter[] params) throws Exception {
    Object[] values = new Object[params.length];
    System.out.println("파라미터 값: {");
    for (int i = 0; i < values.length; i++) {
      values[i] = getParameterValue(params[i].getType());
      System.out.printf("%s ==> %s,\n", //
          params[i].getType().getSimpleName(), //
          values[i].getClass().getName());
    }
    System.out.println("}");
    return values;
  }

  private Object getParameterValue(Class<?> type) throws Exception {
    // 먼저 객체 보관소에 파라미터 객체가 있는지 검사한다.
    Collection<?> objs = objPool.values();
    for (Object obj : objs) {
      // 있으면, 같은 객체를 또 만들지 않고 기존의 생성된 객체를 리턴한다.
      if (type.isInstance(obj)) {
        return obj;
      }
    }

    // 객체풀에 파라미터 타입에 맞는 객체가 없다면,
    // 파라미터 타입에 맞는 클래스를 찾는다.
    Class<?> availableClass = findAvailableClass(type);
    if (availableClass == null) {
      // 파라미터에 해당하는 적절한 클래스를 찾지 못했으면
      // 파라미터 객체를 생성할 수 없다.
      return null;
    }

    return createObject(availableClass);
  }

  private Class<?> findAvailableClass(Class<?> type) throws Exception {
    return null;
  }

  private void findClasses(File path, String packageName) throws Exception {
    File[] files = path.listFiles(file -> {
      if (file.isDirectory() //
          || (file.getName().endsWith(".class")//
              && !file.getName().contains("$")))
        return true;
      return false;
    });
    for (File f : files) {
      String className = String.format("%s.%s", //
          packageName, //
          f.getName().replace(".class", ""));
      if (f.isFile()) {
        Class<?> clazz = Class.forName(className);
        if (isConcreteClass(clazz)) {
          concreteClasses.add(clazz);
        }
      } else {
        findClasses(f, className);
      }
    }
  }

  private boolean isConcreteClass(Class<?> clazz) {
    if (clazz.isInterface() // 인터페이스인 경우
        || clazz.isEnum() // Enum 타입인 경우
        || Modifier.isAbstract(clazz.getModifiers()) // 추상 클래스인 경우
    ) {
      return false; // 이런 클래스를 객체를 생성할 수 없다.
    }
    return true;
  }
}
