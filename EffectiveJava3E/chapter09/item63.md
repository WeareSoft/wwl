# [item 63] 문자열 연결은 느리니 주의하라

### 사전 조사
- [tech-interview 내용](https://github.com/WeareSoft/tech-interview/blob/master/contents/java.md#string-stringbuilder-stringbuffer)

```java
// 리터럴을 이용한 방식
String a = "aa"; // string constant pool 영역에 존재
// new 연산자를 이용
String b = new String("bb"); // Heap 영역에 존재
```
```java
a = a + b; // "aabb" - 새로운 String 인스턴스 생성 (Heap 영역)
```

- String 문자열 연산 
    - 장점: immutable 하기 때문에 여러 스레드가 데이터를 공유하더라도 동기화를 신경쓸 필요가 없이 안정성이 유지된다.
    - 단점: 연산이 수행될 때마다 두 문자열을 모두 복사하여 새로운 메모리에 할당하기 때문에 성능 저하가 나타난다.
    - [https://www.slipp.net/questions/271](https://www.slipp.net/questions/271)
        - JDK 1.5 이후부터는 +를 활용해도 성능상에 큰 이슈는 없다.
            - 컴파일 단계에서 StringBuilder로 컴파일 되도록 변경되었기 때문
        - 하지만 실무에서 런타임에 문자열이 조합되는 경우가 자주 발생하기 때문에 직접 성능 차이를 확인해보는 것이 좋다.
            - 런타임의 경우에는 + 연산자의 연산속도가 현저하게 느려지는 것을 확인한 경우가 많다.

- StringBuilder와 StringBuffer 동기화 차이 
    ```java
    public final class StringBuffer
        public synchronized StringBuffer append(String str) {
            super.append(str);
            return this;
        }
        public synchronized StringBuffer append(boolean b) {
            super.append(b);
            return this;
        }  
        [...]
    }
    ```
    ```java
    public final class StringBuilder {
        public StringBuilder append(String str) {
            super.append(str);
            return this;
        }
        public StringBuilder append(boolean b) {
            super.append(b);
            return this;
        }    	
        [...]
    }
    ```

- StringBuilder와 StringBuffer의 append() 메서드의 동작 과정 
    - 과정 
        1. value에 사용되지 않고 남아있는 공간에 새로운 문자열이 들어갈 정도의 크기가 있다면 그대로 삽입한다.
        2.  그렇지 않다면 value 배열의 크기를 두배로 증가시키면서 기존의 문자열을 복사하고 새로운 문자열을 삽입한다.
    - 예시 
        - `StringBuilder sb = new StringBuilder();`
            - StringBuilder 를 생성할 때 capacity를 지정하지 않으면 기본 16으로 설정한다.
            - **초기:** value의 크기는 16, value의 값은 empty, count는 0
        - `sb.append("first string");`
            - "first string" 이라는 문자열의 크기는 12 이다.
            - value는 비어있고 남은 수용공간인 16보다 크기가 작으므로 아무런 문자열의 복사 없이 바로 추가된다.
            - **갱신:** value의 크기는 16, value의 값은 "first string", count는 12
        - `sb.append("+second string");`
            - "second string" 의 문자열의 크기는 14 이다. 
            - value에 남아 있는 공간의 크기가 4이기 때문에 해당 문자열을 추가할 수 없으므로 배열의 크기를 증가시킨다.
            - value의 크기를 32(2배)로 늘리고 기존의 문자열을 복사한다.
            - **갱신:** value의 크기는 32, value의 값은 "first string+second string", count는 26

- **Q.** StringBuilder를 직접 구현하려면?
    - **A.** String 값을 리스트 변수로 가지고 있고 마지막에 하나의 String에 리스트 값을 붙인다.

## Reference
- https://github.com/WeareSoft/tech-interview/blob/master/contents/java.md#string-stringbuilder-stringbuffer
- https://www.slipp.net/questions/271
- https://ijbgo.tistory.com/2
- https://cjh5414.github.io/why-StringBuffer-and-StringBuilder-are-better-than-String/

---

### 스터디 요약
-

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

