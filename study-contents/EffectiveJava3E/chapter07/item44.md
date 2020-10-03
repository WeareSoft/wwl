# [item 44] 표준 함수형 인터페이스를 사용하라 
<!-- ### 사전 조사  -->

자바가 람다를 지원하면서 템플릿 메서드 패턴의 매력이 줄고 함수 객체를 받는 정적 팩터리나 생성자를 이용하는 방법이 선호되고 있다.

## `LinkedHashMap.removeEldestEntry(...)` 사용 예

### 있는 그대로 상속받아 Overriding 하는 방법
``` java
public static void main(String[] args) {
    int maxSize = 10;
    FixedLinkedHashMap fixedLinkedHashMap = new FixedLinkedHashMap(maxSize);
}

static class FixedLinkedHashMap extends LinkedHashMap<String, Integer> {
    final int maxSize;

    FixedLinkedHashMap(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
        return this.size() > maxSize;
    }
}
```
이 방식은 잘 동작하지만, 람다를 사용한다면 더 잘 해낼 수 있다. 오늘날 다시 구현한다면 함수 객체를 받는 정적 팩터리나 생성자를 제공했을 것이다.

### 함수형 인터페이스를 구현하는 방법
``` java
public static void main(String[] args) {
    int maxSize = 10;
    LambdaLinkedHashMap lambdaLinkedHashMap = new LambdaLinkedHashMap((map, eldest) -> map.size() > maxSize);
}

static class LambdaLinkedHashMap extends LinkedHashMap<Integer, String> {
    private EldestEntryRemovalFunction<Integer, String> removalFunction;

    LambdaLinkedHashMap(EldestEntryRemovalFunction<Integer, String> removalFunction) {
        this.removalFunction = removalFunction;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return removalFunction.remove(this, eldest);
    }
}

@FunctionalInterface
interface EldestEntryRemovalFunction<K, V> {
    boolean remove(Map<K, V> map, Map.Entry<K, V> eldest);
}
```
잘 돌아가지만 굳이 사용할 이유는 없다. 다음과 같은 이유로 위처럼 구현하기 보다 표준 함수형 인터페이스(이 경우에는 `BiPredicate`)를 사용하길 권장한다.
- API가 다루는 개념의 수가 줄어 익히기 더 쉬워진다.
- 유용한 디폴트 메서드들을 많이 제공하므로 다른 코드와의 상호운용성도 크게 좋아질 것이다.

### 표준 함수형 인터페이스를 사용하는 방법
위와 동작 방식은 같다.
``` java
public static void main(String[] args) {
    int maxSize = 10;
    BiPredicateLinkedHashMap lambdaLinkedHashMap = new BiPredicateLinkedHashMap((map, eldest) -> map.size() > maxSize);

}

static class BiPredicateLinkedHashMap extends LinkedHashMap<Integer, String> {
    private BiPredicate<Map<Integer, String>, Map.Entry<Integer, String>> removeEldestEntryPredicate;
    BiPredicateLinkedHashMap(BiPredicate<Map<Integer, String>, Map.Entry<Integer, String >> removeEldestEntryPredicate) {
        this.removeEldestEntryPredicate = removeEldestEntryPredicate;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return removeEldestEntryPredicate.test(this, eldest);
    }
}
```

## 표준 함수형 인터페이스의 종류
`java.util.function` 패키지에는 43개의 인터페이스가 존재한다.

인터페이스|함수 시그니처|예
---|---|---
`UnaryOperator<T>`| `T apply(T t)`| `String::toLowerCase`
`BinaryOperator<T>`|`T apply(t t1, T t2)`|`BigInteger::add`
`Predicate<T>`|`boolean test(T t)`|`Collection::isEmpty`
`Function<T, R>`|`R apply(T t)`|`Arrays::asList`
`Supplier<T>`|`T get()`|`Instance::now`
`Consumer<T>`|`void accept(T t)`|`System.out::println`

## 박싱된 기본타입
표준 함수형 인터페이스의 대부분은 기본 타입만 지원한다. 그렇다고 기본 함수형 인터페이스에 박싱된 기본 타입을 넣어 사용하지는 말자. 계산량이 많을 때는 성능이 처참히 느려질 수 있다.

## 직접 작성하기 vs 표준 함수형 인터페이스 사용하기
다음 조건에 맞다면 표준 함수형 인터페이스 사용하는 것 보다는 직접 작성하는 것을 고려해봐도 좋다.
- 자주 쓰이며, 이름 자체가 용도를 명확히 설명해준다.
- 반드시 따라야 하는 규약이 있다.
- 유용한 디폴트 메서드를 제공할 수 있다.
대표적으로 `Compatator<T>` 인터페이스가 있다. (구조적으로는 `ToIntBiFunction<T, U>`와 동일)
    - https://docs.oracle.com/javase/7/docs/api/java/util/Comparator.html
    - https://docs.oracle.com/javase/8/docs/api/java/util/function/ToIntBiFunction.html

## `@FunctionalInteface`
직접 만든 함수형 인터페이스에는 항상 `@FunctionalInteface`애너테이션을 사용하라.
- 해당 클래스의 코드나 설명 문서를 읽을 이에게 그 인터페이스가 람다용으로 설계된 것임을 알려준다.
- 해당 인터페이스가 추상 메서드를 오직 하나만 가지고 있어애 컴파일 되가 해준다.
- 그 결과 유지보수 과정에서 누군가 실수로 메서드를 추가하지 못하게 막아준다.
---

### 스터디 요약 
- 직접 구현한 함수형 인터페이스 보다 표준 함수형 인터페이스를 쓰기를 권장하고 그 종류를 제시하는 아이템

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

