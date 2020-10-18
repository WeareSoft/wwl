# Github
https://github.com/myeonginwoo/t-academy

# FP - Functional Programming
- Immutable DataStructure - 불변 객체
- Pure Function - 순수함수, 참조 투명성
- Lazy Evauation 

## Immutable DataStructure
- FP에서는 불변 객체로 프로그래밍 하는 것을 추구
- 핵심 철학
- 원본은 변화가 없음 - 그러면 사이드 이펙트가 없을 것

```
val immutableInt: Int = 10
immutableInt = 5 // Error


val immutableInt: String = "FP"
immutableInt = "Kotlin" // Error
```

## Pure Function
함수는 주어진 입력으로 계산하는 것 이외에 프로그램의 실행에 영향을 미치지 않아야 하며, 이를 부수 효과(side effect)가 없어야 한다.
```
func plusNumber(num: Int) -> (Int -> Int) {
  return { x in
    return x + num
  }
}

let addFive = plusNumber(5)
addFive(1)	// 5
addFive(10)	// 15
```

### Lazy evalution
- 함수 호출이라는 말도 사용하지만 `평가`라는 말도 사용
- 선언할 때는 호출되지 않음. 시용할때 비로소 호출?

예1)
```
val lazyValue:String by lazy{
    println("시간이 오래걸림")
    "hello"
}

fun main(atgs: Array<String>) {
    println(lazyValue)
    println(lazyValue)
}
```
이런 기법을 메모제이션Momoztion이라함

```
val infiniteValut = gernerateSequence(0) {it + 1}
infiniteValue
    .take(5)
    .for(Each{print("$it ")}
```

- () -> Unit
    - ():input
    - Unit: void // 실재로는 void하고 다름

```
val lazyValue2: () ->Unit = {
    println("FP")
}

lazyValue2
lazyValue2
lazyValue2
lazyValue2() // FP
```
# First-class citizen
## 1. 변수나 데이타에 할당 할 수 있어야 한다.
```
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val a = test
    }

    val test: () -> Unit = { println("kotlin") }
}
```
```
public class java {

    public static void test(){
        System.out.println("java");
    }

    public static void main(String[] args) {
        System.out.println("java");
//        Object a = test;
    }
}
```
kotlin은 a 에 type이 () -> Unit 인 test 함수 할당이 가능하지만, Java는 불가능.

## 2. 객체의 인자로 넘길 수 있어야 한다.
```
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        function(test)
    }

    fun function(f: () -> Unit) {
       f.invoke()
    }

    val test: () -> Unit = { println("kotlin") }
}
```
kotlin 은 function 함수의 인자로 함수타입을 전달 할 수 있습니다. 하지만 Java에서는 불가능.

## 3. 객체의 리턴값으로 리턴 할수 있어야 한다.
```
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        function()
    }

    fun function(): () -> Unit {
        return { println("kotlin") }
    }

}
```
function함수는 { println(“kotlin”) }, 즉 함수타입을 반환 합니다.


# Functional Data Structure

- 계속 새로운 객체를 생성하면 비효율적이지 않나?
    - -> Data Share

## Data Share
- 미번 새로운객체를 생성해서 반환하는게 아님

# Recursion
- Tip
    - `어떻게` 보다 `무엇`에 집중
    - 종료 조건부터 생각하자
    - 재귀를 반복 할수록 종료 조건으로 수렴

- Readability+, Performance-
    - (?): 재귀가 가독성이 좋다?

## 꼬리재귀
```
tailrec fun factorial3(num: Int, acc: Int = 1): Int = when (num) {
    1->acc
    else -> factorial3(num - 1, acc * num)
}
```
- 생긴건 재귀지만 내부 동작은 for:each와 
    - 가독성+, 퍼포먼스+, !SOP
- tailrec 키워드가 있으면, 스택 쌓는걸 컴파일러가 내부적을 최적화를 시켜줌

## Function Type
## Higher-order funtion
- Pass a function as an Argument
- Return a function

## CallByName
## Partial Applied Function

### Curring
- x의 값은 알고있지만 y, z의 값은 런타임에 각각 전달받는다면?

## Collection

#Algebraic Data Type
## ProductType
- Runtime error
## SumType
- Compile error
- 유용

# Partial Function (!= Partial applied function)
- 예외처리 같은 경우 대두분 예외함수
    - 안좋다고들 얘기하는데 현실적으로 어쩔 수 없는 부분
## Maybe
- 대수적 타입
- 함수형 프로그래밍에서 예외상황을 해결하기 위해...
- optional in java
- 처리할 수 있는 영역은 just로 감싸고 아닌 경우는 None을 반환
## Either
- Maybe랑 비슷. Right, Left로 나뉘어있음
- Right, Left가 값을 지닐 수 있음(None은 아님)

- 게으른 평가를 적용할 수 있는 이유는  순수함수와 불변객체

---
## 용어
- Cons와 NIL