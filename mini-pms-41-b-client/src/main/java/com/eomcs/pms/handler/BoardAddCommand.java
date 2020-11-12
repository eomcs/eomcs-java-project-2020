package com.eomcs.pms.handler;

import java.util.Map;
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
  public void execute(Map<String,Object> context) {
    System.out.println("[게시물 등록]");

    try {
      Board board = new Board();
      board.setTitle(Prompt.inputString("제목? "));
      board.setContent(Prompt.inputString("내용? "));

      // 로그인 사용자 정보 가져오기
      Member loginUser = (Member) context.get("loginUser");
      board.setWriter(loginUser);

      boardDao.insert(board);
      System.out.println("게시글을 등록하였습니다.");

    } catch (Exception e) {
      System.out.println("게시글 등록 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
