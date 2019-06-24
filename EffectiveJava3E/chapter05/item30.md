# [item 30] 이왕이면 제네릭 메서드로 만들라

### 사전 조사
### 제네릭 메서드
- 클래스와 마찬가지로 메서드도 제네릭이 가능하다면 사용하자.
- 사용자 측에서 형변환하는 것보다 훨씬 안전하고 유연해진다.
- 제네릭 클래스 작성법과 비슷하다.
```java
public static Set union(Set s1, Set s2) { // => <E> Set<E> union(Set<E> s1, Set<E> s2)
    Set result = new HashSet(s1); // => Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
```
- 이 메서드는 입력 2개, 반환 1개의 타입이 모두 같아야 하므로 **한정적 와일드카드 타입**을 사용하여 더 유연하게 개선할 수 있다.
```java
public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
```

### 제네릭 싱글턴 팩터리
- 때때로 불변 객체를 여러 타입으로 활용할 수 있게 만들어야 할 때가 있는데, 이때는 제네릭 싱글턴 팩터리를 만들면 된다.
- 제네릭은 런타임에 타입 정보가 소거되므로 하나의 객체를 어떤 타입으로든 매개변수화 할 수 있다.
- `Collections.reverseOrder`, `Collections.emptySet`이 좋은 예제이다.
```java
@SuppressWarnings("unchecked")
public static <T> Comparator<T> reverseOrder() {
    return (Comparator<T>) ReverseComparator.REVERSE_ORDER;
}
```
- 만약 제네릭을 쓰지 않았다면 요청 타입마다 형변환하는 정적 팩터리를 만들었어야 할 것이다. (타입 별로 정적메서드가 1개씩)

### 재귀적 타입 한정
- 자기 자신이 들어간 표현식을 사용하여 타입 매개변수의 허용 범위를 한정할 수 있다.
- 우리가 자주 사용하는 `Comparable`에서 볼 수 있다.
  - 다음과 같이 타입 매개변수을 **한정적으로** 기술해주는 방식이다. 
  - 이를 통해 모든 타입 `E`는 자신과 비교할 수 있다라는 것을 나타냈다.
```java
public static <E extends Comparable<E>> E max(Collection<E> c)
```
- `max`메서드의 리턴값은 `Comparable<E>`을 구현했으므로, 다른 `E`와 비교할 수 있다.


## Reference


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

