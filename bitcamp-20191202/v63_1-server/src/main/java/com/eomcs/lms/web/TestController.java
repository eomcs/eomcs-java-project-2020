package com.eomcs.lms.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

  static Logger logger = LogManager.getLogger(TestController.class);

  @GetMapping("exam01")
  public void exam01() throws Exception {}

  @GetMapping("exam02")
  public void exam02() throws Exception {}

  @GetMapping("exam03")
  public void exam03() throws Exception {}

  @GetMapping("exam04")
  public void exam04() throws Exception {}

}
