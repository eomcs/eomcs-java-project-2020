package com.eomcs.lms.servlet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Servlet {
  void service(ObjectInputStream in, ObjectOutputStream out) throws Exception;
}
