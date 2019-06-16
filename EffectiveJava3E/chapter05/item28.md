# [item 28] 배열보다는 리스트를 사용하라
배열과 제네릭 타입에는 중요헌 차이가 두가지가 있다. **공변**과 **실체화**이다.

## 공변(covarian; 共變)
- 배열은 공변이다.
  - `Sub`가 `Super`의 하위 타입이라면 배열 `Sub[]`는 배열 `Super[]`의 하위타입이 된다.
- 반면 제네릭은 불공변(invarian; 不共變)이다.
  - 서로 다른 타입 `Type1`과 `Type2`가 있을 때, `List<Type1>`은 `List<Type2>`의 하위타입도 아니고 상위타입도 아니다.

### 예시
#### <u>코드 28-1</u> 문법상 허용되지만 런타임에 실패한다.
``` java
Object[] objectArray = new Long[1];
objectArray[o] = "타입이 달라 넣을 수 없다."; // ArrayStoreException을 던진다.
```
#### <u>코드 28-2</u> 컴파일되지 않는다.
``` java
List<Object> ol = new ArrayList<Long>();
ol.add("타입이 달라 넣을 수 없다.");
```

어느쪽이든 `Long`용 저장소에 `String`을 넣을 수는 없다. 다만 배열에서는 그 실수를 **런타임**에야 알게되지만, 리스트를 사용하면 **컴파일**할 때 바로 알 수 있다.

## 실체화(reify)
배열은 실체화된다.
- 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다.
  - 그래서 코드 28-1에서 보듯 `Long`배열에 `String`을 넣으려 하면 `ArrayStoreException`이 발생한다.
- 제네릭은 타입 정보가 런타임에소는 소거(erasure)된다.
  - 원소 타입을 컴파일 타임에만 검사하여 런타임에는 <u>알수조차 없다</u>는 뜻이다.
  - 소거는 제네릭이 지원되기 전의 레거시 코드와 제네릭 타입을 함께 사용할 수 있게 해주는 메커니즘으로, 자바5가 제네릭으로 순조롭게 전환될 수 있도록 해줬다.

이상의 주요 차이로 인해 배열과 제네릭은 잘 어우러지지 못한다.
- 배열은 제네렉 타입, 매개변수화 타입, 타입 매개변수로 사용할 수 없다.
  - `new List<E>[]`, `new List<String>[]`, `new E[]` 식으로 작성하면 컴파일 시 제네릭 배열 생성 오류를 일으킨다.

## 제네릭 배열을 만들지 못하게 막은 이유
타입 안전(type-safe)하지 않기 때문이다.
- 이를 허용한다면 컴파일러가 자동 생성한 형변환 코드에서 런타임에 `ClassCastException`이 발생할 수 있다.

### 예시
``` java
List<String>[] stringLists = new List<String>[1];   // (1) 크기가 1인 List<String> 배열 생성
List<Integer> intList = List.of(42);                // (2) List<Integer> 생성
Object[] objects = stringLists;                     // (3) Object 배열 선언 후 List<Integer> 배열 할당
objects[0] = intList;                               // (4) 그 배열 첫번째에 List<Integer> 할당
String s = stringLists[0].get(0);                   // (5) List<String> 에는...
```
제네릭 배열을 생성하는 (1)이 허용된다고 가정해보자.
- (2)는 원소가 하나인 `List<Ingeter>`를 할당한다.
- (3)은 (1)에서 생성한 `List<String>`의 배열은 `Object` 배열에 할당한다.
  - 배열은 공변이니 아무 문제 없다.
- (4)는 (2)에서 생성한 `List<Ingeter>`의 인스턴스를 `Object` 배열의 첫 원소로 저장한다. 
  - 제네릭은 소거 방식으로 구현되어 있어서 이 역시 성공한다.
  - 즉, 런타임에는 `List<Ingeter>`인스턴스의 타입은 단순히 `List`가 되고, `List<Ingeter>[]`의 타입은 `List[]`가 된다.
  - 따라서 (4)에서도 `ArrayStoreException`을 일으키지 않는다.

