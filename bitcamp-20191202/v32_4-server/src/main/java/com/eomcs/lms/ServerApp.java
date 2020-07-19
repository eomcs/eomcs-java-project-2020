// LMS 서버
package com.eomcs.lms;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.eomcs.lms.context.ApplicationContextListener;
import com.eomcs.lms.domain.Board;
import com.eomcs.lms.domain.Lesson;
import com.eomcs.lms.domain.Member;

public class ServerApp {

  // 옵저버 관련 코드
  Set<ApplicationContextListener> listeners = new HashSet<>();
  Map<String, Object> context = new HashMap<>();

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  private void notifyApplicationInitialized() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(context);
    }
  }

  private void notifyApplicationDestroyed() {
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(context);
    }
  }
  // 옵저버 관련코드 끝!

  public void service() {

    notifyApplicationInitialized();

    try (
        // 서버쪽 연결 준비
        // => 클라이언트의 연결을 9999번 포트에서 기다린다.
        ServerSocket serverSocket = new ServerSocket(9999)) {

      System.out.println("클라이언트 연결 대기중...");

      while (true) {
        Socket socket = serverSocket.accept();
        System.out.println("클라이언트와 연결되었음!");

        if (processRequest(socket) == 9) {
          break;
        }

        System.out.println("--------------------------------------");
      }

    } catch (Exception e) {
      System.out.println("서버 준비 중 오류 발생!");
    }

    notifyApplicationDestroyed();

  } // service()


  @SuppressWarnings("unchecked")
  int processRequest(Socket clientSocket) {
    try (Socket socket = clientSocket;
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

      System.out.println("통신을 위한 입출력 스트림을 준비하였음!");

      while (true) {
        String request = in.readUTF();
        System.out.println("클라이언트가 보낸 메시지를 수신하였음!");

        if (request.equals("quit")) {
          out.writeUTF("OK");
          out.flush();
          break;
        }

        if (request.equals("/server/stop")) {
          out.writeUTF("OK");
          out.flush();
          return 9;
        }

        List<Board> boards = (List<Board>) context.get("boardList");
        List<Member> members = (List<Member>) context.get("memberList");
        List<Lesson> lessons = (List<Lesson>) context.get("lessonList");

        if (request.equals("/board/list")) {
          out.writeUTF("OK");

          out.reset();
          // => 기존에 출력했던 List<Board> 객체의 직렬화 데이터를 무시하고
          // 새로 직렬화를 수행한다.

          out.writeObject(boards);

        } else if (request.equals("/board/add")) {
          try {
            Board board = (Board) in.readObject();

            int i = 0;
            for (; i < boards.size(); i++) {
              if (boards.get(i).getNo() == board.getNo()) {
                break;
              }
            }

            if (i == boards.size()) { // 같은 번호의 게시물이 없다면,
              boards.add(board); // 새 게시물을 등록한다.
              out.writeUTF("OK");

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("같은 번호의 게시물이 있습니다.");
            }


          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/board/detail")) {
          try {
            int no = in.readInt();

            Board board = null;
            for (Board b : boards) {
              if (b.getNo() == no) {
                board = b;
                break;
              }
            }

            if (board != null) {
              out.writeUTF("OK");
              out.writeObject(board);

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 게시물이 없습니다.");
            }

          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/board/update")) {
          try {
            Board board = (Board) in.readObject();

            int index = -1;
            for (int i = 0; i < boards.size(); i++) {
              if (boards.get(i).getNo() == board.getNo()) {
                index = i;
                break;
              }
            }

            if (index != -1) {
              boards.set(index, board);
              out.writeUTF("OK");
            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 게시물이 없습니다.");
            }

          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/board/delete")) {
          try {
            int no = in.readInt();

            int index = -1;
            for (int i = 0; i < boards.size(); i++) {
              if (boards.get(i).getNo() == no) {
                index = i;
                break;
              }
            }

            if (index != -1) { // 삭제하려는 번호의 게시물을 찾았다면
              boards.remove(index);
              out.writeUTF("OK");

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 게시물이 없습니다.");
            }
          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }

        } else if (request.equals("/member/list")) {
          out.writeUTF("OK");
          out.reset();
          out.writeObject(members);

        } else if (request.equals("/member/add")) {
          try {
            Member member = (Member) in.readObject();

            int i = 0;
            for (; i < members.size(); i++) {
              if (members.get(i).getNo() == member.getNo()) {
                break;
              }
            }

            if (i == members.size()) {
              members.add(member);
              out.writeUTF("OK");

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("같은 번호의 회원이 있습니다.");
            }


          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/member/detail")) {
          try {
            int no = in.readInt();

            Member member = null;
            for (Member m : members) {
              if (m.getNo() == no) {
                member = m;
                break;
              }
            }

            if (member != null) {
              out.writeUTF("OK");
              out.writeObject(member);

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 회원이 없습니다.");
            }

          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/member/update")) {
          try {
            Member member = (Member) in.readObject();

            int index = -1;
            for (int i = 0; i < members.size(); i++) {
              if (members.get(i).getNo() == member.getNo()) {
                index = i;
                break;
              }
            }

            if (index != -1) {
              members.set(index, member);
              out.writeUTF("OK");
            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 회원이 없습니다.");
            }

          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/member/delete")) {
          try {
            int no = in.readInt();

            int index = -1;
            for (int i = 0; i < members.size(); i++) {
              if (members.get(i).getNo() == no) {
                index = i;
                break;
              }
            }

            if (index != -1) {
              members.remove(index);
              out.writeUTF("OK");

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 회원이 없습니다.");
            }
          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }

        } else if (request.equals("/lesson/list")) {
          out.writeUTF("OK");
          out.reset();
          out.writeObject(lessons);

        } else if (request.equals("/lesson/add")) {
          try {
            Lesson lesson = (Lesson) in.readObject();

            int i = 0;
            for (; i < lessons.size(); i++) {
              if (lessons.get(i).getNo() == lesson.getNo()) {
                break;
              }
            }

            if (i == lessons.size()) {
              lessons.add(lesson);
              out.writeUTF("OK");

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("같은 번호의 수업이 있습니다.");
            }


          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/lesson/detail")) {
          try {
            int no = in.readInt();

            Lesson lesson = null;
            for (Lesson l : lessons) {
              if (l.getNo() == no) {
                lesson = l;
                break;
              }
            }

            if (lesson != null) {
              out.writeUTF("OK");
              out.writeObject(lesson);

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 수업이 없습니다.");
            }

          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/lesson/update")) {
          try {
            Lesson lesson = (Lesson) in.readObject();

            int index = -1;
            for (int i = 0; i < lessons.size(); i++) {
              if (lessons.get(i).getNo() == lesson.getNo()) {
                index = i;
                break;
              }
            }

            if (index != -1) {
              lessons.set(index, lesson);
              out.writeUTF("OK");
            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 수업이 없습니다.");
            }

          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }
        } else if (request.equals("/lesson/delete")) {
          try {
            int no = in.readInt();

            int index = -1;
            for (int i = 0; i < lessons.size(); i++) {
              if (lessons.get(i).getNo() == no) {
                index = i;
                break;
              }
            }

            if (index != -1) {
              lessons.remove(index);
              out.writeUTF("OK");

            } else {
              out.writeUTF("FAIL");
              out.writeUTF("해당 번호의 수업이 없습니다.");
            }
          } catch (Exception e) {
            out.writeUTF("FAIL");
            out.writeUTF(e.getMessage());
          }

        } else {
          out.writeUTF("FAIL");
          out.writeUTF("요청한 명령을 처리할 수 없습니다.");
        }
        out.flush();
      }

      System.out.println("클라이언트로 메시지를 전송하였음!");

      return 0;

    } catch (Exception e) {
      System.out.println("예외 발생:");
      e.printStackTrace();
      return -1;
    }
  }

  public static void main(String[] args) {
    System.out.println("서버 수업 관리 시스템입니다.");

    ServerApp app = new ServerApp();
    app.addApplicationContextListener(new DataLoaderListener());
    app.service();
  }
}
