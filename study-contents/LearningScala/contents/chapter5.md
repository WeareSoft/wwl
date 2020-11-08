# CHAPTER 5. 일급 함수 

## :heavy_check_mark: 일급 함수(first-class function)
- 함수형 프로그래밍의 핵심 가치 중 하나는 **함수는 일급(first-class) 객체** 이어야 한다는 것이다.
- 함수가 일급 객체라는 것?
  - 함수가 선언되고 호출되는 것 외에 다른 데이터 타입처럼 언어의 모든 부분에 사용될 수 있다는 것을 의미한다.
  - 식별자에 할당되지 않고도 리터럴 형태로 생성될 수 있으며, 
  - 값 변수 또는 데이터 구조처럼 컨테이너에 저장될 수 있으며, 
  - 다른 함수에 매개변수로 사용되거나 다른 함수의 반환값으로 사용될 수 있다. 
- **고차 함수(higher-order function)**
  - 다른 함수를 매개변수로 받아들이거나 반환값으로 함수를 사용하는 함수 
  - e.g. `map()`
    - 함수 매개변수를 취하고 이를 사용하여 하나 또는 그 이상의 항목을 새로운 값이나 타입으로 전환한다. 
  - e.g. `reduce()`
    - 함수 매개변수를 취하고 이를 사용하여 여러 항목을 가지는 컬렉션을 단일 항목으로 축소한다.

### 함수 타입과 값 
- **([<타입>, ...]) => <타입>**
  - e.g. `def double(x: Int): Int = x * 2`
    - 합수 타입: Int => Int 

#### 함수로 할당된 함숫값을 정의하는 방법 
1. 함수를 생성하고 이를 함숫값에 할당할 수 있다.
  - **([<타입>, ...]) => <타입>** 사용 
    ```scala
    val myDouble: (Int) => Int = double
    myDouble(5) // 10
    ```
    ```scala
    def max(a: Int, b: Int) = if (a < b) a else b

    val maximize: (Int, Int) => Int = max
    maximize(50, 30) // 50
    ```
    ```scala
    def logStart() = "=" * 10 + " Starting Now\n"

    val start: () => String = logStart
    println( start() ) // ========== Starting Now
    ```
  - myDouble 값의 명시적 타입은 이를 함수 호출이 아닌 함숫값으로 식별하는 데 필요하다.
2. 함수로 할당된 함숫값을 정의하는 다른 방법
  - 와일드카드 연산자 **'_'** 사용
  - **val <식별자> = <함수명> _** 
    ```scala
    val myDouble = double _
    myDouble(5) // 10
    ```
  - myDouble의 명시적 함수 타입은 함수 호출과 구별하기 위해 필요하지 않다.
  - 언더스코어(_)는 미래의 함수 호출에 대한 자리표시자 역할을 하며, myDouble에 저장할 수 있는 함숫값을 반환한다.

### 고차 함수(higher-order function)
- 입력 매개변수나 반환값으로 함수 타입의 값을 가지는 함수
- e.g. 기존 함수를 고차 함수에 매개변수로 전달하는 방법
  - String에서 동작하지만 입력 String이 널(null)이 아닌 경우에만 동작하는 다른 함수를 호출한다.
  ```scala
  def safeStringOp(s: String, f: String => String) = {
    if (s != null) f(s) else s
  }
  def reverser(s: String) = s.reverse // String => String 함수 타입
  ```
  ```scala
  safeStringOp(null, reverser) // null 
  safeStringOp("Ready", reverser) // ydaeR
  ``` 

### 함수 리터럴(function literal)
- 실제 동작하지만 이름이 없는 함수 
  - 함수 본문의 모든 로직이 인라인으로 기술된다. 
  - 함수 리터럴은 근본적으로 매개변수화된 표현식이다. 
  - 함수 리터럴의 다른 이름 
    - 익명 함수(Anonymous function)
      - 함수 리터럴의 스칼라 언어의 공식적인 이름 
    - 람다 표현식(Lambda expression) 
      - 자바8, C# 
    - function0, function1, ... 
      - 입력 인수 개수를 기반으로하는 스칼라 컴퍼일러 용어 
- 함수 리터럴은 함숫값과 변수에 저장되거나 고차 함수 호출의 부분으로 정의될 수 있다.
  - 즉, 함수 타입을 받는 모든 곳에 함수 리터럴을 표현할 수 있다.
  1. 함숫값과 변수에 저장 
    ```scala
    val greeter = (name: String) => s"Hello, $name"
    val hi = greeter("World") // Hello, World 
    ```
  2. 고차 함수 호출 내부에 정의
    ```scala
    def safeStringOp(s: String, f: String => String) = {
      if (s != null) f(s) else s
    }
    ```
    ```scala
    safeStringOp(null, (s: String) => s.reverse) // null
    safeStringOp("Ready", (s: String) => s.reverse) // ydaeR
    
    // 타입 추론
    safeStringOp(null, s  => s.reverse) // null
    safeStringOp("Ready", s => s.reverse) // ydaeR
    ```
- **([<식별자>: <타입>, ...]) => <표현식>**
  - 함수 리터럴 VS 함수 할당
    - 함수 할당
      ```scala
      def max(a: Int, b: Int) = if (a > b) a else b
      val maximize: (Int, Int) => Int = max // 할당 
      ```
    - 함수 리터럴
      ```scala
      val maximize = (a: Int, b: Int) => if (a > b) a else b
      ```

### 자리표시자 구문(placeholder syntax)
- 함수 리터럴의 축약형으로, 지정된 매개변수를 와일드카드 연산자(_)로 대체한 형태를 가진다.
- **구문 사용 조건**
  - 함수의 명시적 타입이 리터럴 외부에 지정되어 있고, 매개변수가 한 번 이상 사용되지 않는 경우에 사용한다.


#### :link: Reference
- []() 

---

### :house: [LearningScala Home](https://github.com/WeareSoft/wwl/tree/master/study-contents/LearningScala)


### Reference
- []()