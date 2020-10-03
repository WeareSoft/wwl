# [item 46] 스트림에서는 부작용 없는 함수를 사용하라 
스트림은 그저 또 하나의 API가 아닌, 함수형 프로그래밍에 기초한 패러다임이다.
## 스트림 패러다임
스트림 패러다임의 핵심은 계산을 일련의 변환(transformation)으로 재구성 하는 부분이다.
  - 이때 각 변환 단계는 가능한 한 이전 단계의 결과를 받아 처리하는 **순수 함수**여야한다.
  - 순수 함수란 오직 **입력**만이 결과에 영향을 주는 함수이다.

## 수집기(Collector)
스트림을 시용히려면 꼭 배워야 하는 새로운 개념이다. 상당히 복잡하저만 세부 내용을 잘 몰라도 이 API의 장점을 대부분 활용할 수 있다. 그저 축소(reduction) 전력을 캡슐화한 블랙박스 객체라고 생각하기 바란다.

수집기(`Collector`)를 생성하는 `Colletors`의 대표적인 메서드는 다음과 같다.
### `toList()`, `toSet()`, `toCollection(collectionFactory)`
스트림의 데이터들을 모아 리스트, 집합, 또는 지정 컬렉션 타입으로 반환해준다.
### `toMap()`
``` java
public static <T, K, U, M extends Map<K, U>>
    Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                              Function<? super T, ? extends U> valueMapper,
                              BinaryOperator<U> mergeFunction,
                              Supplier<M> mapSupplier) {
```
- `mergeFunction`: 중복 존재시 처리하는 함수를 지정
  - 디폴트는 예외를 던지는 작업. 
  - 예를들어 `(oldVal, newVal) -> newVal)`를 넘겨주면, 기존 키에 항상 새로운 값을 할당
- `mapSupplier`: 다른 종류의 맵 구현체를 사용하고 싶다면 명시한다.
- `toMap(keyMapper, valueMapper)`
- `toMap(keyMapper, valueMapper, (oldVal, newVal) -> newVal)`

### `groupingBy()`
입력으로 분류함수를 받고 출력을 원소들을 카테별로 모아 놓은 **맵을 담은 수집기**를 반환한다.
``` java
Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier,
                                          Supplier<M> mapFactory,
                                          Collector<? super T, A, D> downstream) {
```
- 추가 매개변수
  - `downstream`: 맵에서 리스트가 아닌 다른 형태의 값을 갖게 하고 싶다면 명시. (예를들어 `toSet()` 등)
    - 디폴트는 `Map<K, List<E>>`
  - `mapFactory`: `HashMap`이 아닌 다른 종류의 맵의 구현체를 사용하고 싶다면 명시.

### 그 외
- `groupByConcurrent` 메서드
  - `ConcurrentHashMap`을 담은 수집기를 반환하는 메서드
- `partitioningBy` 메서드
  - 분류기로 predicate를 받는다.
  - 키가 Boolean인 맵을 반환
### `maxBy()`, `minBy()`
Steam 인터페이스의 min과 max 메서드를 살짝 일반화한 것.
  - `java.util.function.BinaryOperator`의 minBy와 maxBy 메서드가 반환하는 이진 연산자의 수집기 버전

### `joining()`
`CharSequence` 타입의 구문문자(delemiter)를 매개 변수로 받는다. 연결 부위에 이 구문 문자를 삽입한 문자열을 반환한다.



---

### 스터디 요약 
- downstream의 역할 및 개념
  - 참고: [Class Collectors 개념](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html)

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

