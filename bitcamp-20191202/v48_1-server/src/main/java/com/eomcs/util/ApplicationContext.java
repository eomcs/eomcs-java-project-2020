package com.eomcs.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.apache.ibatis.io.Resources;

// 역할:
// - 클래스를 찾아 객체를 생성한다.
// - 객체가 일을 하는데 필요로하는 의존 객체를 주입한다.
// - 객체를 생성과 소멸을 관리한다.
//
public class ApplicationContext {

  // concrete class를 담을 저장소
  ArrayList<Class<?>> concreteClasses = new ArrayList<>();

  // 객체 저장소
  HashMap<String, Object> objPool = new HashMap<>();

  public ApplicationContext(String packageName, HashMap<String, Object> beans) throws Exception {
    // Map에 들어 있는 객체를 먼저 객체풀에 보관한다.
    Set<String> keySet = beans.keySet();
    for (String key : keySet) {
      objPool.put(key, beans.get(key));
    }

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

  public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
    // 특정 애노테이션이 붙은 객체를 찾아 보자.

    // => 객체 이름을 저장할 목록을 준비한다.
    ArrayList<String> beanNames = new ArrayList<>();

    // => 객체풀에서 전체 객체의 이름을 꺼낸다.
    Set<String> beanNameSet = objPool.keySet();
    for (String beanName : beanNameSet) {
      // 객체풀에서 이름을 이용하여 객체를 한 꺼낸다.
      Object obj = objPool.get(beanName);

      // 해당 객체에 파라미터로 지정한 애노테이션이 붙었는지 알아낸다.
      if (obj.getClass().getAnnotation(annotationType) != null) {
        beanNames.add(beanName);
      }
    }

    // ArrayList에서 문자열을 배열로 받는다.
    String[] names = new String[beanNames.size()];
    beanNames.toArray(names);
    return names;
  }

  public void printBeans() {
    System.out.println("-----------------------------------");
    Set<String> beanNames = objPool.keySet();
    for (String beanName : beanNames) {
      System.out.printf("%s =====> %s\n", //
          beanName, // 객체 이름
          objPool.get(beanName).getClass().getName() // 클래스명
      );
    }
  }

  // 객체 이름으로 객체를 찾아 꺼내준다.
  public Object getBean(String name) {
    return objPool.get(name);
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
    objPool.put(getBeanName(clazz), obj);
    System.out.println(clazz.getName() + " 객체 생성!");

    return obj;
  }

  private String getBeanName(Class<?> clazz) {
    Component compAnno = clazz.getAnnotation(Component.class);
    if (compAnno == null || compAnno.value().length() == 0) {
      // @Component 애노테이션이 없거나 이름을 지정하지 않았으면
      // 클래스 이름을 빈의 이름으로 사용한다.
      return clazz.getName();
    }
    return compAnno.value();
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
    Collection<?> objs = objPool.values();
    for (Object obj : objs) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }

    Class<?> availableClass = findAvailableClass(type);
    if (availableClass == null) {
      return null;
    }

    return createObject(availableClass);
  }

  private Class<?> findAvailableClass(Class<?> type) throws Exception {
    for (Class<?> clazz : concreteClasses) {
      if (type.isInterface()) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interfaceInfo : interfaces) {
          if (interfaceInfo == type) {
            return clazz;
          }
        }
      } else if (isChildClass(clazz, type)) {
        // 파라미터가 클래스라면,
        // 각각의 클래스에 대해 같은 타입이거나 수퍼 클래스인지 검사한다.
        return clazz;
      }
    }
    return null;
  }

  private boolean isChildClass(Class<?> clazz, Class<?> type) {
    // 수퍼 클래스로 따라 올라가면서 같은 타입인지 검사한다.
    if (clazz == type) {
      return true;
    }

    if (clazz == Object.class) {
      // 더 이상 상위 클래스가 없다면,
      return false;
    }

    return isChildClass(clazz.getSuperclass(), type);
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
        if (isComponentClass(clazz)) {
          concreteClasses.add(clazz);
        }
      } else {
        findClasses(f, className);
      }
    }
  }

  private boolean isComponentClass(Class<?> clazz) {
    if (clazz.isInterface() // 인터페이스인 경우
        || clazz.isEnum() // Enum 타입인 경우
        || Modifier.isAbstract(clazz.getModifiers()) // 추상 클래스인 경우
    ) {
      return false; // 이런 클래스를 객체를 생성할 수 없다.
    }

    // 클래스에서 @Component 애노테이션 정보를 추출한다.
    Component compAnno = clazz.getAnnotation(Component.class);
    if (compAnno == null) {
      return false;
    }

    // 오직 @Component 애노테이션이 붙은 일반 클래스만이 객체 생성 대상이다.
    return true;
  }
}
