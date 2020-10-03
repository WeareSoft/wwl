# [item 32] 제네릭과 가변인수를 함께 쓸 때는 신중하라

### 사전 조사
#### [Example](https://brunch.co.kr/@oemilk/202)
```java
private static void heap_pollution(List<String>... stringLists){
    // List<String>[] stringLists = new List<String>[stringLists.length];

    List<Integer> intList = List.of(42);
    // intList.get(0) = (Integer) 42;

    Object[] objects = stringLists; // 배열이 공변하기 때문에 가능
    // Object[] objects = (Object[])(List<String>[]) stringLists;

    objects[0] = intList; // 힙 오염 발생
    // 혼합된 배열
    // (Object[])(List<Integer>)
    // (Object[])(List<String>[])
    // 제네릭은 타입 소거 방식으로 구현.
        // List<String>[] -> List[]
        // List<Integer> -> List
    // 배열은 제네릭과 다르게 런타임시 타입을 검사한다.
        // stringLists[0] = (List) intList;
        // ArrayStoreException 발생 안됨
        // Heap pollution 발생
    
    String s = stringLists[0].get(0); // ClassCastException
    // (List<String>) stringLists[0] = (List<Integer>) intList;
    // ((List<Integer>) intList).get(0) = 42; // not string
    // 런타임 시, ClassCastException 발생
}
```

#### [힙 오염(Heap pollution)](https://en.wikipedia.org/wiki/Heap_pollution)
- 매개변수화 타입의 변수가 타입이 다른 객체를 참조하는 경우 발생.
- 힙오염은 런 타임에 ClassCastException를 유발한다.

---

#### 매개변수화 타입의 변수가 타입이 다른 객체를 참조하면 "힙 오염" 발생
- [코드 32-1 구체적인 설명](https://brunch.co.kr/@oemilk/202)

#### 타입 안전한 메서드
- 자바 라이브러리에서 제공하는 제네릭 타입의 가변 인수는 type safe
- [List<T> asList(T... a)](https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#asList(T...))
- [Collections.addAll(Collection<? super T> c, T... elements)](https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#addAll(java.util.Collection,%20T...))
- [EnumSet<E> of(E first, E... rest)](https://docs.oracle.com/javase/7/docs/api/java/util/EnumSet.html#of(E,%20E...))

#### [@SafeVarargs]https://docs.oracle.com/javase/7/docs/api/java/lang/SafeVarargs.html]
- 메서드 작성자가 해당 메서드의 타입 안정성을 보장하는 경우에 해당 annotation을 명시
  - 재정의할 수 없는 메서드에만 달아야 한다.
- 타입 안정성을 보장하기 위한 조건
  - 제네릭 가변 인수로 인해 생성된 배열에 아무것도 저장하지 않는다. 
    - 가변 인수 메서드를 호출할 때 varags 매개변수를 담는 제네릭 배열이 만들어진다.
  - 생성된 배열 또는 복제본의 참조가 밖으로 노출되지 않는다.
  - 즉, varags 매개변수 배열이 호출자로부터 그 메서드로 순수하게 인수들을 전달받기만 한다면 type safe 

#### 제네릭 varags 매개변수를 안전하게 사용하는 메서드
- 위의 조건을 만족한 상태로, @SafeVarargs 사용
- varargs 매개변수를 List 매개변수로 변경
  - Ex. `flatten(List.of(frends, romans, countrymen));`
  - 단점: 클라이언트 코드 지저분, 속도 감소 

#### 결론

## Reference
- [https://brunch.co.kr/@oemilk/202](https://brunch.co.kr/@oemilk/202)

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

