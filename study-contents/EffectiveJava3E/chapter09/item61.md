# [item 61] 박싱된 기본 타입보다는 기본 타입을 사용하라
### 사전 조사
- 박싱된 기본 타입은 언제 써야 하는가?
    - 컬렉션의 원소, 키, 값으로 쓰는 경우
    - 매개변수화 타입이나, 매개변수화 메서드의 타입 매개변수로 사용하는 경우

- 그럼 애초에 Wrapper 클래스를 만들지.. Primitive Type과 Wrapper 클래스를 따로 만들었을까?
    - Nesoy 의견.
    - 기존은 Primitive Type으로 메모리 절약 방식으로 개발했으나?
    - 객체 지향언어로 개발하다 보니.. 여러 군데에서 Primitive Type을 사용하지 못하는 이슈 발생
    - 이를 해결하기 위해 Wrapper로 객체로 감싸는 방식으로 진화

#### 다른 언어는 어떻게 구현했을까?
- [Kotlin](https://namu.wiki/w/Kotlin)
    - Java의 'Integer'나 'Double'처럼 primitive type을 위한 별도의 wrapper class가 존재하지 않는다.
    - 모든 primitive type은 객체 취급을 받는다. 따라서 Int 따위의 변수는 객체에 할당된 toString 함수 등을 바로바로 이용할 수 있다.

## Reference
- <https://www.quora.com/What-are-primitive-type-wrapper-classes>

---

### 스터디 요약
-

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

