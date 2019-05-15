# [item 65 리플렉션보다는 인터페이스를 사용하라]
### 사전 조사
- 자바 클래스 파일은 바이트 코드로 컴파일 되어 실행시간에 바이트 코드가 해석되어 실행된다는 것.
- 이 바이트 코드에는 클래스에 대한 모든 정보를 포함
#### 리플렉션이란?
- 구체적인 클래스 타입을 알지 못해도, 컴파일된 바이트 코드를 통해 역으로 클래스 정보를 알아내어 클래스를 사용할 수 있는 기법.

#### 왜 리플렉션인가?
- 다형성을 구현하는 강력한 도구.
- 특정 클래스에 의존성을 가지지 않기 때문에 동적으로 확장하기 매우 좋은 장점.

#### 리플렉션의 단점은 없을까?
- 동적으로 결정되기 때문에 컴파일 타입 검사가 주는 이점을 누릴 수 없다.
- 성능이 떨어진다.. 동적으로 결정되기 때문에.

#### 클래스에 대한 정보는 어떤 것이 있을까?
- ClassName
- Class Modifiers (public, private, synchronized 등)
- Package Info
- Superclass
- Implemented Interfaces
- Constructors
- MethodsFields
- Annotations

## Reference
- <https://hiddenviewer.tistory.com/114>
- [Trail: The Reflection API - Java Doc](https://docs.oracle.com/javase/tutorial/reflect/)
---

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

