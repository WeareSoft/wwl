# [item 73. 추상화 수준에 맞는 예외를 던지라]
### 정리
#### 예외 번역
상위계층에서는 저수준의 예외를 잡아 자신의 추상화 수준에 맞는 예외로 바꿔 던져야 한다. 이를 예외 번역(exception translation)이라 한다.
``` java
try {
    ... // 저수준 추상화를 이용
} catch(LowerLevelException e) {
    // 추상화 수준에 맞게 번역한다.
    throw new HigherLevelException(...);
}
```



#### 예외 연쇄
저수준의 예외가 디버깅에 도움이 된다면 예외 연쇄(exception chaning)를 사용하는게 좋다. 그러면 별도의 접근자 메서드를 통해 필요하면 언제든 저수준 예외를 꺼내볼 수 있다.
``` java
try {
    ... // 저수준 추상화를 이용
} catch(LowerLevelException caution) {
    // 저수준 예외를 고수준 예외에 실어보단다.
    throw new HigherLevelException(caution);
}
```

- 실제 예
`Scanner`클래스의 `nextInt`메서드
    ``` java
    try {
        String s = next(integerPattern());
        if (matcher.group(SIMPLE_GROUP_INDEX) == null)
            s = processIntegerToken(s);
        return Integer.parseInt(s, radix);
    } catch (NumberFormatException nfe) {
        position = matcher.start(); // don't skip bad token
        throw new InputMismatchException(nfe.getMessage());
    }
    ```

#### 하지만...
무턱대고 예외를 전파하는 것 보다 예외 번역이 우수한 방법이나, 그렇다고 남용해서는 곤란하다. 가능하다면 저수준의 메서드가 반드시 성공하도록(아래 계층에서 예외가 발생하지 않도록) 하는 것이 최선이다. 때문에 상위 계층 메서드의 매개변수 값을 아래 계층에 건네기 전에 검사하는 방법도 사용할 수 있다.

#### 다른 방법은?
아래 계층에서 예외가 발생한다면 그 계층에서 조용히 처리하여 API 호출자에게 전파하지 않는 방법도 있다.

#### Reference
- [Exception Handling in Java](https://www.baeldung.com/java-exceptions)