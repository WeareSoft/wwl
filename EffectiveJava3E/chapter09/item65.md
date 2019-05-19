# [item 65 리플렉션보다는 인터페이스를 사용하라]
### 사전 조사
- 자바 클래스 파일은 바이트 코드로 컴파일 되어 실행시간에 바이트 코드가 해석되어 실행된다는 것.
- 이 바이트 코드에는 클래스에 대한 모든 정보를 포함
#### [리플렉션이란?](https://en.wikipedia.org/wiki/Reflection_(computer_programming))
- 구체적인 클래스 타입을 알지 못해도, 컴파일된 바이트 코드를 통해 역으로 클래스 정보를 알아내어 클래스를 사용할 수 있는 기법.

#### 왜 리플렉션인가?
- 다형성을 구현하는 강력한 도구.
- 특정 클래스에 의존성을 가지지 않기 때문에 동적으로 확장하기 매우 좋은 장점.

#### 리플렉션의 단점은 없을까?
- 동적으로 결정되기 때문에 컴파일 타입 검사가 주는 이점을 누릴 수 없다.
- 성능이 떨어진다.. 동적으로 결정되기 때문에.
  - 최적화의 관점에서 성능적으로 '무조건' 떨어지는 것은 아니다.
- Reflection API를 사용하는 경우의 문제점
  - Reflection API가 파워풀한 특징을 가지고 있기는 하지만 꼭 필요한 곳에만 사용할 필요가 있다. 무분별한 사용은 성능이나 기능상 도움이 되지 않는다. 그러므로 Reflection API를 사용하지 않는 방법이 최선이며, 다른 방법이 존재함에도 불구하고 Reflection API를 사용한다면 개발자의 이해가 부족한 경우일 것이다.
  - **성능의 저하**
    - Java Reflection API는 컴파일 타임이 아닌 실행시에 클래스의 정보를 파악해야 하기 때문에 다른 코드보다 느려지는 단점을 가지고 있다. 그러므로 반복적으로 호출되는 로직 부분이나 루프를 구성하는 코드를 Reflection 으로 작성한다면 성능이 현저히 떨어지게 된다.
  - **보안상 문제**
    - Java Reflection API는 자바 애플릿과 같은 보안 모델 안에서 사용되는 경우도 있기 때문에 이러한 환경에서 실행되도록 작성된 프로그램은 Security Manager 가 실행을 허락하지 않는다면 문제가 있을 수 있다.
  - **클래스 내부의 노출**
    - 클래스의 내부에는 객체지향 개념에 따라 은닉성을 적용한 멤버 변수나 메소드가 있을 수 있는데, Reflection API를 사용한다면 객체지향 개념에 따라 은닉된 멤버에도 접근할 수 있게 되므로 예기치 못한 부작용이 있을 수 있다. 기능이 오작동하거나 이식성이 떨어지거나 플랫폼을 업그레이드할 때의 영향에 따라 프로그램의 기능이 달라지는 추상화 기능의 오작동이 있을 수 있다.



출처: https://rosalife.tistory.com/67 [Rosa Lee Life]

#### 클래스에 대한 정보는 어떤 것이 있을까?
- ClassName
- Class Modifiers (public, private, synchronized 등)
- Package Info
- Superclass
- Implemented Interfaces
- Constructors
- MethodsFields
- Annotations

#### 다른 언어는 비슷한 기능이 없을까?
- [C# - Reflection](https://docs.microsoft.com/ko-kr/dotnet/csharp/programming-guide/concepts/reflection)
- [Python - Reflection](https://docs.python.org/3.6/c-api/reflection.html)


> Java만이 제공하는 기능은 아님. Computer Language 관점으로 이해하는 것이 가장 BEST

## Reference
- <https://hiddenviewer.tistory.com/114>
- [Trail: The Reflection API - Java Doc](https://docs.oracle.com/javase/tutorial/reflect/)
## Reference2
- [[Java] 아주쉬운 리플렉션 예제](egloos.zum.com/lemonfish/v/4087285)
- [slipp - 자바의 리플렉션 기능은 뭘까요?](https://www.slipp.net/questions/275)
- [자바 리플렉션에 대한 오해와 진실](https://kmongcom.wordpress.com/2014/03/15/자바-리플렉션에-대한-오해와-진실/)
- [Reflection API(번역)](https://rosalife.tistory.com/67)
---

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

