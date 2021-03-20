# 아이템13 - clone 재정의는 주의해서 진행하라

## Cloneable
> 믹스인 인터페이스?

```JAVA
public interface Cloneable { }
```
복제해도 되는 클래스임을 명시하는 용도의 인터페이스

## clone 메소드
- ```clone``` 메소드는 접근제한자로 ```protected``` 사용
- **재정의하려면 ```Cloneable``` 인터페이스를 구현**해야 하는데, 이 메소드는 ```Object``` 클래스에 정의되어 있음
  - ```Cloneable``` 인터페이스에는 아무 것도 정의되어 있지 않음
  - ```Cloneable``` 인터페이스를 구현하여 ```clone```의 동작 방식이 결정됨
- ```Cloneable```을 구현하지 않고 ```clone``` 메소드 호출 시 ```CloneNotSupportedException``` 발생
  - ```CloneNotSupportedException```은 Checked Exception이기 때문에 반드시 예외처리 필요

다만, ```Cloneable```을 구현하여 ```clone```을 재정의할 경우 생성자를 호출하지 않고도 객체를 생성할 수 있게 되어 다소 위험하고 모순적인 매커니즘이 탄생


> p.78 마지막 문단 ~ p.79 첫번째 문단 이해 못함

### clone 예시 1. 클래스의 모든 필드가 기본 타입이거나 불변 타입인 경우(클래스가 가변 객체를 참조하지 않는 경우)
```JAVA
public final class PhoneNumber implements Cloneable {
  private final short areaCode, prefix, lineNum;  // 기본 타입

  // . . .

  @Override
  public PhoneNumber clone() {
    try {
      return (PhoneNumber) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(); // 절대 발생하지 않는 예외
    }
  }
}
```
- ```PhoneNumber```의 객체 복사 시 모든 필드가 새로 복사되기 때문에 문제 없음
- 반환 타입을 ```PhoneNumber```로 하여 클라이언트 쪽에서 형변환을 하지 않고 사용 가능
- 단, 쓸데없는 복사를 지양해야 한다는 관점에서 보면 불필요한 코드

### clone 예시 2. 가변 객체 복제 1 - 클래스에 가변 객체를 참조하는 필드가 있는 경우
```JAVA
public class Stack {
  private Object[] elements;  // 가변 객체
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  // . . .

  @Override
  public Stack clone() {
    try {
      Stack result = (Stack) super.clone();
      result.elements = elements.clone(); // 배열의 clone 메소드 사용
      return result;

    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
```
- 예시 1과 같이 단순히 ```Stack```의 복사본을 반환한다면, 원본의 ```elements```와 복사본의 ```elements```가 같은 배열을 참조하여 문제 발생
- 따라서, 위 코드처럼 **배열의 ```clone``` 메소드를 사용**하여 해결

### clone 예시 3. 가변 객체 복제 2 - 깊은 복사
깊은 복사(deep copy)가 필요한 경우는 대표적으로 다음과 같은 해시테이블
```JAVA
public class HashTable implements Cloneable {
  private Entry[] buckets = ...; // 각 버킷은 키-값 쌍을 담는 연결 리스트의 첫 번째 엔트리 참조

  private static class Entry {
    final Object key;
    Object value;
    Entry next;

    Entry(Object key, Object value, Entry next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }

    // 이 엔트리가 가리키는 연결 리스트를 재귀적으로 복사
    Entry deepCopy() {
      return new Entry(key, value, next == null ? null : next.deepCopy());
    }
  }

  @Override
  public HashTable clone() {
    try {
      HashTable result = (HashTable) super.clone();
      result.buckets = new Entry[buckets.length];

      for (int i = 0; i < buckets.length; i++) {
        if (buckets[i] != null) {
          result.buckets[i] = buckets[i].deepCopy();
        }
      }
      return result;

    } catch(CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
```
- 예시 2와 같이 ```buckets```를 단순히 배열의 ```clone```으로만 복제한다면 배열 자체는 자신만의 배열을 갖게 되지만, 배열 ```buckets```의 각 원소들은 원본과 같은 연결리스트를 참조하여 문제 발생
- 따라서 위와 같이 ```deepCopy``` 메소드를 재귀적으로 복사하도록 구현
- 단, 재귀 방식은 배열 크기가 커질수록 Stack Overflow 발생 가능성이 높아지기 때문에 대안으로 반복 방식 사용
  ```JAVA
  // 반복 방식으로 수정한 deepCopy
  Entry deepCopy() {
    Entry result = new Entry(key, value, next);

    for (Entry p = result; p.next != null; p = p.next) {
      p.next = new Entry(p.next.key, p.next.value, p.next.next);
    }

    return result;
  }
  ```

