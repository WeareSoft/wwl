# [item 26] 로 타입은 사용하지 말라 
클래스와 인터페이스 선언에 타입 매개변수(type patameter)가 쓰이면, 이를 **제네릭 클래스** 혹은 **제네렉 인터페이스**라 한다. 이 둘을 통틀어 **제네릭 타입**(generic type)이라고 한다.

## 로(raw) 타입
- 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않을 때를 말한다.
  - ex) `List<E>`의 로 타입은 `List`이다.
- 타입 선언에서 제네릭 타입 정보가 전부 지워진 것 처럼 동작한다.

> 로 타입을 쓰면 제네릭이 안겨주는 안정성과 표현력을 모두 잃게된다.
### 로 타입의 안좋은 예시
``` java
private final Collection stamps = ...;
```

``` java
// 실수로 동전을 넣는다.
stamps.add(new Coin(...)); // "unchecked call" 경고를 내뱉는다.
```
컬렉션에서 이 동전을 다시 꺼내기 전에는 오류를 알아차리지 못한다.
``` java
for (Iterator i = stamps.iterator(); i.hasNext();) {
    Stamp stamp = (Stamp) i.next(); // ClassCastException을 던진다.
    stamp.cancel();
}
```
오류는 가능한 발생 즉시, 이상적으로는 컴파일할 때 발견하는 것이 좋다. 런타임때에 발견한다면 문제를 겪는 코드와 원인을 제공한 코드가 물리적으로 상당히 떨어져 있을 가능성이 커진다.

### 개선 - 매개변수화된 컬렉션 타입
``` java
private final Collection<Stamp> stamps = ...;
```
이렇게 선언하면 컴퍼일러는 stamps에는 `Stamp`의 인스턴스만 넣아야 함을 인지하게 된다. 따라서 아무런 경고 없이 컴파일된다면 의도대로 동작할 것임을 보장한다.

컴파일러는 컬렉션에서 원소를 꺼내는 모든 곳에서 보이지 않는 형변환을 추가하여 절대 실패하지 않음을 보장한다. 

### 로 타입의 존재 이유
- **호환성** 때문이다.
  - 자바가 제네릭을 받아들일 때 까지 거의 10년이 걸린 탓에 제네렉 없이 짠 코드가 이미 세상을 뒤덮어 버렸다.
  - 마이그레이션 호환성을 위해 제네릭 구현에는 소거 방식을 사용하기로 했다.

## `List`와 `List<Object>`
`List`같은 로 타입은 사용해서는 안되지만,  `List<Object>`처럼 임의 객체를 허용하는 매개변수화 타입은 괜찮다.
- `List`: 제네릭 타입과는 아무런 상관이 없다.
- `List<Object>`: 제네릭 타입을 이용하여 모든 타입을 허용한다는 것은 컴파일러에게 전달한다.

> `List<Object>` 같은 매개변수화 타입을 사용할 때와 달리 `List` 같은로 타입을 사용하면 타입 안정성을 잃게된다.

### 로 타입 사용의 예시1
``` java
// 코드 26-4 런타임에 실패한다. - unsafeAdd 메서드가 로 타입(List)을 사용 (156-157쪽)
public class Raw {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, Integer.valueOf(42));
        String s = strings.get(0); // 컴파일러가 자동으로 형변환 코드를 넣어준다.
    }

    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }
}
```
- 이 코드는 컴파일은 되지만 경고가 발생한다.
  - 로 타입인 `List`를 사용하였기 때문이다.
- 이대로 실행하면 `strings.get(0)`의 결과를 형변환하려 할 때 `ClassCastException`을 던진다.
  - `Integer`를 `String`으로 변환하려 시도했기 때문
- 이 형변환은 컴파일러가 자동으로 만들어준 것이라 보통은 실패하지 않는다.
  - 컴파일러의 경고를 무시했기 때문에 런타임 에러가 발생한 것

### 로 타입 사용의 예시2
2개의 집합(Set)을 받아 공통 원소를 반환하는 메서드를 작성한다고 해보자.
``` java
static int numElementsInCommon(Set s1, Set s2) { // 로 타입 사용
    int result = 0;
    for(Object o1 : s1) {
        if(s2,contains(o2)) result++;
        return result;
    }
}
```
- 이 메서드는 동작은 하지만 로타입을 사용해 안전하지 않다.
- 대신 **비한정적 와일드카드 타입(unbounded wildcard type)** 을 사용하는 것이 좋다.

## 비한정적 와일드카드 타입(unbounded wildcard type)
제네릭 타입을 쓰고 싶지만 실제 타입 매개변수가 무엇인지 신경쓰고 싶지 않는다면 물음표(?)를 사용하자. 타입 안전하며 유연하다.
- ex) `Set<E>`의 비 한정적 와일드카드 타입은 `Set<?>`

