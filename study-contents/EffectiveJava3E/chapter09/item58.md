# [item 58] 전통적인 for 문 보다는 for-each 문을 사용하라
## 정리

``` java
for (Iterator<Elemet> i = c.iterator(); i.hasNext();) {
    Element e = i.next();
    // e로 무언가를 한다.
}
```

``` java
for (int i=0 i<.a.lengh; i++) {
    // a[i]로 무언가를 한다.
}
```


``` java
for (Element e : elements) {
    // e로 무언가를 한다.
}
```
향상된 for 문(enhanced for statement)은 반복자와 index를 사용하지 않으니 코드가 깔끔해 지고, 어떤 컬렉션과 배열이든 처리할 수 있어 편리하다.

### for-each 사용이 불가능한 상황
- 파괴적인 필터링(destructive filtering)
- 변형(transforming)
- 병렬 반복(parallel itertion)

#### 개인적인 추가
이것 이외에 대표적으로 '인덱스에 의미가 있는 경우'인 것 같다. 몇번 인덱스부터 시작해야한다거나, 몇 번째 인덱스의 값을 뽑아야 된다거나 등... 보통은 컬렉션보다 배열을 쓸때 이런 경우가 많겠지

## 기타 조사
### `Iterable`과 `Iterator`
향상된 for 문을 사용하기 위헤서는 배열이거나 순회가 가능해야한다. 순회가 가능하다고 함은 `Iterable` 인터페이스를 구현했다는 것인데, 이 `Iterable` 인터페이스의 필수 구현 메서드는 다음과 같다.
``` java
/**
* Returns an iterator over elements of type {@code T}.
*
* @return an Iterator.
*/
Iterator<T> iterator();
```

때문에 어떤 `T`들을 가지고 있는 컨테이너를 순회를 위해서는 `Iterator` 인터페이스를 구현해야한다.
(문서를 포함한 전체 API들은 [다음](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)을 참고할 것. )
``` java
public interface Iterator<E> {

    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}
```
`ArrayList`에도 자체 `Iterator`가 구현되어 있다.

### fail-fast

> In systems design, a fail-fast system is one which immediately reports at its interface any condition that is likely to indicate a failure. Fail-fast systems are usually designed to stop normal operation rather than attempt to continue a possibly flawed process. Such designs often check the system's state at several points in an operation, so any failures can be detected early. The responsibility of a fail-fast module is detecting errors, then letting the next-highest level of the system handle them.
> 
> wikipedia - [fail-fast](https://en.wikipedia.org/wiki/Fail-fast)

어떤 시스템이 fail-fast 방식으로 동작한다는 것은 어떤 실패할 가능성이 있을 때 (상위) 인터페이스 보고하고, 그 레벨에서 오류를 처리하는 것을 말하는 듯 하다. "이 시스템은 fail-fast 하게 동작한다." 또는 "fail-fast 하게 구현했다" 라는 식으로 표현할 수 있지 않을까?

다른 블로그나 설명에서는
>  컬렉션 클래스들은 저장된 객체들에 대한 순차적 접근을 제공한다.
그러나, 순차적 접근이 모두 끝나기 전에 콜렉션 객체에 변경이 일어날 경우 순차적 접근이 실패되면서 ConcurrentModificationException 예외를 return하게 되는데 이를 fail-fast 방식이라고 부른다.
>
> 출처: https://itmore.tistory.com/entry/자바-FAILFAST-방식이란 [IT모아]

와 같이 (좁은 의미로) 설명하는데 원래 용어 자체는 컴퓨터 시스템 전반에서 사용하는 포괄적인 의미인 것 같다.

참고로 `Vector`, `Hashtable` 등의 `Collection`은 fail-fast하게 동작한다.

## `Iterator`와 `Enumeration`
둘다 순차적 순회에 관련된 인터페이스인데, 흔히 이에 관련된 블로그나 설명글을 보면 정보를 보면 `Iterator`는 fail-fast하게 동작하고 `Enumeration`은 그렇지 않다. 라고 표현되어 있다. 하지만 정확한 표현은 아닌 것 같다.

- 단적으로 `HashMap`의 (`KetSet`의) `Iterator`는 fail-fast하게 동작하지만, `ConcurrentHashMap`의 (`KetSet`의) `Iterator`는 그렇지 않다.
    - 하지만 `ConcurrentHashMap`이 `Enumeration`과 관련되어 있는 것은 맞다
    - `ConcurrentHashMap`의 내부 클래스인 `KeySetView`에서 반환하는 `KeyIterator` 내부 클래스가 `Iterator` 인터페이스를 구현했다
    - 이때 내부 클래스 `KeyIterator`에 `Enumeration` 인터페이스도 같이 구현되어있다
        ``` java
        static final class KeyIterator<K,V> extends BaseIterator<K,V>
            implements Iterator<K>, Enumeration<K> { ... }
        ```
    - (복잡...)
- 결론적으론 `Iterator` == fail-fast는 아닌 것
- `Enumeration` != fail-fast는 맞다

아래는 그 `KeyIterator` 클래스의 `next` 메서드이다.

``` java
public final K next() {
    Node<K,V> p;
    if ((p = next) == null)
        throw new NoSuchElementException();
    K k = p.key;
    lastReturned = p;
    advance();
    return k;
}
```
그리고 아래는 `HashSet`(fail-fast함)의 내부 클래스 `KeyIterator`의 `next` 메서드의 내부 구현이다.

``` java
 final Node<K,V> nextNode() {
    Node<K,V>[] t;
    Node<K,V> e = next;
    if (modCount != expectedModCount)
        throw new ConcurrentModificationException();
    if (e == null)
        throw new NoSuchElementException();
    if ((next = (current = e).next) == null && (t = table) != null) {
        do {} while (index < t.length && (next = t[index++]) == null);
    }
    return e;
}
```
확실히 `HashSet`에서는 `modCount`이라는 변수를 관리하고, `ConcurrentModificationException`예외를 발생시키는 것을 확인할 수 있지만, `ConcurrentHashMap`는 별다른 체크를 하지 않는다.

``` java
 /**
  * The number of times this HashMap has been structurally modified
  * Structural modifications are those that change the number of mappings in
  * the HashMap or otherwise modify its internal structure (e.g.,
  * rehash).  This field is used to make iterators on Collection-views of
  * the HashMap fail-fast.  (See ConcurrentModificationException).
  */
transient int modCount;
```

`ArrayList`의 `Iterator`를 구현한 내부 클래스인 `Itr`에서도 fail-fast한 동작을 확인할 수 있다.
``` java
public E next() {
    checkForComodification();
    int i = cursor;
    if (i >= size)
        throw new NoSuchElementException();
    Object[] elementData = ArrayList.this.elementData;
    if (i >= elementData.length)
        throw new ConcurrentModificationException();
    cursor = i + 1;
    return (E) elementData[lastRet = i];
}
```