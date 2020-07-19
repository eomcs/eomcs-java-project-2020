package com.eomcs.lms.handler;

import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.List;
import com.eomcs.util.Prompt;

public class LessonHandler {
  
  // 목록을 다루는 객체를 지정할 때,
  // => 특정 클래스(예: AbstractList, LinkedList, ArrayList)를 지정하는 대신에,
  // => 사용 규칙(예: List)을 지정함으로써 
  // 더 다양한 타입의 객체로 교체할 수 있게 만든다.
  // => List 사용 규칙을 구현한 객체라면 어떤 클래스의 객체든지 사용할 수 있다.
  // 결국 유지보수를 더 유연하게 하기 위함이다.
  List<Lesson> lessonList;
  
  Prompt prompt;
  
  public LessonHandler(Prompt prompt, List<Lesson> list) {
    // list 파라미터는 List 인터페이스를 구현한 객체를 받는다.
    this.prompt = prompt;
    this.lessonList = list;
  }
  
  public void addLesson() {
    Lesson lesson = new Lesson();
    
    lesson.setNo(prompt.inputInt("번호? "));
    lesson.setTitle(prompt.inputString("수업명? "));
    lesson.setDescription(prompt.inputString("설명? "));
    lesson.setStartDate(prompt.inputDate("시작일? "));
    lesson.setEndDate(prompt.inputDate("종료일? "));
    lesson.setTotalHours(prompt.inputInt("총수업시간? "));
    lesson.setDayHours(prompt.inputInt("일수업시간? "));
    
    lessonList.add(lesson);
    
    System.out.println("저장하였습니다.");
  }
  
  public void listLesson() {
    // 수업 객체 목록을 복사 받을 배열을 준비하고, toArray()를 실행한다.
    // toArray()의 리턴 값은 파라미터로 넘겨준 배열의 주소이다.
    Lesson[] arr = this.lessonList.toArray(new Lesson[this.lessonList.size()]);
    for (Lesson l : arr) {
      System.out.printf("%d, %s, %s ~ %s, %d\n",
          l.getNo(), l.getTitle(), 
          l.getStartDate(), l.getEndDate(), l.getTotalHours());
    }
  }
  
  public void detailLesson() {
    int index = indexOfLesson(prompt.inputInt("번호? "));
    
    if (index == -1) {
      System.out.println("해당 번호의 수업이 없습니다.");
      return;
    }
    
    Lesson lesson = this.lessonList.get(index);
    
    System.out.printf("번호: %d\n", lesson.getNo());
    System.out.printf("수업명: %s\n", lesson.getTitle());
    System.out.printf("설명: %s\n", lesson.getDescription());
    System.out.printf("시작일: %s\n", lesson.getStartDate());
    System.out.printf("종료일: %s\n", lesson.getEndDate());
    System.out.printf("총수업시간: %d\n", lesson.getTotalHours());
    System.out.printf("일수업시간: %d\n", lesson.getDayHours());
  }
  
  public void updateLesson() {
    int index = indexOfLesson(prompt.inputInt("번호? "));
    
    if (index == -1) {
      System.out.println("해당 번호의 수업이 없습니다.");
      return;
    }
    
    Lesson oldLesson = this.lessonList.get(index);
    Lesson newLesson = new Lesson();
    
    newLesson.setNo(oldLesson.getNo());
    
    newLesson.setTitle(prompt.inputString(
        String.format("수업명(%s)? ", oldLesson.getTitle()), 
        oldLesson.getTitle()));

    newLesson.setDescription(prompt.inputString("설명? ", oldLesson.getTitle()));
    
    newLesson.setStartDate(prompt.inputDate(
        String.format("시작일(%s)? ", oldLesson.getStartDate()), 
        oldLesson.getStartDate()));
    
    newLesson.setEndDate(prompt.inputDate(
        String.format("종료일(%s)? ", oldLesson.getEndDate()), 
        oldLesson.getEndDate()));
    
    newLesson.setTotalHours(prompt.inputInt(
        String.format("총수업시간(%d)? ", oldLesson.getTotalHours()), 
        oldLesson.getTotalHours()));
    
    newLesson.setDayHours(prompt.inputInt(
        String.format("일수업시간(%d)? ", oldLesson.getDayHours()), 
        oldLesson.getDayHours()));
    
    /*
    int oldValue = oldLesson.getDayHours();
    String label = "일수업시간(" + oldValue + ")? ";
    int newValue = inputInt(label, oldValue);
    newLesson.setDayHours(newValue);
    */
    
    if (oldLesson.equals(newLesson)) {
      System.out.println("수업 변경을 취소하였습니다.");
      return;
    }
    
    this.lessonList.set(index, newLesson);
    System.out.println("수업을 변경했습니다.");
  }
  
  public void deleteLesson() {
    int index = indexOfLesson(prompt.inputInt("번호? "));
    
    if (index == -1) {
      System.out.println("해당 번호의 수업이 없습니다.");
      return;
    }
    
    this.lessonList.remove(index);
    
    System.out.println("수업을 삭제했습니다.");
  }
  
  private int indexOfLesson(int no) {
    for (int i = 0; i < this.lessonList.size(); i++) {
      if (this.lessonList.get(i).getNo() == no) {
        return i;
      }
    }
    return -1;
  }
  

  
}




