# 아이템14 - Comparable을 구현할지 고려하라

3장에서 다룬 이전의 아이템들은 모두 ```Object``` 클래스의 메소드를 재정의한 것이지만 이번 아이템에서 다룰 ```compareTo``` 메소드는 ```Comparable``` 인터페이스에 정의되어 있는 메소드

## Comparable
```JAVA
public interface Comparable<T> {
  int compareTo(T t);
}
```
제네릭 타입 인터페이스이며, ```compareTo``` 메소드는 단순 동치성 비교에 더해 순서까지 비교 가능

.```Comparable```을 구현하면 그 클래스의 인스턴스들에는 natural order가 있음을 의미
```JAVA
public class WordList {
  public static void main(String[] args) {
    Arrays.sort(args);

    Set<String> s = new TreeSet<>();
    Collections.addAll(s, args);
    System.out.println(s);
  }
}
```
- ```String```은 ```Comparable```을 구현한 클래스
- 인스턴스간 순서가 있기 때문에 ```Arrays.sort```를 활용해 간단히 정렬 가능
- 또한 ```TreeSet``` 타입은 정렬되어 저장되므로, 추가적인 정렬 처리 없이도 알파벳 순으로 출력 가능

자바 플랫폼 라이브러리의 모든 값 클래스와 열거 타입은 ```Comparable```을 구현해두었으며, 따라서 알파벳/숫자/연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 ```Comparable``` 인터페이스를 구현하는 것이 좋은 습관

### compareTo 메소드의 일반 규약
- 이 객체와 주어진 객체의 순서를 비교
- 이 객체가 주어진 객체보다 작으면 음의 정수 반환
- 이 객체가 주어진 객체와 같으면 0 반환
- 이 객체가 주어진 객체보다 크면 양의 정수 반환
- 이 객체와 비교할 수 없는 타입의 객체가 주어지면 ```ClassCastException``` 발생

다음 설명의 **sgn(표현식)** 표기는 수학의 부호 함수를 뜻하며, 표현식 값이 음수, 0, 양수일 때 각각 -1, 0, 1을 반환하는 함수
- 모든 x, y에 대해 다음 식을 만족
  ```JAVA
  sgn(x.compareTo(y)) == -sgn(y.compareTo(x))
  ```
- 추이성을 보장하므로 다음 식 A를 만족한다면 B도 만족
  ```JAVA
  // A
  x.compareTo(y) > 0 && y.compareTo(z) > 0
  ```
  ```JAVA
  // B
  x.compareTo(z) > 0
  ```
- 모든 z에 대해 다음 식 A를 만족한다면 B도 만족
  ```JAVA
  // A
  x.compareTo(y) == 0
  ```
  ```JAVA
  // B
  sgn(x.compareTo(z)) == sgn(y.compareTo(z))
  ```
- 필수는 아니지만 권장되는 규약. 지켜지지 않는다면 별도 명시 필요
  ```JAVA
  (x.compareTo(y) == 0) == (x.equals(y))
  ```
  - 만약 이 규약을 어긴 클래스를 정렬된 컬렉션에 넣을 경우 해당 컬렉션의 인터페이스(Collection, Set, Map)에 정의된 방식과 다른 방식으로 동작할 수 있음
    - 정렬된 컬렉션 : 동치성 비교 시 ```equals``` 대신 ```compareTo```로 비교 (TreeSet, LinkedHashMap 등)
    - 그 외 컬렉션 : ```equals```로 비교
    ```JAVA
    // compareTo와 equals가 일관되지 않은 클래스 BigDecimal
    Set<BigDecimal> hashSet = new HashSet<>();  // 정렬되지 않는 컬렉션
    Set<BigDecimal> treeSet = new TreeSet<>();  // 정렬된 컬렉션

    hashSet.add(new BigDecimal("1.0"));
    hashSet.add(new BigDecimal("1.00"));

    treeSet.add(new BigDecimal("1.0"));
    treeSet.add(new BigDecimal("1.00"));

    hashSet.size(); // == 2
    treeSet.size(); // == 1
    ```
    - 정렬된 컬렉션 ```TreeSet```은 ```compareTo```로 비교하기 때문에 ```1.0```과 ```1.00```은 같은 값 취급

