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