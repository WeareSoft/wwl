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



#### 결론

## Reference

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

