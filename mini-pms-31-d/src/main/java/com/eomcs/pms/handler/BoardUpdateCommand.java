package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardUpdateCommand implements Command {

  List<Board> boardList;

  public BoardUpdateCommand(List<Board> list) {
    this.boardList = list;
  }

  @Override
  public void execute() {
    System.out.println("[게시물 변경]");
    int no = Prompt.inputInt("번호? ");
    Board board = findByNo(no);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    String title = Prompt.inputString(
        String.format("제목(%s)? ", board.getTitle()));
    String content = Prompt.inputString(
        String.format("내용(%s)? ", board.getContent()));
    String writer = Prompt.inputString(
        String.format("작성자(%s)? ", board.getWriter()));

    String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (!response.equalsIgnoreCase("y")) {
      System.out.println("게시글 변경을 취소하였습니다.");
      return;
    }

    board.setTitle(title);
    board.setContent(content);
    board.setWriter(writer);
    System.out.println("게시글을 변경하였습니다.");
  }

  private Board findByNo(int no) {
    for (int i = 0; i < boardList.size(); i++) {
      Board board = boardList.get(i);
      if (board.getNo() == no) {
        return board;
      }
    }
    return null;
  }
}
