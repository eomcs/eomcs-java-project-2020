package com.eomcs.pms.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;

@Controller
@RequestMapping("/project")
@SessionAttributes("loginUser")
public class ProjectController {

  @Autowired ProjectService projectService;
  @Autowired MemberService memberService;
  @Autowired TaskService taskService;

  @GetMapping("form")
  public Map<String,Object> form() throws Exception {
    Map<String,Object> map = new HashMap<>();
    map.put("members", memberService.list());
    // 페이지 컨트롤러의 요청 핸들러가 Map 객체를 리턴하면,
    // 프런트 컨트롤러는 Map 객체에 들어 있는 값을
    // ServletRequest 보관소로 옮긴다.
    return map;
  }

  @PostMapping("add")
  public String add(
      Project project,
      int[] memberNo,
      @ModelAttribute("loginUser") Member loginUser) throws Exception {

    project.setOwner(loginUser);

    List<Member> memberList = new ArrayList<>();
    if (memberNo != null) {
      for (int no : memberNo) {
        memberList.add(new Member().setNo(no));
      }
    }
    project.setMembers(memberList);

    projectService.add(project);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {

    if (projectService.delete(no) == 0) {
      throw new Exception("해당 프로젝트가 없습니다.");
    }
    return "redirect:list";
  }

  @GetMapping("detail")
  public void detail(int no, Model model) throws Exception {

    Project project = projectService.get(no);
    if (project == null) {
      throw new Exception("해당 프로젝트가 없습니다!");
    }

    model.addAttribute("project", project);
    model.addAttribute("members", memberService.list());
    model.addAttribute("tasks", taskService.listByProject(no));
  }

  @GetMapping("list")
  public void list(
      String keyword,
      String keywordTitle,
      String keywordOwner,
      String keywordMember,
      Model model) throws Exception {

    if (keyword != null) {
      model.addAttribute("list", projectService.list(keyword));

    } else if (keywordTitle != null) {
      HashMap<String,Object> keywordMap = new HashMap<>();
      keywordMap.put("title", keywordTitle);
      keywordMap.put("owner", keywordOwner);
      keywordMap.put("member", keywordMember);

      model.addAttribute("list", projectService.list(keywordMap));

    } else {
      model.addAttribute("list", projectService.list());
    }
  }

  @PostMapping("update")
  public String update(
      Project project,
      int[] memberNo) throws Exception {

    List<Member> memberList = new ArrayList<>();
    if (memberNo != null) {
      for (int no : memberNo) {
        memberList.add(new Member().setNo(no));
      }
    }
    project.setMembers(memberList);

    if (projectService.update(project) == 0) {
      throw new Exception("해당 프로젝트가 존재하지 않습니다.");
    }
    return "redirect:list";
  }
}
