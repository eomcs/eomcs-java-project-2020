package com.eomcs.pms.web;

import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;
import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

@Controller
@RequestMapping("/member")
public class MemberController {

  @Autowired ServletContext servletContext; // 메서드의 파라미터로 못 받는다.
  @Autowired MemberService memberService;

  @RequestMapping("add")
  public String add(
      String name,
      String email,
      String password,
      String tel,
      Part photoFile) throws Exception {

    Member member = new Member();
    member.setName(name);
    member.setEmail(email);
    member.setPassword(password);
    member.setTel(tel);

    String filename = UUID.randomUUID().toString();
    String saveFilePath = servletContext.getRealPath("/upload/" + filename);

    photoFile.write(saveFilePath);
    member.setPhoto(filename);

    generatePhotoThumbnail(saveFilePath);

    memberService.add(member);
    return "redirect:list";
  }

  @RequestMapping("delete")
  public String delete(int no) throws Exception {
    if (memberService.delete(no) == 0) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    }
    return "redirect:list";
  }

  @RequestMapping("detail")
  public ModelAndView detail(int no) throws Exception {
    Member member = memberService.get(no);
    if (member == null) {
      throw new Exception("해당 회원이 없습니다!");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("member", member);
    mv.setViewName("/member/detail.jsp");
    return mv;
  }

  @RequestMapping("list")
  public ModelAndView list() throws Exception {
    ModelAndView mv = new ModelAndView();
    mv.addObject("list", memberService.list());
    mv.setViewName("/member/list.jsp");
    return mv;
  }

  @RequestMapping("update")
  public String update(Member member) throws Exception {
    memberService.update(member);
    return "redirect:list";
  }

  @RequestMapping("updatePhoto")
  public String updatePhoto(int no, Part photoFile) throws Exception {

    Member member = new Member();
    member.setNo(no);

    // 회원 사진 파일 저장
    if (photoFile.getSize() > 0) {
      String filename = UUID.randomUUID().toString();
      String saveFilePath = servletContext.getRealPath("/upload/" + filename);
      photoFile.write(saveFilePath);
      member.setPhoto(filename);

      // 회원 사진의 썸네일 이미지 파일 생성하기
      generatePhotoThumbnail(saveFilePath);
    }

    if (member.getPhoto() == null) {
      throw new Exception("사진을 선택하지 않았습니다.");
    }

    memberService.update(member);
    return "redirect:detail?no=" + member.getNo();
  }

  private void generatePhotoThumbnail(String saveFilePath) {
    try {
      Thumbnails.of(saveFilePath)
      .size(30, 30)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_30x30";
        }
      });

      Thumbnails.of(saveFilePath)
      .size(120, 120)
      .outputFormat("jpg")
      .crop(Positions.CENTER)
      .toFiles(new Rename() {
        @Override
        public String apply(String name, ThumbnailParameter param) {
          return name + "_120x120";
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
