# 예외

## 4.1 사라진 SQLException
- 예외를 잡고는 아무것도 하지 않는다. 정상적인 상황인 것처럼 다음 라인으로 넘어가겠다는 분명한 의도가 있는 게 아니라면 연습 중에도 절대 만들어서는 안되는 코드.
- 무시하고 계속 진행해버리기 때문에 메모리나 리소스가 소진되거나, 예상치 못한 다른 문제를 일으킬 것이다.

```java

try{

} catch(SQLException e){
    System.out.println(e);
    e.printStackTrace();
}
```

- 운영서버에 콘솔 로그는 매우 심각한 폭탄이다.
- 예외를 처리할 때 반드시 지켜야 할 핵심 원칙은 한 가지다.
    - 모든 예외는 적절하게 복구되든지 아니면 작업을 중단시키고 운영자, 개발자에게 분명하게 통보돼야 한다.

- 무의미하고 무책임한 throws
    - 메소드 선언에 throws Exception을 기계적으로 붙이는 개발자.
    - 적절한 위치와 시간에 처리를 하여 복구될 수 있는 예외상황도 제대로 다루지 못한다.

- Error
    - 시스템에 뭔가 비정상적인 상황이 발생했을 경우
    - JVM에서 발생시키는 것이고 어플리케이션 코드에서 잡으려고 하면 안된다.
    - OutOfMemoryError, ThreadDeath와 같은 에러

- Exception
    - 개발자들이 만든 애플리케이션 코드의 작업 중에 에외상황이 발생했을 경우
    - Checked Exception
        - Compile Error
        - SQLException
        - IOException
        - 초기 설계자들은 체크 예외를 발생 가능한 예외에 모두 적용하려고 했던 것 같다.
    - unChecked Exception
        - RunTime Exception