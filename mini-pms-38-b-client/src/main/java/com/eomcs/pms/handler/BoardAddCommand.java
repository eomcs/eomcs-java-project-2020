package com.eomcs.pms.handler;

import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class BoardAddCommand implements Command {

  BoardDao boardDao;
  MemberDao memberDao;

  public BoardAddCommand(BoardDao boardDao, MemberDao memberDao) {
    this.boardDao = boardDao;
    this.memberDao = memberDao;
  }

  @Override
  public void execute() {
    System.out.println("[게시물 등록]");

    try {
      Board board = new Board();
      board.setTitle(Prompt.inputString("제목? "));
      board.setContent(Prompt.inputString("내용? "));

      while (true) {
        String name = Prompt.inputString("작성자?(취소: 빈 문자열) ");

        if (name.length() == 0) {
          System.out.println("게시글 등록을 취소합니다.");
          return;
        } else {
          Member member = memberDao.findByName(name);

          if (member == null) {
            System.out.println("등록된 회원이 아닙니다.");
            continue;
          }
          board.setWriter(member);
          break;
        }
      }

      boardDao.insert(board);
      System.out.println("게시글을 등록하였습니다.");

    } catch (Exception e) {
      System.out.println("게시글 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
