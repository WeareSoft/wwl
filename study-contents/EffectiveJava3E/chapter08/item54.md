# [item 54] null이 아닌, 빈 컬렉션이나 배열을 반환하라 
### 사전 조사 
#### null 반환 금지
- null을 반환해서 방어적 코드를 작성하는 것 보다 빈 컬렉션을 반환하는 것이 낫다
- 성능 차이는 신경쓸만한 수준이 되지 않는다.
- 대안으로 빈 '불변' 컬렉션을 반환한다면 해결 가능하다.
  - `Collections.emptyList`메서드, `Collections.emptyMap`메서드, `Collections.emptySet`메서드 등
  - 최적화 작업에 해당하니 꼭 필요할때만 사용하자
- 배열 반환도 null 보다 크기가 0인 배열이 반환하자

#### `List`의 `toArray`
``` java
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    if ((size = elementData.length) != 0) {
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        // replace with empty array.
        this.elementData = EMPTY_ELEMENTDATA;
    }
}
```


---

### 스터디 요약 
- `List`의 `toArray` 메서드의 동작
  1. 인수로 받은 배열의 크기가 더 작다면, `List`가 가진 원소의 크기와 같은 배열을 새로 생성해서 `List`가 가진 원소를 복사 후 반환한다
  2. 같다면, 받은 인수에 `List`가 가진 원소를 복사 후 반환한다
  3. 크다면, 인수 배열에 `List`가 가진 원소를 복사 후 끝(`List` 기준) index에 `null`을 할당하여 반환한다
- 해당 동작에 (특히 3번) 대해 의문이 있었지만, '*호출자가 리스트의 요소 중 null이 없음을 알고 있는 경우에만 리스트의 길이를 결정하는 데 유용하다*'고 주석에서는 말한다

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

