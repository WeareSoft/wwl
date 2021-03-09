# 아이템5 - 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

클래스가 **내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면** 싱글톤과 유틸성 클래스는 사용하지 말 것

예를 들어, 사전에 의존하는 맞춤법 검사 프로그램을 만들 경우
```JAVA
// 싱글톤
public class SpellChecker {
  private final Lexicon dictionary = ...;

  private SpellChecker() { }
  public static SpellChecker INSTANCE = new SpellChecker();

  public boolean isValid(String word) { . . . }
  public List<String> suggestions(String typo) { . . . }
}
```
```JAVA
// 유틸성 클래스
public class SpellChecker {
  private static final Lexicon dictionary = ...;

  private SpellChecker() { }

  public static boolean isValid(String word) { . . . }
  public static List<String> suggestions(String typo) { . . . }
}
```
- 다른 언어의 사전이나 특수 사전 등 사전은 단 한 종류만 있는 것이 아니기 때문에 부적절한 방법
- 여러 사전을 사용하게 하기 위해 ```final``` 키워드를 제거하고 다른 사전으로 교체하는 메소드를 추가할 수는 있지만, 멀티스레드 환경에서 사용 불가능

## 의존 객체 주입 기법
클래스가 자원을 직접 만들게 하지 않고, 필요한 자원(또는 자원을 만들어주는 팩토리)을 생성자(또는 정적 팩토리나 빌더)에 넘기는 방식

맞춤법 검사기 클래스는 의존하는 자원(사전)의 여러 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원을 사용하기 위해 **맞춤법 검사기의 인스턴스 생성 시 생성자에 필요한 자원을 넘겨주는 방식**을 사용
```JAVA
public class SpellChecker {
  private final Lexicon dictionary;

  // 외부에서 의존성 주입
  public SpellChecker(Lexicon dictionary) {
    this.dictionary = Objects.requireNonNull(dictionary);
  }

  public boolean isValid(String word) { . . . }
  public List<String> suggestions(String typo) { . . . }
}
```

### 장점
- ```final``` 키워드로 인해 의존하는 객체의 불변을 보장(아이템 17)하여 같은 자원(사전)을 사용하려는 여러 클라이언트가 문제없이 공유 가능
- 클래스의 유연성, 재사용성, 테스트 용이성 개선에 도움

### 활용 방법
- 생성자, 정적 팩토리 또는 빌더에 사용할 자원을 인자로 전달하는 방식
- 생성자에 자원 **팩토리**를 넘겨주는 방식
  - 팩토리란, 호출 시마다 적절한 타입의 인스턴스를 만들어주는 객체
  - ```Supplier<T>``` 인터페이스는 팩토리를 잘 표현한 예
  - 바운디드 타입을 활용해 특정 타입의 하위 타입만 생성할 수 있는 팩토리 전달

대거(Dagger), 주스(Guice), 스프링(Spring) 같은 프레임워크는 의존성 주입 기법을 잘 활용한 예
