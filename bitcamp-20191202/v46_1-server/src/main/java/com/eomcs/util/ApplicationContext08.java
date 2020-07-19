package com.eomcs.util;

import java.io.File;
import java.lang.reflect.Modifier;
import org.apache.ibatis.io.Resources;

// 역할:
// - 클래스를 찾아 객체를 생성한다.
// - 객체가 일을 하는데 필요로하는 의존 객체를 주입한다.
// - 객체를 생성과 소멸을 관리한다.
//
public class ApplicationContext08 {

  public ApplicationContext08(String packageName) throws Exception {
    File path = Resources.getResourceAsFile(packageName.replace('.', '/'));

    findClasses(path, packageName);
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
          System.out.println("ApplicationContext: " + className);
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
