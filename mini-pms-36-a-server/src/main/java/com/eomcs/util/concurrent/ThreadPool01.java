package com.eomcs.util.concurrent;

import java.util.ArrayList;

public class ThreadPool01 {

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
}
