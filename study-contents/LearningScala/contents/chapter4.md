# CHAPTER 4. 함수

## :heavy_check_mark: 함수(function)
- 함수는 재사용 가능한 로직의 핵심 구성 요소다.

#### 함수형 프로그래밍에서 순수 함수의 특징
- 하나 또는 그 이상의 입력 매개변수를 가진다.
- 입력 매개변수만을 가지고 계산을 수행한다.
- 값을 반환한다.
- 동일 입력에 대해 항상 같은 값을 반환한다.
- 함수 외부의 어떤 데이터도 사용하거나 영향을 주지 않는다.
- 함수 외부 데이터에 영향받지 않는다.

#### 함수 정의 
- **입력값 없는 함수 정의** : `def <식별자> = <표현식>`
  - e.g. `def hi = "hi"`
- **반환 타입을 지정하여 함수 정의** :  `def <식별자>: <타입> = <표현식>`
  - e.g. `def hi: String = "hi"`
- **함수 정의** : `def <식별자>(<식별자>: <타입>[, ... ]): <타입> = <표현식>`
  - e.g. `def multiplier(x: Int, y: Int): Int = { x * y }`

## :heavy_check_mark: 프로시저(procedure)
- 프로시저는 반환값을 가지지 않는 함수다.
  - println() 호출과 같은 문장으로 끝나는 모든 함수 또한 프로시저다.
  - 문장으로 끝나는 명시적인 반환 타입이 없는 단순 함수의 경우, 스칼라 컴파일러는 이 함수의 반환 타입을 값이 없는 Unit 타입으로 추론할 것이다.
- 묵시적 반환 타입
  - e.g. `dev log(d: Double) = println(f"Got value $d%.2f")`
- 명시적 반환 타입
  - e.g. `dev log(d: Double): Unit = println(f"Got value $d%.2f")`

## :heavy_check_mark: 빈 괄호를 가지는 함수
- 입력 매개변수가 없는 함수를 정의하고 호출하는 다른 방법으로는 빈 괄호를 사용하는 것이다.
- **빈 괄호로 함수 정의**: `def <식별자>()[: <타입>] = <표현식>`
  - e.g. `dev hi(): String = "hi"`
  - 호출 방법: hi() 또는 hi
  - 반대로, 스칼라는 괄호 없이 정의된 함수를 괄호를 사용하여 호출하는 것을 허용하지 않는다.

## :heavy_check_mark: 표현식 블록을 이용한 함수 호출
- 단일 매개변수를 사용하여 함수를 호출할 때, 중괄호 안에 표현식 블록을 사용하여 매개변수를 전달할 수 있다.
  - 즉, 표현식 블록 안에서 연산 또는 다른 행위를 처리하고 그 블록의 반환값으로 함수를 호출할 수 있다.
- **표현식 블록을 이용하여 함수 호출하기**: `<함수 식별자> <표현식 블록>`
  - e.g. 함수에 계산될 값을 전달하는 경우
  ```scala
  def formatEuro(amt: Double) = f"$amt%.2f"
  formatEuro { 
    val rate = 1.32;
    0.234 + 0.7123 + rate * 5.32
  }
  ```

## :heavy_check_mark: 재귀 함수(recursive function)
- 재귀 함수는 자기 자신을 호출하는 함수
  - 각 함수의 호출이 함수 매개변수를 저장하기 위한 자신만의 스택을 가지고 있기 때문에 가능하다.
- 재귀 함수의 문제는 '스택 오버플로우(stack overflow)' 에러에 빠질 수 있다는 것이다. 
  - 재귀 함수를 너무 많이 호출하면 결국 할당된 스택 공간을 모두 소진할 수 있기 때문이다.
- 스칼라 컴파일러는 이를 예방하기 위해 **꼬리-재귀(tail-recursion)** 을 사용하여 일부 재귀 함수를 최적화한다.
  - 단, 마지막 문장이 재귀적 호출인 함수만이 최적화 대상이 된다.
  - 최적화될 함수를 표시하는 **함수 애너테이션** 
    - `@annotaion.tailrec` 를 함수 정의 앞 또는 그 전 줄에 추가한다.
  - e.g.
  ```scala
  @annotation.tailred
  def power(x: Int, n: Int, t: Int = 1): Int = {
    if (n < 1) t
    else power(x, n-1, x*t)
  }
  // power(2, 8) // Int = 256 
  ```
  
## :heavy_check_mark: 중첩 함수(nested function)
- e.g. 세 개의 정수를 취하여 가장 높은 값을 가진 정수를 반환하는 메서드
  ```scala
  def max(a: Int, b: Int, c: Int) = {
    def max(x: Int, y: Int) = if (x > y) x else y
    max(a, max(b, c))
  }
  ```
- 스칼라 함수는 함수 이름과 매개변수 타입 목록으로 구분된다.
- 하지만 이름과 매개변수 타입이 똑같더도 충돌이 일어나지는 않는데, 지역(중첩된) 함수가 외부 함수에 우선하기 때문이다.

## :heavy_check_mark: 이름으로 매개변수를 지정하여 함수 호출하기
- 스칼라에서는 이름으로 매개변수를 호출할 수 있어, 매개변수를 순서와 관계없이 지정하는 것이 가능하다.
- **이름으로 매개변수 지정하기**: `<함수명>(<매개변수> = <값>)`
  - e.g. 
  ```scala
  def greet(prefix: String, name: String) = s"$prefix $name"
  // 방법 1
  val greeting1 = greet("Ms", "Brown")
  // 방법 2
  val greeting2 = greet(name = "Brown", prefix = "Ms")
  ```

## :heavy_check_mark: 기본값을 갖는 매개변수
- 함수 오버로딩(function overloading)이란 
  - 동일한 이름을 갖지만 입력 매개변수가 다른 동일한 함수를 여러 버전으로 제공하는 것을 말한다. 
- 스칼라에서는 어떤 매개변수에도 기본값을 지정하고, 호출자가 해당 매개변수를 선택적으로 사용할 수 있도록 한다.
- **함수 매개변수로 기본값 지정하기**: `dev <식별자>(<식별자>: <타입> = <값>): <타입>`
  - e.g.
  ```scala
  def greet(prefix: String = "", name: String) = s"$prefix$name"
  val greeting1 = greet(name = "Paul")
  ```
  ```scala
  def greet(name = String, prefix: String = "") = s"$prefix$name"
  val greeting2 = greet("Ola")
  ```
  - 필수 매개변수가 먼저 오면 매개변수 이름을 생략하여 함수를 호출할 수 있다.
  - 필수 매개변수가 먼저 오는 것이 표현법상 더 좋다. 

## :heavy_check_mark: 가변 매개변수 
- 가변 인수(vararg)란 
  - 0개 이상의 여러 인수가 일치할 수 있는 함수 매개변수다.
  


#### :link: Reference
- []()


---

### :house: [LearningScala Home](https://github.com/WeareSoft/wwl/tree/master/study-contents/LearningScala)


### Reference
- []()