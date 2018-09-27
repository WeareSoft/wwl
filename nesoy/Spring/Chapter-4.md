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

- 런타임 예외를 일반화해서 사용하는 방법은 여러모로 장점이 많다. `단, 런타임 예외로 만들었기에 사용에 더 주의하자.` 예외상황을 충분히 고려하지 않을 수도 있기 때문이다.
- 런타임 예외를 사용하는 경우에는 API 문서나 Reference 문서 등을 통해, 메소드를 사용할 때 발생할 수 있는 예외의 종류와 원인, 활용 방법을 자세히 설명해두자.

### 애플리케이션 예외
- 첫번째 방법
    - 잔고가 부족한 경우라면 0 또는 -1과 같은 특별한 값을 리턴한다.
    - 표준은 없다. 사전에 상수로 정의해둔 표준 코드를 만들어서 개발간의 의사소통하자.
    - if 범벅.
- 두번째 방법
    - 비즈니스적인 의미를 띤 예외를 던지도록 만드는 것이다.
    - if 범벅은 피할 수 있다.
    - 상대적으로 안전하다.

### 정리하면
- 애플리케이션 Level에서 해결하지 못할 Exception이면 빠르게 개발자에게 알림을 전달해주는 방법.
- 필요도 없는 기계적인 throws 선언이 등장하도록 방치하지 말고 가능한 빨리 unchecked / runtimeExcepion으로 전환해야 한다.


## 4.2 예외 전환
- 런타임 예외로 포장해서 굳이 필요하지 않은 catch/throws를 줄여주는 것.
- 다른 하나는 Low Level의 예외를 좀 더 의미 있고 추상화된 예외로 바꿔서 던져주는 것.
- `DataAccessException`은 `SQLException`에 담긴 다루기 힘든 상세한 예외정보를 의미 있고 일광성 있는 예외로 전환해서 추상화해주려는 용도로 쓰이기도 한다.
- DB에 독립적인 유여한 코드를 작성하는 건... 현실상 어렵다.

### JDBC의 한계
- DB의 기능을 추상화하여 유여한 코드를 작성할 수 있게 도와주는 기능
    - 특정 DB에서만 동작하는 기능
    - SQL 성능 최적화 기법
    - DB마다 다른 Error Code

- 변경되는 부분을 주입하자!
    - DB마다 다른 Error Code를 XML로 작성하여 주입하는 방식

### DAO인터페이스와 구현의 분리
- 데이터 Access Logic을 담은 코드를 성격이 다른 코드에서 분리해놓기 위해서다.

```java
public interface UserDao{
    public void add(User user); // 이상적인 코드
}
```

- DAO에서 사용하는 데이터 엑세스 기술의 API가 예외를 던지기 때문.
```java
public interface UserDao{
    public void add(User user) throws SQLException;
}
```

- 이렇게 정의한 인터페이스는 JDBC가 아닌 데이터 엑세스 기술로 DAO 구현으로 전환할 수 없다.

```java
public void add(User user) throws PersistentExeception; // JPA
public void add(User user) throws HibernateException; // Hibernate
```

- JPA, Hibernate, JDO들은 checkedException 대신 Runtime Exception으로 작성했기에 따로 선언하지 않아도 괜찮다.
- Optimistic locking
    - 같은 정보를 두 명 이상의 사용자가 동시에 조회하고 순차적으로 업데이트를 할 때 뒤늦게 업데이트한 것이 먼저 업데이트한 것을 덮어쓰지 않도록 막아주는 데 쓸 수 있는 편리한 기능
    - 사용자에게 적절한 안내 메시지를 보여주고, 다시 시도할 수 있도록 해줘야 한다.

- DataAccessException을 사용하는 이유는?
    - JDBC와 JPA등등 모든 것을 예외 추상화를 적용하여 데이터 액세스 기술과 구현 방법에 독립적인 이상적인 DAO를 만들 수 있다.


![No Image](/nesoy/Images/Spring/13.png)

- UserDao 인터페이스와 구현의 분리

![No Image](/nesoy/Images/Spring/14.png)


### DataAccessException 활용 시 주의사항
- 기술에 상관없이 어느 정도 추상화된 공통 예외로 변환해주기는 하지만 근본적인 한계 때문에 완벽하다고 기대할 수는 없다.
- 사용에 주의를 기울여야 한다. 미리 학습 테스트를 만들어서 실제로 전환되는 예외의 종류를 확인해둘 필요가 있다.


## 4.3 정리
- 예외를 잡아서 아무런 조치를 취하지 않거나 의미 없는 throws 선언을 남발하는 것은 위험하다.
- 복구하거나 의도적으로 전달하거나 적절한 예외로 전환되어야 한다.
- 복구할 수 없는 예외는 RuntimeException으로 전환하자.

