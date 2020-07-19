package com.eomcs.util;

import java.io.File;
import org.apache.ibatis.io.Resources;

// 역할:
// - 클래스를 찾아 객체를 생성한다.
// - 객체가 일을 하는데 필요로하는 의존 객체를 주입한다.
// - 객체를 생성과 소멸을 관리한다.
//
public class ApplicationContext04 {

  public ApplicationContext04(String packageName) throws Exception {
    File path = Resources.getResourceAsFile(packageName.replace('.', '/'));

    // 해당 경로를 뒤져서 모든 클래스의 이름을 알아낸다.
    findClasses(path);
  }

  private void findClasses(File path) {
    File[] files = path.listFiles(file -> {
      if (file.isDirectory() //
          || file.getName().endsWith(".class"))
        return true;
      return false;
    });
    for (File f : files) {
      if (f.isFile()) {
        System.out.println("ApplicationContext: " + f.getName());
      } else {
        findClasses(f);
      }
    }
  }
}
