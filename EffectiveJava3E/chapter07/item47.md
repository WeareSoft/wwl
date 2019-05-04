# [item 47] 반환 타입으로는 스트림보다 컬렉션이 낫다 
### 사전 조사
#### 스트림(Stream)과 컬렉션(Collection)
- 스트림은 외부 반복을 통해 작업하는 컬렉션과는 달리 내부 반복(internal iteration)을 통해 작업을 수행한다.
  - `Iterable`를 확장하지 않았기 때문에 (외부) iteration을 지원하지 않는다.
  - 흥미로운 건 `Stream`은 `Iterable` 인터페이스가 정의한 추상메서드를 포함하고, 그 방식 그대로 동작한다.
- 스트림은 재사용이 가능한 컬렉션과는 달리 단 한 번만 사용할 수 있다.

#### `Stream` vs `Collection`
- **사용자의 편의성을 고려**
  - 어느 하나만 쓰일 것을 알고 있다면, 그것을 반환하도록 하면된다.
  - 공개 API를 작성한다면 메서드의 반환타입을 `Collection`이나 그 하위 타입을 쓰는 것이 최선이다.
    - `Colelction` 인터페이스는 손쉽게 스트림도 지원 가능하다.
- **다른 고려해야할 점**
  - 하지만, 컬렉션을 반환한다는 이유로 덩치 큰 시퀀스를 메모리에 올려서는 안된다.
    - 간단하게 표현할 방법이 있다면 전용 컬렉션을 구현하는 것을 고려하라.
  - 그냥 단순하게 "구현하기 쉬운" 쪽을 선택해도 괜찮다.
#### 한계
- 리스트(컬렉션)을 스트림으로 변환 어댑터를 사용할 때는 성능 감소와 코드가 지저분해지는 것을 감안해야 한다.

#### `Stream<E>` to `Iterable<E>` 어댑터
1. 원본(코드 47-3)
``` java
public static <E> Iterable<E> iterableOf(Stream<E> stream) {
    return steam::iterator;
}
```

2. 메서드 참조가 아닌 람다식
``` java
public static <E> Iterable<E> iterableOf(Stream<E> stream) {
    return () -> stream.iterator();
}
```

3. 람다식이 아닌 익명 메서드
``` java
public static <E> Iterable<E> iterableOf(Stream<E> stream) {
    return new Iterable<T>() {
        @Override
        public Iterator<T> iterator() {
            return stream.iterator();
        }
    };
}
```

순서상으로 따지자면 3 ➔ 2 ➔ 1이다.
사실 3 ➔ 2 과정이 너무 뛰어넘은 듯 한데, 자바가 똑똑하게 타입추론을 잘 해줘서 그런 듯 하다. 다음 코드를 보면 이해가 더 빠를지도.
``` java
Iterable<String> itr = () -> stream.iterator(); // 제네릭 타입이 String인 것은 그냥 예
```
사실 그래도, 메서드(참조)가 갑자기 객체로 변한다는 괴리감은 잘 떨쳐지지가 않는다.

#### 어댑터 사용
``` java
// 이전 (코드 47-2)
for(ProcessHAndle ph : (Iterable<ProcessHandle>) ProcessHandle.allProcesses()::iterator) {
    // 프로세스 처리
}

// 이후
for(ProcessHAndle ph : IterableOf(ProcessHandle.allProcesses())) {
    // 프로세스 처리
}
``` 
- 결론
  - for-each 구문에서 콜론 이후에 나오는 객체는 `Iterable<E>`의 구현체여야 한다.
  - 이번 아이템 예제에서 나온 어댑터는 새로 만들어진 `Iterable<E>` 객체를 반환하는 것이다.
    - 이때, 추상 메서드를 구현하기 위해 메서드 참조를 받아 이를 사용한다.


---

### 스터디 요약 
- 메서드 참조와 자동 형변환에 대하여 [#](#streame-to-iterablee-어댑터)
  - 어째서 Override에서 반환할 메서드 참조만을 넘겨주는 것으로 `Iterable<E>`로 자동 형변환에 대한 이슈가 있었음

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

