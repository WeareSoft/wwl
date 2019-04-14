# [item 42] 익명 클래스보다는 람다를 사용하라 
### 사전 조사 
- 비교자 생성 메서드 사용 예시
  - `Collections.sort(words, comparingInt(String::length))`
    - 비교자 생성 메서드 정의
      ```java
        /**
        * Accepts a function that extracts an {@code int} sort key from a type
        * {@code T}, and returns a {@code Comparator<T>} that compares by that
        * sort key.
        *
        * <p>The returned comparator is serializable if the specified function
        * is also serializable.
        *
        * @param  <T> the type of element to be compared
        * @param  keyExtractor the function used to extract the integer sort key
        * @return a comparator that compares by an extracted key
        * @see #comparing(Function)
        * @throws NullPointerException if the argument is null
        * @since 1.8
        */
        public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor) {
            Objects.requireNonNull(keyExtractor);
            return (Comparator<T> & Serializable)
                (c1, c2) -> Integer.compare(keyExtractor.applyAsInt(c1), keyExtractor.applyAsInt(c2));
        }
      ```
        - & 연산 
          - [https://npcore.tistory.com/19](https://npcore.tistory.com/19)
        - String::length - Method Reference
          - [https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html)
  - `words.sort(comparingInt(String::length))`
    - 내부적으로는 Arrays.sort 사용

- DoubleBinaryOperator interface
  - @FunctionalInterface
    - Functional Interface는 일반적으로 '구현해야 할 추상 메소드가 하나만 정의된 인터페이스'를 가리킨다.
     ```java
      //Functional Interface인 경우: 메소드가 하나만 있음
      public interface Functional {
          public boolean test(Condition condition);
      }
      
      //java.lang.Runnable도 결과적으로 Functional Interface임
      public interface Runnable {
          public void run();
      }
      
      //구현해야 할 메소드가 하나 이상 있는 경우는 Functional Interface가 아님
      public interface NonFunctional {
          public void actionA();
          public void actionB();
      }
     ```
    - 어노테이션 타입 FunctionalInterface는 어떤 인터페이스가 Functional Interface라는 것을 나타내기 위해 사용된다. 
      - 이것을 이용하면 부적절한 메소드 선언이 포함되어 있거나 함수형이어야 하는 인터페이스가 다른 인터페이스를 상속한 경우 미리 확인할 수 있다.
      - @FunctionalInterface로 지정되어 있으면서 실제로는 Functional Interface가 아닌 인터페이스를 선언한 경우 컴파일 타임 에러가 발생한다.
      - 어떤 인터페이스들은 우연히 함수형으로 정의될 수도 있기 때문에, Functional Interface들이 모두 @FunctionalInterface 어노테이션으로 선언될 필요도 없고 그렇게 하는 것이 바람직하지도 않다.
    - [http://wiki.sys4u.co.kr/pages/viewpage.action?pageId=7766426#FunctionalInterface%EC%99%80LambdaExpression-1.FunctionalInterface](http://wiki.sys4u.co.kr/pages/viewpage.action?pageId=7766426#FunctionalInterface%EC%99%80LambdaExpression-1.FunctionalInterface)

  - 람다 대신 익명 클래스를 사용해야 하는 경우
  1. 람다는 함수형 인터페이스에서만 사용
      - Ex) 추상 클래스의 인스턴스를 만들 때 람다를 쓸 수 없으니 익명 클래스를 사용해야 한다.
      - 람다를 이용하여 작은 함수 객체를 아주 쉽게 표현 가능
  2. 추상 매서드가 여러 개인 인터페이스의 인스턴스를 만들 때도 익명 클래스 사용
      - 즉, 함수형 인터페이스가 아닌 타입의 인스턴스를 만들 때만 익명 클래스 사용 
  3. 람다의 this 키워드는 바깥 인스턴스를 가리킨다. 즉, 자신을 참소할 수 없다.
      - 함수 객체가 자신을 참조해야 한다면 반드시 익명 클래스를 써야 한다.
  

---

### 스터디 요약 
- 

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