[로 타입 사용의 예시2]의 코드를 와일드카드 타입을 사용해 다시 선언한 모습이다.
``` java
static int numElementsInCommon(Set<?> s1, Set<?> s2) { ... }
```

### 비한정적 와일드 카드(`Set<?>`)와 로 타입(`Set`)의 차이
- **비한정적 와일드카드 타입**은 <u>안전</u>하고, **로 타입**은 <u>그렇지 않다</u>.
  - 로 타입 컬렉션에는 아무 원소나 넣을 수 있으니 타입 불변식을 훼손하기 쉽다.
  - 반면 `Collection<?>`에는 (null 외에는) 어떤 원소도 넣을 수 없다.
    - 다른 원소를 넣으려 하면 컴파일 시 에러가 발생한다.

즉, 비한정적 와일드카드 타입은 컬렉션 타입 불변식을 훼손하지 못하게 막았다. 구체적으로는 (null 이외의) 어떤 원소도 `Collection<?>`에 넣지 못하게 했으며 컬렉션에서 꺼낼 수 있는 객체 타입도 전혀 알 수 없게 했다.
  - 이러한 제약을 받아들일 수 없다면 제네릭 메서드나 한정적 와일드카드 타입을 사용하면 된다.


## 로 타입을 사용하는 예외
### 1. class 리터럴에는 로 타입을 써야한다.
- 자바 명세는 class 리터럴에 매개변수화 타입을 사용하지 못하게했다(배열과 기본 타입은 허용).
  - ex1) `List.class`, `String[].class`, `int.class`는 허용
  - ex2) `List<String>.class`, `List<?>.class`는 비허용
### 2. `instanceof`
- 런타임에는 제네릭타입 정보가 지워지므로 `instanceof` 연산자는 비한정적 와일드카드 타입 이외의 매개변수화 타입에는 적용할 수 없다.
- 로 타입이든 비한정적 와일드카드 타입이든 `instanceof`는 완전히 똑같이 동작한다.
  - 꺾쇠괄호와 물음표는 코드만 지저분하게 만드므로 차라리 로 타입을 쓰는 편이 깔끔하다.
    ``` java
    if(o instaceof Set) {       // 로 타입
        Set<?> s = (Set<?>) o;  // 와일드카드 타입, 사용시에는 형변환이 필요하다.
        ...
    }
    ```

## 용어
한글 용어|영문 용어|예|아이템
-|-|-|-|-
매개변수화 타입|parameterized type|`List<String>`|아이템 26
실제 타입 매개변수|actual type parameter|`String`|아이템 26
제네릭 타입 매개변수|generic type|`List<E>`|아이템 26, 29
정규 타입 매개변수|formal type parameter|`E`|아이템 26
비한정적 와일드카드 타입|unbounded wildcard type|`List<?>`|아이템 26
로 타입|raw type|`List`|아이템 26
한정적 타입 매개변수|bounded type parameter|`<E extends Number>`|아이템 29
재귀적 타입 한정|recusive type bound|`<T extends Comparable<T>>`|아이템 30
한정적 와딜드카드 타입|bounded wildcard type|`List<? extends Nuber>`|아이템 31
제네릭 메서드|gereric method|`static <E> List<E>`<br>`asList(E[] a)`|아이템 30
타입 토큰|type token|`String.class`|아이템 33

### 사전 조사
#### 소거(erasure)
- 소거
  - 컴파일 시 원소의 타입 정보를 지우며 런타임에는 알 수 없다
  - 결국 런타임에는 로 타입처럼 동작하나, 컴파일 타임에 타입 체킹을 한 셈
  - 제네릭이 없던 시절의 코드와 호환하기 위한 궁여지책






- https://medium.com/@joongwon/java-java%EC%9D%98-generics-604b562530b3


