# 아이템18 - 상속보다는 컴포지션을 사용하라
상위 클래스와 하위 클래스가 모두 같은 개발자가 관리하는 한 패키지 내에 있고, 확장할 목적으로 만들어져 문서화가 잘 되어 있다면 안전하지만, 그렇지 않은 다른 패키지의 클래스를 상속하는 일은 높은 위험성

## 상속을 지양해야 하는 이유
### 메소드 호출과 달리 상속은 캡슐화를 깨뜨림
상위 클래스가 어떻게 구현되느냐에 따라 하위 클래스에서 오동작 발생 가능성

하위는 상위의 변화에 따라 같이 수정되어야 할 수도 있기 때문에 캡슐화를 깨뜨리는 것

#### 예시 - HashSet을 상속받아 재정의하는 클래스
- 처음 생성된 이후 추가된 원소의 개수를 알기 위한 클래스
- 필요한 변수와 접근자 메소드를 추가하고, `add()`, `addAll()` 재정의함
```JAVA
public class InstrumentedHashSet<E> extends HashSet<E> {
  // 추가된 원소 수
  private int addCount = 0;

  public InstrumentedHashSet() { }

  public InstrumentedHashSet(int initCap, float loadFactor) {
    super(initCap, loadFactor);
  }

  @Override
  public boolean add(E e) {
    addCount++;
    return super.add(e);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    addCount += c.size();
    return super.addAll(c);
  }

  public int getAddCount() {
    return addCount;
  }
}
```
```JAVA
InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
s.addAll(List.of("1", "2", "3"));
```
- 실행 결과를 예상했을 때는 3이 나와야 정상이지만 실제로는 6이 나옴
- 이유는 `HashSet`의 `addAll()` 메소드는 `add()` 메소드를 사용해 구현되어 있는데
- 재정의한 `addAll()`에서 `super.addAll()` 호출 시 내부에서 `add()`를 호출하면 **재정의한 `add()`가 호출**되기 때문에 값이 중복으로 더해지는 문제 발생

### 보안 관련 문제 발생 가능성
컬렉션에 추가할 모든 원소는 특정 조건을 만족해야 하기 때문에 원소를 추가하는 모든 종류의 메소드를 재정의해 필요한 조건을 검사하도록 구현한다면 일시적으로는 해결되겠지만, 이후 새로운 원소 추가 메소드가 정의된다면 하위 클래스에서 해당 메소드는 재정의하지 못해 보안 문제 발생 가능성
- 실제로 `HashTable`과 `Vector`를 컬렉션 프레임워크에 포함시켰을 때, 이러한 문제 발생했던 경험 있음

### 메소드 시그니처 중복
기존에 하위 클래스에 추가했던 메소드 시그니처와 다음 릴리즈에서 상위 클래스에 추가 정의된 메소드 시그니처가 우연히 중복된다면 문제 발생(컴파일 불가능)


## 문제 해결 방법
상속으로 인한 위와 같은 문제들을 해결하는 방법으로 **컴포지션** 사용

컴포지션이란, 기존 클래스를 확장하는 대신 새로운 클래스를 만들고 `private` 필드로 기존 클래스의 인스턴스를 참조하게 하는 것
- 기존 클래스가 새로운 클래스의 **구성요소**로 쓰인다는 의미에서 컴포지션이라 지칭
```JAVA
// 상속
public 새_클래스 extends 기존_클래스 {

}

public 기존_클래스 {
  private String name;

  public String getName() {
    return name;
  }
}
```
```JAVA
// 컴포지션
public 새_클래스 {
  private 기존_클래스 origin;

  // 새 클래스의 메소드와 대응하는 기존 클래스의 메소드를 호출해서 결과를 반환하는 것을 '전달'이라 하고, 이런 역할을 하는 메소드를 '전달 메소드'라 지칭
  public String getOriginName() {
    return origin.getName();
  }
}
```

#### 예시 - HashSet을 상속하는 대신 컴포지션 사용
```JAVA
public class InstrumentedSet<E> extends ForwardingSet<E> {
  private int addCount = 0;

  public InstrumentedSet(Set<E> s) {
    super(s);
  }

  @Override
  public boolean add(E e) {
    addCount++;
    return super.add(e);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    addCount += c.size();
    return super.addAll();
  }

  public int getAddCount() {
    return addCount;
  }
}
```
```JAVA
// 재사용할 수 있는 전달 클래스
public class ForwardingSet<E> implements Set<E> {
  private final Set<E> s; // 기존 클래스를 private 필드로 정의

  public ForwardingSet(Set<E> s) { this.s = s; }

  // 다음은 모두 전달 메소드
  public void clear()               { s.clear(); }
  public boolean contains(Object o) { return s.contains(o); }
  public boolean isEmpty()          { return s.isEmpty(); }
  public int size()                 { return s.size(); }
  public Iterator<E> iterator()     { return s.iterator(); }
  public boolean add(E e)           { return s.add(e); }
  public boolean remove(Object o)   { return s.remove(o); }
  public boolean containsAll(Collection<?> c) { return s.containsAll(c); }
  public boolean addAll(Collection<? extends E> c) { return s.addAll(c); }
  public boolean removeAll(Collection<?> c) { return s.removeAll(c); }
  public boolean retainAll(Collection<?> c) { return s.retainAll(c); }

  // . . .
}
```
- 임의의 `Set`에 계측 기능을 추가해 새로운 타입의 `Set`으로 만드는 것이 핵심
- 만약 상속 방식으로 한다면, `Set`의 구체 클래스인 `TreeSet`, `HashSet`, `LinkedHashSet` 등을 각각 따로 상속 받아 확장해줘야 하지만, 컴포지션을 사용함으로써 어떤 `Set` 구현체라도 계측 기능 추가 가능
```JAVA
Set<Instant> times = new InstrumentedSet<>(ne new TreeSet<>(cmp));
Set<E> s = new InstrumentedSet<>(new HashSet<>(INIT_CAPACITY));
```
- `InstrumentedSet`과 같은 클래스는 다른 `Set` 인스턴스를 감싸고 있다는 뜻에서 **래퍼 클래스**라 하며, 다른 `Set`에 추가 기능을 덧붙인다는 의미에서 **데코레이터 패턴**이라고 함

### 래퍼 클래스
단점이 거의 없지만 콜백 프레임워크와는 어울리지 않는 클래스
- 내부 객체는 자신을 감싸는 래퍼 클래스의 존재를 모르기 때문에 자기 자신의 참조를 콜백 때 사용하도록 넘기고, 콜백 때는 래퍼가 아닌 내부 객체를 호출하게 되어 **SELF 문제 발생**
- [참조](http://bit.ly/2LepViV)


### 주의할 점
상속은 반드시 A와 B가 **is-a 관계**일 때만 사용. 그렇지 않은 경우 B는 단순히 A의 구현체 중 하나인 것이므로 컴포지션 사용
- 자바 라이브러리에서 원칙 위반한 케이스
  - `Stack` - `Vector`
  - `Properties` - `HashTable`

is-a 관계라고 하더라도, 상위와 하위의 패키지가 서로 다르고 상위가 확장을 고려해 설계되지 않았다면 위험

컴포지션을 써야할 때 상속을 사용한다면 내부 구현을 불필요하게 노출하여 클라이언트가 노출된 내부에 직접 접근 가능

래퍼 클래스로 구현할 적당한 인터페이스가 있다면 웬만하면 상속대신 컴포지션
