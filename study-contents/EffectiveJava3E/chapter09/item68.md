# [item 68] 일반적으로 통용되는 명명 규칙을 따르라
### 사전 조사
#### 철자 규칙
- 패키지
    - 패키지와 모듈 이름은 각 요소를 **점(.)** 으로 구분하여 계층적으로 짓는다.
    - 요소들은 모두 소문자 알파벳 혹은 숫자로 이뤄진다.
        - ex) com.google, kr.co.edu
    - 패키지를 설명하는 하나이상의 요소로 이루어져 있다.
    - 일반적으로 8자 이하의 짧은 단어로 한다.
    - utilities보다는 util처럼 의미가 통하는 약어가 좋다.
    - 요소의 이름은 보통 한 단어 혹은 약어로 이루어진다.
    - 많은 기능을 제공하는 애플리케이션의 경우에는 계층을 더 많은 요소로 나누는 것이 좋다.
- 클래스, 인터페이스, 열거타입
    - 클래스 명은 하나이상의 단어로 구성되며, 첫글자는 대문자로 작성한다.
    - 여러 단어의 첫글자만 딴 약자나 널리 통용되는 줄임말을 제외하고는 줄임말을 쓰지 않도록 한다.
    - 조합한 단어를 구분할 수 있게 camel case로 작성한다.
- 메서드, 필드명
    - 첫 글자를 소문자로 작성하고 클래스 명과 같게 단어 별로 camel case로 작성한다.
    - 첫 단어가 약자라면 단어 전체가 소문자여야 한다.
- 상수 필드
    - 상수 필드를 구성하는 단어는 모두 대문자로 쓰며 단어 사이는 언더바(_)로 구분한다.
        - ex) VALUES, NEGATIVE_INFINITY
    - 상수 필드는 `static final`인 타입을 의미한다.
    - 가르키는 객체가 불변이라면, 그 타입은 가변이어도 상수이다.
- 지역번수
    - 다른 멤버와 동일한 규칙이 적용된다.
    - 단, 문맥에서 의미를 쉽게 유추할 수 있는 경우에는 약어를 사용해도 좋다.
        - ex) i, denom, houseNum
- 타입 매개변수
    - 보통 한 글자로 표현한다.
        - T: 임의의 타입 (Type)
        - E: 컬렉션의 원소 (Element)
        - K: 맵의 키 (Key)
        - V: 맵의 값 (Value)
        - X: 예외 (eXception)
        - R: 메서드의 반환타입 (Return)
        - 그 이외의 타입에는 T, U, V 혹은 T1, T2, T3의 식으로 사용
#### 문법 규칙
- 객체를 생성할 수 있는 클래스나 열거타입 인터페이스
    - 단수 명사나 명사구를 사용한다.
    - ex) Thread, PriorityQueue, ChessPiece
- 객체를 생성할 수 없는 클래스 (Utils 클래스)
    - 보통 복수형 명사로 짓는다.
    - ex) Collectors, Collections
- 인터페이스
    - 클래스명과 동일하게 짓거나, ible, able로 끝나는 형용사로 짓는다.
    - ex) Runnable, Iterable, Accessible
- 애너테이션
    - 지배적인 규칙이 없이 명사, 형용사, 동사, 전치사가 두루 쓰인다.
    - ex) @Binding, @Inject, @ImplementsBy, @Singleton
- 메서드
    - 동사나 목적어를 포함한 동사구로 짓는다.
    - ex) append, drawImage
- boolean 값을 반환하는 메서드
    - is~, has~로 짓는다.
    - ex) isDigit, isEmpty, hasSiblings
- 반환타입이 boolean이 아닌경우
    - 명사, 명사구, get~로 짓는다.
    - ex) size, hashcode, getTime
- 반환타입을 또다른 타입을 반환하는 경우
    - toType 의 형태로 짓는다.
    - ex) toString, toArray
- 객체의 내용을 다른 뷰로 보여주는 메서드
    - asType 의 형태로 짓는다.
    - ex) asList, asMap
- 객체의 값을 기본 타입(primitive type)으로 반환하는 경우
    - typeValue 의 형태로 짓는다.
    - ex) intValue, longValue
- 정적 팩터리
    - ex) from, valueOf, getInstance, newInstance

## Reference
-

---

### 스터디 요약
-

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

