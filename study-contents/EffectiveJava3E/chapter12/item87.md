# [item 87] 커스텀 직렬화 형태를 고려해보라

### 사전 조사
#### 기본 직렬화 형태
- 객체가 포함한 데이터들과 그 객체에서부터 시작해 접근할 수 있는 모든 객체를 답아내며, 심지어 이 객체들이 연결된 위상까지 기술한다.
- 직렬화하는 시점의 특정 객체에 관련된 모든 데이터를 직렬화 하는 것이다.
    - 객체의 필드 값
    - 객체가 필드의 객체 참조로 포함하고 있는 모든 객체들의 필드 값
    - 객체의 클래스의 모든 슈퍼클래스 정보 등

#### trasient
- transient 키워드를 붙인 변수는 직렬화 대상에서 제외된다.
- 기본 직렬화를 사용한다면 transient 필드들은 역직렬화될 때 기본값으로 초기화된다.
    - 객체 참조 필드: null
    - 숫자 기본 타입 필드: 0
    - boolean 필드: false
- JPA (or hibernate)의 도메인에서는 @transient 어노테이션을 붙이게 되면 DB의 값과 매핑시키지 않는다.

#### 용량
- 자바 직렬화시에 기본적으로 타입에 대한 정보 등 클래스의 메타 정보도 가지고 있기 때문에 상대적으로 다른 포맷에 비해서 용량이 큰 문제가 있다.
- 직렬화 형태에 포함할 가치가 없는 내부구현에 해당하는 부분은 transient 한정자를 사용해 용량을 줄이는 것이 좋다.
- JSON 같은 최소의 메타정보만 가지고 있으면 텍스트로 된 포맷보다 같은 데이터에서 최소 2배 최대 10배 이상의 크기를 가질 수 있다.

#### serialVersionUID
- 직렬 버전 UID를 명시하지 않으면 런타임에 이 값을 생성하느라 복잡한 연산을 수행한다.
    - 클래스 구조 정보를 이용해 생성한 해쉬값을 사용한다.
- 직렬 버전 UID를 명시하지 않고 객체를 직렬화 한 후, 새로운 멤버 변수를 추가하고 다시 역직렬화하면 `java.io.InvalidClassException` 예외가 발생한다.
- serialVersionUID 값이 동일할 때에도 문제가 생길 수 있다.
    - 멤버 변수명은 같은데 멤버 변수 타입이 바뀔 때
    - 직렬화 자바 데이터에 존재하는 멤버 변수가 없애거나 추가했을 때
- 특별한 문제없으면 자바 직렬화 버전 serialVersionUID의 값은 개발 시 직접 관리해야 한다.
- 자바 직렬화를 사용할 때에는 될 수 있으면 자주 변경되는 클래스의 객체는 사용 안 하는 것이 좋다.
    - 변경에 취약하기 때문에 생각지도 못한 예외사항들이 발생할 가능성이 높다.
    - 특히 역직렬 화가 되지 않을 때와 같은 예외처리는 기본적으로 해두는 것을 추천한다.

#### objectStream
- `void defaultReadObject()`
    - 현재 Stream에서 static,transient가 아닌 객체를 읽는다. 
- `void defaultWriteObject()`
    - 현재 Stream에 static,transient가 아닌 객체를 쓴다.

---

#### 기본 직렬화를 사용해도 좋은 경우
1. 직접 설계한 직렬화가 기본 직렬화 형태와 거의 같은 결과가 나오는 경우
2. 객체의 물리적 표현과 논리적 내용이 같은 경우
    - 기본 직렬화 형태에 적합한 경우에도 불변식 보장과 보안을 위해 `readObject` 메서드를 제공해야할 때가 있다.
    - Object를 반환하므로 형변환 필요 
        ```java
        // read and print an object and cast it as string
        System.out.println("" + (String) ois.readObject());
        // read and print an object and cast it as string
        byte[] read = (byte[]) ois.readObject();
        ```

#### transient 한정자
- 의미
    - 해당 인스턴스 필드가 기본 직렬화 형태에 포함되지 않는다는 표시
- 주의
    - 클래스의 인스턴스 필드 모두가 transient라도 `defaultWriteObject` 메서드와 `defaultReadObject` 메서드를 호출해서 다음 릴리스에 필드 추가 시 호환될 수 있도록 한다. 
    - defaultWriteObject 메서드 호출: 기본 직렬화를 수용하든 하지 않든 transient로 선언하지 않은 모든 인스턴스 필드가 직렬화된다.
    - 따라서, transient 한정자를 선언해도 되는 필드에는 모두 붙여야 한다.
    - Ex. 캐시된 해시 값처럼 다른 필드에서 유도되는 필드
    - Ex. JVM을 실행할 때마다 값이 달라지는 필드 (네이티브 자료구조를 가리키는 long 필드) - UID?
- 객체의 논리적 상태와 무관한 필드라고 확신할 때만 transient 한정자를 생략한다.
- 기본 직렬화 사용 시 transient 필드들은 역직렬화할 때 기본값으로 초기화된다.

#### UID
- SUID(serialVersionUID) 
    - SUID(serialVersionUID) 필수 값은 아니다.
    - 호환 가능한 클래스는 SUID값이 고정되어 있다.
    - SUID가 선언되어 있지 않으면 클래스의 기본 해쉬값을 사용한다. 
    - 자바 직렬화 버전 serialVersionUID의 값은 개발 시 직접 관리해야 한다.


## Reference
- [자바 직렬화, 그것이 알고싶다. 실무편](http://woowabros.github.io/experience/2017/10/17/java-serialize2.html)
- [직렬화(Serialization)](https://brunch.co.kr/@oemilk/181)
- [[JAVA] 객체 직렬화 ObjectInputStream / ObjectOutputStream](https://hyeonstorage.tistory.com/252)
- [readObject 메서드 사용법](https://www.tutorialspoint.com/java/io/objectinputstream_readobject.htm)
- [자바 직렬화 serialVersionUID 참고](http://woowabros.github.io/experience/2017/10/17/java-serialize2.html)


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

