package com.eomcs.util.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool01 {

  // 생성한 스레드를 보관할 컬렉션 준비
  List<Worker> workers = new ArrayList<>();

  // 스레드 + 작업을 맡기고 + 깨우는 기능 + 작업 완료 후 잠자는 기능
  class Worker extends Thread {

    Runnable task;

    public void setTask(Runnable task) {
      // 스레드가 할 작업을 배정한 후
      this.task = task;

      synchronized (this) {
        // 스레드를 깨운다.
        this.notify();
      }
    }

    @Override
    public void run() {
      synchronized (this) {
        while (true) {
          try {
            System.out.printf("[%s] - 스레드 대기 중...\n", this.getName());

            this.wait();

            System.out.printf("[%s] - 스레드 작업 시작!\n", this.getName());
            // 이 스레드 객체에 대해 대기 상태로 있다가
            // 이 스레드에게 깨어나라는 알림(notify()/nofifyAll())이 온다면
            // 즉시 running 상태로 되돌아 간다.

          } catch (Exception e) {
            System.out.printf("[%s] - 스레드 실행 중 오류 발생!\n", this.getName());
            break;
            // 기다리다가 인터럽트 예외가 발생하면
            // 스레드를 종료한다.
          }
          try {
            task.run();
            System.out.printf("[%s] - 스레드 작업 종료!\n", this.getName());

          } catch (Exception e) {
            System.out.printf("[%s] - %s\n",
                this.getName(), e.getMessage());
            // 작업 수행 중 오류가 발생하더라도
            // 스레드는 계속 유효하다.
          } finally {
            // 정상적인 종료든 작업 수행 중 예외가 발생했든 간에
            // 작업을 마친 스레드는 재사용할 수 있도록
            // 다시 목록에 보관되어야 한다.
            workers.add(this);
            System.out.printf("[%s] - 스레드풀로 되돌아 감!\n", this.getName());
          }
        }
      }
    }
  }

  public void execute(Runnable task) {
    Worker t;
    if (workers.size() == 0) {
      // 스레드가 없다면 새로 생성한다.
      t = new Worker();
      System.out.printf("[%s] - 스레드 생성!\n", t.getName());

      // 그리고 즉시 실행한다.
      // - 실행하더라도 스레드는 처음에 스스로 대기 상태로 갈 것이다.
      t.start();

      // 현재 main 스레드를 잠깐 멈추게 하여
      // 새로 만든 스레드가 실행할 틈을 주자!
      // 그래야만 새로 만든 스레드가 실행하자 마자 대기상태로 간다.
      try {
        Thread.sleep(20);
      } catch (Exception e) {
        // sleep() 중 발생한 예외는 무시한다.
      }
    } else {
      // 스레드가 있다면, 스레드풀에서 한 개 꺼낸다.
      // - 스레드풀에서 꺼낸 스레드는 현재 대기 상태이다.
      t = workers.remove(0);
      System.out.printf("[%s] - 스레드 꺼내서 재사용!\n", t.getName());
    }

    // 스레드를 깨워서 일을 시킨다.
    // - 스레드에게 해야 할 작업을 배정하면 된다.
    t.setTask(task);
  }
}
