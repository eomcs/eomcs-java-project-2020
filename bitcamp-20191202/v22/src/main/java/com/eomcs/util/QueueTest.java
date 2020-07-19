package com.eomcs.util;

public class QueueTest {

  public static void main(String[] args) {
    
    Queue q = new Queue();
    
    q.offer("aaa");
    q.offer("bbb");
    q.offer("ccc");
    
    while (q.size() > 0) {
      System.out.println(q.poll());
    }

  }

}
