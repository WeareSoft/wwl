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

### 예외 복구
- 예외상황을 파악하고 문제를 해결해서 정상 상태로 돌려놓는 것.
- 네트워크가 불안해서 가끔 서버에 접속이 잘 안되는 열악한 환경에 있는 시스템

```java
int maxretry = MAX_RETRY;
while(maxretry --> 0){
    try{
        // process
        return ;
    }catch(SomeException e){
        // 로그 출력. 정해진 시간만큼 대기
    }finally{
        // return Resource
    }
}
throw new RetryFailedException(); // 최대 재시도 횟수를 넘기면 직접 예외 발생
```

### 예외처리 회피
- 자신이 담당하지 않고 자신을 호출한 쪽으로 던져버리는 것이다.
- 콜백 오브젝트의 메소드는 모두 throws SQLException이 붙어 있다.
- 콜백 오브젝트의 역할이 아니라고 보기 때문.
- 예외를 회피하는 것은 예외를 복구하는 것처럼 의도가 분명해야 한다.

### 예외 전환
- 내부에서 발생한 예외를 그대로 던지는 것이 그 예외상황에 대한 적절한 의미를 부여해주지 못하는 경우에 의미를 분명하게 해줄 수 있는 예외로 바꿔주기 위해서다.
- 아이디 중복 체크 과정에서 SQLException이 발생하면 SQLException보다는 DuplicateUserIdException이 더 적절한 예외라고 할 수 있다.
- 예외를 처리하기 쉽고 단순하게 만들기 위해 wrap하는 것이다.
- 복구하지 못할 예외라면 어플리케이션 코드에서는 런타임 예외로 포장해서 던저버리고, 예외처리 서비스 등을 이용해 자세한 로그를 남기고, 관리자에게는 메일 등으로 통보해주고, 사용자에게는 친절한 안내 메시지를 보여주는 식으로 처리하는게 바람직하다.