### clone 예시 4. 가변 객체 복제 3 - 고수준 API 활용
먼저 ```super.clone()```을 호출해 얻은 객체의 모든 필드를 초기 상태로 설정 후, 원본 객체의 상태를 다시 생성하는 고수준 메소드를 호출
- 고수준? 사람이 이해하기 쉬운 수준. 저수준을 기반으로 이해하기 쉽게 만들어진 것

예를 들어 위의 해시테이블 같은 경우, ```buckets``` 필드를 새로운 배열로 초기화한 후 원본의 모든 키-값 쌍 각각을 복제본의 ```put(key, value)``` 메소드(고수준 메소드)를 호출해서 데이터를 넣어줌으로써 원본과 복제본을 일치시키는 방식

단, 다른 저수준 방식들에 비해 다소 느린 편이고, ```Cloneable``` 아키텍처의 기초가 되는 **필드 단위 객체 복사를 우회**하는 방법이기 때문에 다소 적절하지 않은 느낌

## 주의할 점
- **clone 메소드는 사실상 생성자와 같은 효과. 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장하도록 구현**하는 것이 중요
- 생성자와 마찬가지로 **```clone``` 메소드 내부** 에서는 문제 발생 방지를 위해 하위 타입에서 재정의할 수 있는 메소드를 호출하지 않아야 하며 **```private```이나 ```final``` 메소드만 호출** 가능
- 재정의한 ```clone``` 메소드에는 ```throws``` 제거
- 상속용 클래스는 ```Cloneable``` 구현 금지
- Thread safe하려면 ```clone``` 메소드도 동기화 처리 필요

## clone의 대안
주의해서 사용해야 하며, 구현하기 다소 복잡하다는 단점도 있기 때문에 **복사 생성자와 복사 팩토리라는 더 나은 객체 복사 방식**으로 대체 가능

### 복사 생성자?
단순히 자신과 같은 클래스의 인스턴스를 인수로 받는 생성자이며, 복사 팩토리는 복사 생성자를 모방한 정적 팩토리
```JAVA
// 복사 생성자
public Yum(Yum yum) { . . . }

// 복사 팩토리
public static Yum newInstance(Yum yum) { . . . }
```

복사 생성자는 깊은 복사를 통해 새롭게 생성되는 객체가 원본 객체와 같으면서도 완전히 독립적

#### ```clone``` 재정의보다 더 나은 이유
- 생성자를 사용해서 객체 생성
- 형변환 불필요
- 불필요한 검사 예외 제거 가능
- 복사 생성자와 복사 팩토리 메소드의 인자로 인터페이스 타입을 지정할 수 있기 때문에 클라이언트는 원본의 구현 타입에 얽매이지 않고 복제본의 타입을 적절히 선택 가능
  - 인터페이스 기반 복사 생성자와 복사 팩토리의 정확한 명칭은 '변환 생성자', '변환 팩토리'
  - 예를 들어, ```HashSet``` 객체를 ```TreeSet``` 타입으로 복제가 가능
    ```JAVA
    Set set = new HashSet<>();
    Set treeSet = new TreeSet<>(set); // HashSet을 TreeSet으로 복제
    ```
