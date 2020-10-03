# [item 88] readObject 메서드는 방어적으로 작성하라 

### 사전 조사
#### [readObject](https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html)

```java
public final Object readObject() throws IOException, ClassNotFoundException

Read an object from the ObjectInputStream. The class of the object, the signature of the class, and the values of the non-transient and non-static fields of the class and all of its supertypes are read. Default deserializing for a class can be overridden using the writeObject and readObject methods. Objects referenced by this object are read transitively so that a complete equivalent graph of objects is reconstructed by readObject.
The root object is completely restored when all of its fields and the objects it references are completely restored. At this point the object validation callbacks are executed in order based on their registered priorities. The callbacks are registered by objects (in the readObject special methods) as they are individually restored.

Exceptions are thrown for problems with the InputStream and for classes that should not be deserialized. All exceptions are fatal to the InputStream and leave it in an indeterminate state; it is up to the caller to ignore or recover the stream state.
```

#### trasient
- transient 키워드를 붙인 변수는 직렬화 대상에서 제외된다.
- 기본 직렬화를 사용한다면 transient 필드들은 역직렬화될 때 기본값으로 초기화된다.
    - 객체 참조 필드: null
    - 숫자 기본 타입 필드: 0
    - boolean 필드: false
- JPA (or hibernate)의 도메인에서는 @transient 어노테이션을 붙이게 되면 DB의 값과 매핑시키지 않는다.

#### 기본 readObject 사용하는 경우
- transient 필드를 제외한 모든 필드의 값을 매개변수로 받아 유효성 검사 없이 필드에 대입하는 public 생성자를 추가해도 괜찮은가?
    - **NO**
    - => 1. 커스텀 readObject 메서드를 만들어 모든 유효성 건사와 방어적 복사를 수행해야 한다.
    - => 2. 직렬화 프록시 패턴 

### readObject 메서드 작성 시 주의 사항 
- final이 아닌 직렬화 가능 클래스
    - readObject 메서드는 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안 된다.
    - (private, final, static 메서드는 재정의가 불가능하니 호출 가능)
    - 이 규칙을 어기면, 하위 클래스의 상태가 완전히 역직렬화되기 전에 하위 클래스에서 재정의된 메서드가 실행된다.

### readObject 메서드 작성 지침
- private이어야 하는 객체 참조 필드는 각 필드가 가리키는 객체를 방어적으로 복사하라. 불변 클래스 내의 가변 요소가 여기 속한다.
- 모든 불변식을 검사하여 어긋나는 게 발견되면 InvalidObjecdtException을 던진다. 방어적 복사 다음에는 반드시 불변식 검사가 뒤따라야 한다.
- 역직렬화 후 객체 그래프 전체의 유효성을 검사해야 한다면 [ObjectInputValidation 인터페이스](https://docs.oracle.com/javase/8/docs/api/java/io/ObjectInputValidation.html)를 사용하라.
- 직접적이든 간접적이든, 재정의할 수 있는 메서드는 호출하지 말자.

## Reference


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

