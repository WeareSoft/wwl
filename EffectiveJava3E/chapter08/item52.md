# [item 52] 다중정의는 신중히 사용하라
### 사전 조사 
#### 다중정의시 주의할 점
- **매개변수 수가 같은 다중정의는 만들지 말자**
  - 대신 메서드 이름을 다르게 할 수 있다
  - 매개변수 수가 같더라도, 그 중 하나 이상이 "근본적으로 다르다(readically defferenct)"면 괜찮다
    - ex) `ArrayList`의 생성자는 `int`를 받는 것과 `Collection`을 받는 것이 둘 있는데, 줄은 어느 쪽으로든 형변환이 불가능하다
  - 단, 박싱된 기본타입 사용에 주의하라
    - 기본 타입이 참조타입으로 형변환되는 경우가 생길 수 있으므로 예상치 못하게 동작할 수 있다.
    - ex) `List<E>`의 `remove(Object)`와 `remove(int)`
- **람다와 메서드 참조 사용시 주의하라**
  - 다중 정의된 메서드(혹은 생성자)들이 함수형 인터페이스를 인수로 받을 때, 비록 서로 다른 함수형 인터페이스라도 인수 위치가 같으면 혼란이 생긴다
  - 적용성 테스트?
    - https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.12.2.2
  - [ExecutorService](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
      ``` java
      public interface ExecutorService extends Executor {
          ...
          <T> Future<T> submit(Callable<T> task);
          ...
          Future<?> submit(Runnable task);
          ...
      }
      ```
  - [Callable<V>](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Callable.html)
      ``` java
      @FunctionalInterface
      public interface Callable<V> {
          V call() throws Exception;
      }
      ```

  - [Runnable](https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
      ``` java
      @FunctionalInterface
      public interface Runnable {
          public abstract void run();
      }
      ```

#### 예외
지침(매개변수 수가 같은 다중정의)을 어겨야할 상황이 있다면, 인수를 포워드 하여 같은 작업을 하도록 한다면 상관 없다.

- `String.valueOf`
    ``` java
    char[] str = new char[]{'s', 't', 'r'};
    String s1 = String.valueOf(str);
    String s2 = String.valueOf((Object) str);
    System.out.println(s1); // str
    System.out.println(s2); // [C@75412c2f
    ```
---

#### Callable
- 모든 println은 void를 반환
- Callable은 반환값이 존재

```java
@FunctionalInterface
public interface Callable<V> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    V call() throws Exception;
}
```


#### 다중정의 해소(Resolution)
- 적절한 다중정의 메서드를 찾는 알고리즘
- // 이미지 추가 
- 참조된 메서드(println)와 호출한 메서드(submit) 양쪽 다 다중정의되어, 다중정의 해소 알고리즘이 우리의 기대처럼 동작하지 않는다.
- 다중정의된 메서드(혹은 생성자)들이 함수형 인터페이스를 인수로 받을 때, 비록 서로 다른 함수형 인터페이스라도 인수 위치가 같으면 혼란이 생긴다.
    - 서로 다른 함수형 인터페이스라도 서로 근본적으로 다르지 않다.
    - `Runnable`과 `Callable`

```java
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```
```java
// Callable 의 설명 참고 
 * <p>The {@code Callable} interface is similar to {@link
 * java.lang.Runnable}, in that both are designed for classes whose
 * instances are potentially executed by another thread.  A
 * {@code Runnable}, however, does not return a result and cannot
 * throw a checked exception.
```

#### 어떤 다중정의 메서드가 불리는지 몰라도 기능이 같다면 신경쓰지 않아도 된다.
- 더 특수한 다중 정의 메서드에서 더 일반적인 다중정의 메서드로 일을 넘긴다. (forward)
- forward 예시

```java
// StringBuffer, StringBuilder, String, CharBuffer 등 
public boolean contentEquals(StringBuffer sb) {
    return contentEquals((CharSequence) sb); // 공통 인터페이스로 인수를 forward 
}
```

#### 매개변수 수가 같을 때는 다중정의를 피한다.
- 생성자의 경우, 헷갈릴 만한 매개변수는 형변환하여 정확한 다중정의 메서드가 선택되도록 한다.

---

### 스터디 요약 
- 

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

