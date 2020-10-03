# [item 55] 옵셔널 반환은 신중히 하라

### 사전 조사
-   예외를 던지거나?
    -   <https://nesoy.github.io/articles/2018-07/Java-Handling-Exception>
    -   예외는 스택 추적 전체를 캡처해야하므로 비용이 만만치 않다.
-   null를 반환하는 것.

### 다른 언어는 어떻게 할까?
-   하스켈 -> https://wiki.haskell.org/Partial_functions
-   스칼라 -> Option
    -   빈값을 의미하는 객체생성.
-   코틀린 -> https://thdev.tech/kotlin/2016/08/04/Kotlin-Null-Safety/
    -   컴파일레벨에서 체크.

### How to use Optional?
-   <http://www.daleseo.com/java8-optional-effective/>

### Optional 메서드
- `Optional.empty()`
  - 내부 값이 비어있는 Optional 객체 반환
- `Optional.of(T value)`
  - 내부 값이 `value`인 Optional 객체 반환
  - 만약 value가 `null`인 경우 `NullPointerException` 발생
- `Optional.ofNullable(T value)`
  - 가장 자주 쓰이는 Optional 생성 방법
  - value가 `null`이면 `empty Optional`을 반환하고, 값이 있으면 `Optional.of`로 생성
- `T get()`
  - Optional 내의 값을 반환
  - 만약 Optional 내부 값이 `null`인 경우 `NoSuchElementException` 발생
- `boolean isPresent()`
  - Optional 내부 값이 `null`이면 false, 있으면 true 반환
- `boolean isEmpty()`
  - Optional 내부의 값이 `null`이면 true, 있으면 false 반환
  - `isPresent()` 와 반대되는 메서드
- `void ifPresent(Consumer<? super T> consumer)`
  - Optional 내부의 값이 있는 경우 `consumer` 함수를 실행
- `Optional<T> filter(Predicate<T> predicate)`
  - Optional에 `filter` 조건을 걸어 조건에 맞을 때만 Optional 내부 값이 있음
  - 조건이 맞지 않으면 `Optional.empty`를 리턴
- `Optional<U> map(Funtion<? super T, ? extends U> f)`
  - Optional 내부의 값을 `Function`을 통해 가공
- `T orElse(T other)`
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 `null`인 경우 `other`을 반환
- `T orElseGet(Supplier<? extends T> supplier)`
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 `null`인 경우 `supplier`을 실행한 값을 반환
- `T orElseThrow()`
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 `null`인 경우 `NoSuchElementException` 발생
- `T orElseThrow(Supplier<? extends X> exceptionSupplier)`
  - Optional 내부의 값이 있는 경우 그 값을 반환
  - Optional 내부의 값이 `null`인 경우 `exceptionSupplier`을 실행하여 `Exception` 발생

### Optional 사용 예제
- 학교, 교실, 선생님, 과목이라는 클래스가 있을 때 이 학교의 교실의 선생님의 과목을 반환하는 코드를 작성
```java
public class School {
    private ClassRoom classRoom;
}

public class ClassRoom {
    private Teacher teacher;
    private List<Student> students;
}

public class Teacher {
    private Subject subject;
    private String name;
}

public class Subject {
    private String subjectName;
}
```
- null-safe 하지 않은 코드
  ```java
  school.getClassRoom().getTeacher().getSubject().getSubjectName();
  ```
- null-safe 한 코드
  ```java
  if(school == null) {
    return null;
  }

  ClassRoom classRoom = school.getClassRoom();
  if(classRoom == null) {
      return null;
  }

  Teacher teacher = classRoom.getTeacher();
  if(teacher == null) {
      return null;
  }

  Subject subject = teacher.getSubject();
  if(subject == null) {
      return null;
  }

  return subject.getSubjectName();
  ```
- Optional을 이용한 코드
  ```java
  Optional.ofNullable(school).map(School::getClassRoom)    //Optional<School>
                             .map(ClassRoom::getTeacher)   //Optional<ClassRoom>
                             .map(Teacher::getSubject)     //Optional<Teacher>
                             .map(Subject::getSubjectName) //Optional<Subject>
                             .orElse(null);
  ```
  - `map` 메서드에서 `null`인 경우 `empty Optional`을 반환하기 때문에 `NullPointException`이 발생하지 않음
  - 최후에 `orElse` 구문에서 `Optional` 내부의 값이 `null`인 경우 파라미터로 들어간 `null`을 반환하기 때문에 `NullPointException`에 안전

### Reference
- https://www.slideshare.net/gyumee/null-142590829
- https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html
- https://www.facebook.com/tobyilee/posts/10215671541676146
- https://jaehun2841.github.io/2019/02/24/effective-java-item55/

### 스터디 요약

-

—

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)
