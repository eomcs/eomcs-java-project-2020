package com.eomcs.util.concurrent;

import java.util.ArrayList;

public class ThreadPool {

  // 스레드풀의 종료 상태
  boolean stopping = false;

  // 스레드 목록을 담는 리스트
  ArrayList<Worker> workers = new ArrayList<>();

  // 작업을 수행하는 스레드
  private class Worker extends Thread {
    Runnable task;

    public void setTask(Runnable task) {
      this.task = task;
      synchronized (this) {
        this.notify();
      }
    }

    @Override
    public void run() {
      synchronized (this) {
        try {
          while (true) {
            this.wait();

            // 스레드풀의 상태가 종료하는 상태라면, 스레드 실행을 멈춘다.
            if (ThreadPool.this.stopping)
              break;

            task.run();
            workers.add(this);
            System.out.println("스레드풀로 되돌아 감!");
          }
        } catch (Exception e) {
          System.out.printf("%s 스레드에서 오류 발생! - 스레드풀에서 제거.\n", this.getName());
          workers.remove(this);
        }
      }
    }
  }

  public void execute(Runnable task) {
    Worker t = null;
    if (workers.isEmpty()) {
      t = new Worker();
      t.start();
      System.out.printf("새 스레드(%s) 생성 및 실행!\n", t.getName());
    } else {
      t = workers.remove(0);
      System.out.printf("기존 스레드(%s) 사용!\n", t.getName());
    }

    t.setTask(task);
  }

  public void shutdown() {
    try {
      this.stopping = true;

      // 일단 현재 목록에 대기하고 있는 스레드를 종료한다.
      while (!workers.isEmpty()) {
        Worker worker = workers.remove(0);
        synchronized (worker) {
          worker.notify();
        }
      }

      // 아직 클라이언트의 요청 처리를 완료하지 못한 스레드를 기다린다.
      Thread.sleep(2000);

      // 다시 현재 목록에 대기하고 있는 스레드를 종료한다.
      while (!workers.isEmpty()) {
        Worker worker = workers.remove(0);
        synchronized (worker) {
          worker.notify();
        }
      }
    } catch (Exception e) {
      System.out.println("스레드풀 종료 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