위 규약을 잘 지키면 비교를 활용하는 ```TreeSet```, ```TreeMap``` 클래스와 정렬 알고리즘을 활용하는 유틸리티 클래스인 ```Collections```, ```Arrays```와 함께 사용 가능

또한, ```equals```의 규약과 똑같이 반사성, 대칭성, 추이성을 충족해야 하기 때문에 주의사항도 동일
- 클래스 확장 시 ```compareTo``` 규약 위배가 불가피
- 우회 방법은 상속 대신 컴포지션 사용

### compareTo 작성 요령
- 대체로 ```equals```와 비슷하지만 ```Comparable```이 제네릭 인터페이스이므로 ```compareTo```의 인수 타입은 컴파일타임에 결정되어 타입 체크나 형변환 과정이 불필요
- 인수가 ```null```일 경우 ```NullPointerException``` 던지기
- 객체 참조 필드를 비교하려면 ```compareTo```를 재귀 호출??
- 정수 기본 타입 필드 비교 시 박싱된 기본 타입 클래스에 새로 추가된 정적 메소드 ```compare``` 사용
  ```JAVA
  // ex. Integer
  public static int compare(int x, int y) {
    return (x < y) ? -1 : ((x == y) ? 0 : 1);
  }
  ```

#### Comparator
.```Comparable```을 구현하지 않은 필드나 표준이 아닌 순서로 비교하려면 ```Comparator```를 대신 사용
```JAVA
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
  // . . .

  @Override
  public int compareTo(CaseInsensitiveString cis) {
    // 자바가 제공하는 Comparator
    return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
  }
}
```
- ```Comparator```는 직접 만들거나 자바가 제공하는 것 중 사용

#### 비교할 필드가 여러 개일 때
비교 순서도 중요하기 때문에 **가장 핵심적인 필드부터 비교**하고, 가장 핵심 필드가 똑같다면 똑같지 않은 필드를 찾을 때까지 그 다음으로 중요한 필드를 비교해나가는 방식
```JAVA
public int compareTo(PhoneNumber pn) {
  int result = Short.compare(areaCode, pn.areaCode); // 가장 핵심 필드
  if (result == 0) {
    result = Short.compare(prefix, pn.prefix);
    if (result == 0) {
      result = Short.compare(lineNum, pn.lineNum);
    }
  }
  return result;
}
```
```JAVA
// JAVA 8에서 가능한 방식 : 메소드 체이닝을 활용해 정적 비교자를 생성해 사용
// 코드는 간결해지지만 약간의 성능 저하 발생
private static final Comparator<PhoneNumber> COMPARATOR = Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
                                                          .thenComparingInt(pn -> pn.prefix)
                                                          .thenComparingInt(pn -> pn.lineNum);

@Override
public int compareTo(PhoneNumber pn) {
  return COMPARATOR.compare(this.pn);
}
```
- ```int``` 외 ```long```과 ```double```용 비교 메소드와 객체 참조용 비교자 생성 메소드도 제공
  - ```comparingLong```, ```thenComparingLong```
  - ```comparingDouble```, ```thenComparingDouble```
  - ```comparing```, ```thenComparing```

- 오버플로우나 부동소수점 계산 방식에 따른 오류를 낼 수 있는 경우 이 방식을 사용
  - 예를들어, hashcode 값의 차를 비교하는 경우
  ```JAVA
  // 오류나는 코드
  static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
      return o1.hashCode() - o2.hashCode(); // 오류 발생 가능성
    }
  }
  ```
  ```JAVA
  // 개선 1. 정적 compare 메소드 사용
  static Comparator<Object> hashCodeOrder = new Comparator<>() {
    public int compare(Object o1, Object o2) {
      return Integer.comapre(o1.hashCode(), o2.hashCode());
    }
  }
  ```
  ```JAVA
  // 개선 2. 비교자 생성 메소드 활용
  static Comparator<Object> hashCodeOrder = Comparator.comparingInt(o -> o.hashCode());
  ```