<!-- ## 2.5 Array
It is instructive to compare the treatment of lists and arrays in Java, keeping in mind
the Substitution Principle and the Get and Put Principle.
In Java, array subtyping is covariant, meaning that type S[] is considered to be a subtype
of T[] whenever S is a subtype of T. Consider the following code fragment, which allocates an array of integers, assigns it to an array of numbers, and then attempts to
assign a double into the array:
``` java
Integer[] ints = new Integer[] {1,2,3};
Number[] nums = ints;
nums[2] = 3.14; // array store exception
assert Arrays.toString(ints).equals("[1, 2, 3.14]"); // uh oh!
```
Something is wrong with this program, since it puts a double into an array of integers!
Where is the problem? Since `Integer[]` is considered a subtype of Number[], according
to the Substitution Principle the assignment on the second line must be legal. Instead,
the problem is caught on the third line, and it is caught at run time. When an array is
allocated (as on the first line), it is tagged with its reified type (a run-time representation
of its component type, in this case, Integer), and every time an array is assigned into
(as on the third line), an array store exception is raised if the reified type is not compatible with the assigned value (in this case, a double cannot be stored into an array of
Integer).
In contrast, the subtyping relation for generics is invariant, meaning that type `List<S>` is
not considered to be a subtype of `List<T>`, except in the trivial case where S and T are
identical. Here is a code fragment analogous to the preceding one, with lists replacing
arrays:
``` java
List<Integer> ints = Arrays.asList(1,2,3);
List<Number> nums = ints; // compile-time error
nums.set(2, 3.14);
assert ints.toString().equals("[1, 2, 3.14]"); // uh oh!
```
Since `List<Integer>` is not considered to be a subtype of List<Number>, the problem is detected on the second line, not the third, and it is detected at compile time, not runtime Wildcards reintroduce covariant subtyping for generics, in that type `List<S>` is considered to be a subtype of List<? extends T> when S is a subtype of T. Here is a third variant of the fragment:
``` java
List<Integer> ints = Arrays.asList(1,2,3);
List<? extends Number> nums = ints;
nums.set(2, 3.14); // compile-time error
assert ints.toString().equals("[1, 2, 3.14]"); // uh oh!
```
As with arrays, the third line is in error, but, in contrast to arrays, the problem is detected
at compile time, not run time. The assignment violates the Get and Put Principle, because you cannot put a value into a type declared with an extends wildcard.
Wildcards also introduce *contravariant* subtyping for generics, in that type `List<S>` is
considered to be a subtype of `List<? super T>` when S is a supertype of T (as opposed
to a subtype). Arrays do not support contravariant subtyping. For instance, recall that
the method count accepted a parameter of type Collection<? super Integer> and filled
it with integers. There is no equivalent way to do this with an array, since Java does
not permit you to write `(? super Integer)[]`.

Detecting problems at compile time rather than at run time brings two advantages, one
minor and one major. The minor advantage is that it is more efficient. The system does
not need to carry around a description of the element type at run time, and the system
does not need to check against this description every time an assignment into an array
is performed. The major advantage is that a common family of errors is detected by the
compiler. This improves every aspect of the program’s life cycle: coding, debugging,
testing, and maintenance are all made easier, quicker, and less expensive.

Apart from the fact that errors are caught earlier, there are many other reasons to prefer
collection classes to arrays. Collections are far more flexible than arrays. The only operations supported on arrays are to get or set a component, and the representation is
fixed. Collections support many additional operations, including testing for containment, adding and removing elements, comparing or combining two collections, and
extracting a sublist of a list. Collections may be either lists (where order is significant
and elements may be repeated) or sets (where order is not significant and elements may
not be repeated), and a number of representations are available, including arrays, linked
lists, trees, and hash tables. Finally, a comparison of the convenience classes Collec
tions and Arrays shows that collections offer many operations not provided by arrays,
including operations to rotate or shuffle a list, to find the maximum of a collection, and
to make a collection unmodifiable or synchronized.

Nonetheless, there are a few cases where arrays are preferred over collections. Arrays
of primitive type are much more efficient since they don’t involve boxing; and assignments into such an array need not check for an array store exception, because arrays
of primitive type do not have subtypes. And despite the check for array store exceptions,
even arrays of reference type may be more efficient than collection classes with the
current generation of compilers, so you may want to use arrays in crucial inner loops.
As always, you should measure performance to justify such a design, especially since
future compilers may optimize collection classes specially. Finally, in some cases arrays
may be preferable for reasons of compatibility.

To summarize, it is better to detect errors at compile time rather than run time, but
Java arrays are forced to detect certain errors at run time by the decision to make array
subtyping covariant. Was this a good decision? Before the advent of generics, it was
absolutely necessary. For instance, look at the following methods, which are used to
sort any array or to fill an array with a given value:
``` java
public static void sort(Object[] a);
public static void fill(Object[] a, Object val);
```
Thanks to covariance, these methods can be used to sort or fill arrays of any reference
type. Without covariance and without generics, there would be no way to declare
methods that apply for all types. However, now that we have generics, covariant arrays
are no longer necessary. Now we can give the methods the following signatures, directly
stating that they work for all types:
``` java
public static <T> void sort(T[] a);
public static <T> void fill(T[] a, T val);
```

In some sense, covariant arrays are an artifact of the lack of generics in earlier versions
of Java. Once you have generics, covariant arrays are probably the wrong design choice,
and the only reason for retaining them is backward compatibility.
Sections Section 6.4–Section 6.8 discuss inconvenient interactions between generics
and arrays. For many purposes, it may be sensible to consider arrays a deprecated
type.We return to this point in Section 6.9. -->