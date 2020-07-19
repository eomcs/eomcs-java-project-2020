package com.eomcs.lms.handler;

import com.eomcs.lms.domain.Lesson;
import com.eomcs.util.AbstractList;
import com.eomcs.util.Prompt;

public class LessonHandler {
  
  // ArrayList나 LinkedList를 마음대로 사용할 수 있도록 
  // 객체 목록을 관리하는 필드를 선언할 때  
  // 이들 클래스의 수퍼 클래스로 선언한다.
  // => 대신 이 필드에 들어갈 객체는 생성자에서 파라미터로 받는다.
  // => 이렇게 하면 ArrayList도 사용할 수 있고, LinkedList도 사용할 수 있어
  //    유지보수에 좋다. 즉 선택의 폭이 넓어진다.
  AbstractList<Lesson> lessonList;
  
  Prompt prompt;
  
  public LessonHandler(Prompt prompt, AbstractList<Lesson> list) {
    this.prompt = prompt;
    this.lessonList = list;
    // Handler가 사용할 List 객체(의존 객체; dependency)를 생성자에서 직접 만들지 않고
    // 이렇게 생성자가 호출될 때 파라미터로 받으면,
    // 필요에 따라 List 객체를 다른 객체로 대체하기가 쉽다.
    // 예를 들어 ArrayList를 사용하다가 LinkedList로 바꾸기 쉽다.
    // LinkeList를 사용하다가 다른 객체로 바꾸기가 쉽다.
    // 즉 다형적 변수에 법칙에 따라 List의 하위 객체라면 어떤 객체든지 가능하다.
    // 이런식으로 의존 객체를 외부에서 주입받는 것을 
    // "Dependency Injection(DI; 의존성주입)"이라 부른다.
    // => 즉 의존 객체를 부품화하여 교체하기 쉽도록 만드는 방식이다. 
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




