# [item 53] 가변인수는 신중히 사용하라 
### 사전 조사 
#### 가변인수 사용 예
- 인수가 1개 이상이어야 할때
  - 디폴트 인수를 하나 설정한다.
    ``` java
    static int (int firstarg, int... reaminingarg) {
        int min = firstarg;
        for (int arg : remainingargs) {
            if (arg < min) min = arg
            return min;
        }
    }
    ```
    

- 인수 개수가 정해져 있지 않을 때
  - 성능에 민감한 상황이라면 가변 인수는 걸림돌이 될 수 있다. 많이 호출되는 메서드들은 따로 만들고 나머지 변수를 처리할 수 있는 메서드를 만들자
    ``` java
    public void foo() {}
    public void foo(int a1) {}
    public void foo(int a1, int a2) {}
    public void foo(int a1, int a2, int a3) {}
    public void foo(int a1, int a2, int 3, int... rest) {} // 호출 빈도 적음
    ```
---

#### varargs(자바 5.0)
- 가변인수가 없던 시절
    - 컬렉션이나 배열을 이용해서 가변인수를 대체하고 있었다.
- 컬렉션 사용 예시 
    ```java
    public class varargsmain {
        public static void display(vector v) {
            for (object s : v) {
                system.out.println("컬렉션형태:" + s);
            }
        }
    }
    ```
    ```java
    vector vec = new vector ();
    vec.add("hello");
    vec.add("world");
    vec.add("korea");
    varargsmain.display(vec);
    ```

- 가변인수 사용 예시
    ```java
    public class varargsmain {
        public static void display(string... strs) {
        // 컴파일러 변환 후 : public static void display(String as[])
            for (string s : strs) {
                system.out.println("가변인수형태:" + s);
            }
        }
    }
    ```
    ```java
    VarArgsMain.display("Hello" , "World", "Korea");
    // 컴파일러 변환 후 : VarArgsMain.display(new String[] {"Hello", "World", "Korea"});
    ```

- [ref](https://gyrfalcon.tistory.com/entry/java-varargs)

#### 가변인수 메서드 동작과정
1. 인수의 개수와 길이가 같은 배열 생성
2. 인수들을 이 배열에 저장하여 가변인수 메서드에 전달

- 성능에 민감한 상황이라면 걸림돌이 될 수 있다.
    - 가변인수 메서드는 호출될 때마다 배열을 새로 하나 할당하고 초기화한다.

#### 가변인수의 유연성이 필요할 때 사용하는 패턴 (ex. `enumset`)
```java
public static <e extends enum<e>> enumset<e> of(e e) {
    enumset<e> result = noneof(e.getdeclaringclass());
    result.add(e);
    return result;
}
```
```java
public static <e extends enum<e>> enumset<e> of(e e1, e e2, e e3, e e4, e e5) {
    enumset<e> result = noneof(e1.getdeclaringclass());
    result.add(e1);
    result.add(e2);
    result.add(e3);
    result.add(e4);
    result.add(e5);
    return result;
}
```
```java
@safevarargs
public static <e extends enum<e>> enumset<e> of(e first, e... rest) {
    enumset<e> result = noneof(first.getdeclaringclass());
    result.add(first);
    for (e e : rest)
        result.add(e);
    return result;
}
```
---


### 스터디 요약 
- 

---

> :leftwards_arrow_with_hook:[effectivejava3e](/effectivejava3e/readme.md)

