# 아이템3 - private 생성자나 열거 타입으로 싱글턴임을 보증하라

## 싱글턴
인스턴스를 오직 하나만 생성할 수 있는 클래스

주로 유틸성 클래스나 설계상 유일해야 하는 시스템 컴포넌트에 사용

### 단점
싱글턴 클래스는 이를 사용하는 클라이언트를 테스트하기가 어려움. 이유는 싱글턴 인스턴스를 mock으로 대체할 수 없기 때문(타입을 인터페이스로 정의하고 인터페이스를 구현하면 가능)

## 만드는 방법
### 1. public static final 필드 방식
```JAVA
public class Elvis {
  public static final Elvis INSTANCE = new Elvis();

  private Elvis() { }
}
```
- ```private``` 생성자는 INSTANCE를 초기화할 때 딱 한 번만 호출되므로 인스턴스가 전체 시스템에서 하나 뿐임이 보장
- 다만 리플렉션 API를 사용해 ```private``` 생성자를 호출할 수 있으므로 생성자에서 두 번째 객체가 생성되려할 때 예외 처리 추가

#### 장점
- 해당 클래스가 싱글턴임이 API에 명확히 드러나는 점
- 간결함

<br />

### 2. 정적 팩토리 방식
```JAVA
public class Elvis {
  private static final Elvis INSTANCE = new Elvis();

  private Elvis() { }

  public static Elvis getInstance() {
    return INSTANCE;
  }
}
```

#### 장점
- 더이상 싱글턴으로 사용하지 않으려면 API를 바꾸지 않고도 싱글턴이 아니게 변경 가능
  - 호출하는 스레드별로 다른 인스턴스를 넘겨주게 할 수 있음
- 원한다면 정적 팩토리를 제네릭 싱글턴 팩토리로 만들 수 있는 점(아이템 30)
- 정적 팩토리의 메소드 참조를 공급자로 사용 가능 (아이템 43, 44)

<br />

### 3. 열거 타입 방식 - 바람직한 방법
```JAVA
public enum Elvis {
  INSTANCE;

  //
}
```

#### 장점
- 위 두 방법은 직렬화 처리가 필요하지만 열거 타입 방식은 없어도 무방
- 더 간결한 방법
- 제 2의 인스턴스가 생기는 일을 완벽히 방지

**대부분의 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법**
(단, Enum 외 다른 클래스 상속 필요 시 불가능)

## 직렬화
1, 2번 방법으로 만들어진 싱글톤 클래스를 직렬화하려면 단순히 ```Serializable``` 구현만으로는 부족하고, 모든 인스턴스 필드를 ```transient```로 선언하고 ```readResolve``` 메소드를 제공해야 함 (아이템 89)
- 이렇게 하지 않으면 역직렬화 할 때 마다 새로운 인스턴스 생성
```JAVA
private Object readResolve() {
  // 진짜 Elvis를 반환하고, 가짜 Elvis는 가비지 컬렉터에 맡김
  return INSTANCE;
}
```
