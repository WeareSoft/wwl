# 아이템1 - 생성자 대신 정적 팩토리 메소드를 고려하라
클래스는 클라이언트에 ```public``` 생성자 대신 **정적 팩토리 메소드**를 제공할 수 있다.

## 정적 팩토리 메소드의 장점
### 1. 이름을 가질 수 있다.
- 생성자만으로는 생성자가 어떤 역할을 하는지 문서를 찾아보지 않고는 파악하기 어려운 문제 발생
- 정적 팩토리 메소드를 통해 이름을 부여하여 해결 가능

### 2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
- 덕분에 불변 클래스는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용
- 플라이웨이트 패턴(Flyweight pattern)과 비슷한 기법
- 반복되는 요청에 같은 객체를 반환함으로써 언제, 어느 인스턴스를 살아있게 할지 통제하는 것이 가능 <br /> => **인스턴스 통제**
  - 인스턴스를 통제하는 이유는 클래스를 싱글톤으로 만들거나 인스턴스화가 불가능하게 하거나 불변 클래스와 동치(a == b일 때 a.equals(b)도 성립)인 인스턴스가 단 하나뿐임을 보장 가능
  - 대표적으로 열거 타입
```JAVA
public static final BigInteger ZERO = new BigInteger(new int[0], 0);

private final static int MAX_CONSTANT = 16;
private static BigInteger posConst[] = new BigInteger[MAX_CONSTANT+1];
private static BigInteger negConst[] = new BigInteger[MAX_CONSTANT+1];

public static BigInteger valueOf(long val) {
  if (val == 0)                               // 미리 만들어둔 객체 반환
    return ZERO;
  if (val > 0 && val <= MAX_CONSTANT)
    return posConst[(int) val];
  else if (val < 0 && val >= -MAX_CONSTANT)
    return negConst[(int) -val];

  return new BigInteger(val);                 // 없으면 새로운 객체 반환
}
```

### 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
- 이 장점을 응용하여 구현 클래스를 공개하지 않고도 객체를 반환하여 API를 작게 유지 가능
- 예시로, 자바 컬렉션 프레임워크(List, Set, Map)는 핵심 인터페이스들에 수정 불가나 동기화 동의 기능을 덧붙인 유틸리티 구현체를 ```java.util.Collections```에서 정적 팩토리 메소드를 통해 구현
  - 클라이언트는 컬렉션 프레임워크의 모든 구현체를 알지 못해도 인터페이스만으로 구현 객체를 다루는 것이 가능
- 인터페이스를 정적 팩토리 메소드의 반환 타입으로 사용하는 인터페이스 기반 프레임워크를 만드는 핵심 기술
  - 자바 8부터 인터페이스에 정적 메소드 선언이 가능

### 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
- 반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관 없음
- 예시로 ```EnumSet``` 클래스는 정적 팩토리 메소드만 제공하는데 경우에 따라 다른 타입의 인스턴스를 반환하지만 클라이언트는 어떤 타입의 인스턴스인지 알 수 없고 알 필요도 없음
- **객체 생성을 캡슐화**
```JAVA
public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
  Enum<?>[] universe = getUniverse(elementType);
  if (universe == null)
    throw new ClassCastException(elementType + " not an enum");

  if (universe.length <= 64)
    return new RegularEnumSet<>(elementType, universe);
  else
    return new JumboEnumSet<>(elementType, universe);
}
```
- 클라이언트는 ```RegularEnumSet```인지 ```JumboEnumSet```인지 알 필요가 없으며 ```EnumSet```의 하위 클래스이기만 하면 됨

### 5. 정적 팩토리 메소드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
- 서비스 제공자 프레임워크
- 리플렉션?
- ???

## 정적 팩토리 메소드의 단점
### 1. 정적 팩토리 메소드만 제공하면 상속이 불가능하여 하위 클래스를 만들 수 없다.
- 상속을 하려면 ```public```이나 ```protected``` 생성자가 필요
- 자바 컬렉션 프레임워크의 유틸리티 구현 클래스는 상속 불가능
- 다만 추후 [아이템 17](), [아이템 18]()에 따르기 위해 이 제약은 장점이 될 수도 있음

### 2. 정적 팩토리 메소드는 프로그래머가 찾기 어렵다.
- 생성자처럼 API 설명에 드러나지 않기 때문에 문서화를 잘 하고, 규약을 따라 네이밍 할 것

#### 주요 명명 방식
- ```from``` : 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메소드
  ```JAVA
  Date date = Date.from(instant);
  ```
- ```of``` : 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메소드
  ```JAVA
  Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
  ```
- ```valueOf``` : from과 of의 더 자세한 버전
  ```JAVA
  BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
  ```
- ```instance``` / ```getInstance``` : 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않음(새로운 인스턴스일수도)
  ```JAVA
  StackWalker luke = StackWalker.getInstance(options);
  ```
- ```create``` / ```newInstance``` : ```instance``` 또는 ```getInstance```와 같지만 매번 새로운 인스턴스를 생성해 반환
  ```JAVA
  Object newArray = Array.newInstance(classObject, arrayLen);
  ```
- ```getType``` : ```getInstance```와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 사용. ```Type```은 반환할 객체의 타입명
  ```JAVA
  FileStore fs = Files.getFileStore(path);
  ```
- ```newType``` : ```newInstance```와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 사용. ```Type```은 반환할 객체의 타입명
  ```JAVA
  BufferedReader br = Files.newBufferedReader(path);
  ```
- ```type``` : ```getType```과 ```newType```의 간결한 버전
  ```JAVA
  List<Complaint> litany = Collections.list(legacyLitany);
  ```

## 결론
정적 팩토리 메소드와 ```public``` 생성자는 각각의 장단점이 있으므로 적절히 사용. 다만, 정적 팩토리 메소드가 유리한 경우가 많음

### Reference
- 조슈아 블로크, 개앞맵시(이복연) 역, 『이펙티브 자바 3판』, 인사이트(2015)
