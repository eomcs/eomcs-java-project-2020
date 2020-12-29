package com.eomcs.pms.web;

import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired MemberService memberService;

  @GetMapping("login")
  public void loginForm() throws Exception {
  }

  @PostMapping("login")
  public String login(
      String email,
      String password,
      String saveEmail,
      HttpServletResponse response,
      HttpSession session) throws Exception {

    Cookie emailCookie = new Cookie("email", email);

    if (saveEmail != null) {
      emailCookie.setMaxAge(60 * 60 * 24 * 7);
    } else {
      emailCookie.setMaxAge(0); // 유효기간이 0이면 삭제하라는 의미다.
    }
    response.addCookie(emailCookie);

    Member member = memberService.get(email, password);
    if (member == null) {
      return "/auth/loginError.jsp";
    }
    session.setAttribute("loginUser", member);
    return "redirect:../../index.html";
  }

  @SuppressWarnings("unchecked")
  @GetMapping("facebookLogin")
  public String facebookLogin(String accessToken, HttpSession session) throws Exception {

    // 페이스북 서버에 사용자 정보 요청
    // => 액세스토큰을 가지고 페이스북 서버에 사용자 정보를 요청한다.
    //
    // 1) Facebook Graph API 실행하기
    // => JSON 또는 XML을 리턴하는 HTTP 요청을 코딩하는 방법?
    //    - 스프링에서 제공하는 RestTemplate 클래스를 사용하라.
    //    - JSON 또는 XML을 자바 객체로 자동 변환해주기 때문에 편리하다.
    RestTemplate restTemplate = new RestTemplate();

    // 2) 서버에 요청하기
    // => 첫 번째 파라미터 : 요청 URL
    // => 두 번째 파라미터 : 서버가 응답한 JSON을 어떤 타입의 객체로 만들지 지정.
    Map<String,Object> facebookUserInfo = restTemplate.getForObject(
        "https://graph.facebook.com/v9.0/me?access_token={1}&fields={2}",
        Map.class,
        accessToken,
        "id,name,email,gender");

    // 사용자 정보 확인
    String email = (String) facebookUserInfo.get("email");
    if (email == null) {
      session.invalidate();
      return "/auth/loginError.jsp";
    }

    Member member = memberService.get(email);
    if (member == null) {
      // 자동 회원 가입
      member = new Member();
      member.setEmail(email);
      member.setPassword("1111");
      member.setName((String) facebookUserInfo.get("name"));
      memberService.add(member);

      // 회원 등록 후 DB의 등록된 회원 정보를 가져온다.
      session.setAttribute("loginUser", memberService.get(email));

    } else {
      session.setAttribute("loginUser", member);
    }

    return "redirect:../../index.html";
  }

  @GetMapping("loginUser")
  public void loginUser() {
  }

  @GetMapping("logout")
  public void logout(HttpSession session, Model model) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      session.invalidate();
    }
    model.addAttribute("loginUser", loginUser);
  }
}