문제는 지금부터이다.

- `List<String>` 인스턴스만 담겠다고 선언한 `stringLists`배열에서는 지금 `List<Ingeter>` 인스턴가 저장돼 있다.
- 그리고 (5)는 이 배열의 처음 리스트에서 첫 원소를 꺼내려고 한다.
  - 컴파일러는 꺼낸 원소를 자동으로 `String`으로 형변환 하는데, 이 원소는 `Integer`이므로 런타임에 `ClassCastException`이 발생한다.

이런 일을 방지하려면 (제네릭에서 배열이 생성되지 않도록) (1)에서 컴파일 오류를 내야한다.

## 실체화 불가 타입
`E`, `List<E>`, `List<String>` 같은 타입을 실체화 불가타입(non-reifiable type)이라 한다.

- 실체화 되지 않아서 런타임에는 컴파일타임보다 타입 정보를 적게 가지는 타입이다.
- 소거 메커니즘 때문이다.
- 매개변수화 타입 가운데 실체화될 수 있는 타입은 `List<?>`와 `Map<?, ?>`와 같은 비한정적 와일드카드 타입 뿐이다.

### 사전 조사
#### 공변(covarian; 共變)
- 공변
  - 배열은 공변이다.
    - [[Naftalin07, 2.5]](https://pdfs.semanticscholar.org/8acb/1b38046030f6d8bf964991a5c26918f7c176.pdf?_ga=2.24918450.981992488.1560636958-2069674838.1560636958) - 22p
    - [[JLS 4.10]](https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.10)

---

- 배열과 제네릭타입(Ex. 리스트)의 차이
    - 첫 번째. 공변(Covariant)과 불공변(Invariant)
        - 배열은 공변이다.
            - Sub가 Super의 하위 타입이라면 배열 Sub[]는 배열 Super[]의 하위 타입이 된다.
        - 제네릭은 불공변이다.
            - 서로 다른 타입 Type1, Type2가 있을 떄, List<Type1>은 List<Type2>ㅇ의 하위 타입도 아니고 상위 타입도 아니다.
    - 두 번째. 배열은 실체화(Reify), 제네릭은 소거(Erasure)
        - 배열은 실체화된다.
            - 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다.
        - 제네릭은 타입 정보가 런타임에는 소거된다.
            - 원소 타입을 컴파일타임에만 검사하며 런타임에는 알수조차 없다.
- 배열은 제네릭 타입, 매개변수화 타입, 타입 매개변수로 사용할 수 없다.
    - `new List<E>[]`, `new List<String>[]`, `new E[]`
    - 컴파일할 때 제네릭 배열 생성 오류 발생
- 제네릭 배열을 만들지 못하게 막은 이유?
    - 타입 안전하지 않기 때문
    - 허용한다면 => 컴파일러가 자동 생성한 형변환 코드에서 런타임에 ClassCastException이 발생할 수 있다.
    - 제네릭 타입 취지: 런타임에 ClassCastException이 발생하는 것을 막아준다.
- 제네릭 타입, 매개변수화 타입, 타입 매개변수: **실체화 불가 타입(non-reifiable type)**
    - 실체화되지 않아서 런타임에는 컴파일타임보다 타입 전보를 적게 가지는 타입 

- `ArrayStoreException`
    - https://docs.oracle.com/javase/7/docs/api/java/lang/ArrayStoreException.html
    - int형 배열에 String 객체를 넣으려는 것과 같이 배열에 대해 부적절한 데이터 저장 시 발생
    - [예시 참고](http://blog.daum.net/_blog/BlogTypeView.do?blogid=07wRi&articleno=15862024&_bloghome_menu=recenttext)


## Reference
- https://docs.oracle.com/javase/7/docs/api/java/lang/ArrayStoreException.html
- [ArrayStoreException 설명](https://dololak.tistory.com/52)

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)




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