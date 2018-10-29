# 서비스 추상화

## 5.1 사용자 레벨 관리 기능 추가
- 빠르게 실행 가능한 포괄적인 테스트를 만들어두면 이렇게 기능의 추가나 수정이 일어날 때 그 위력을 발휘한다.
- 테스트를 만들면 작성한 코드에 확신을 가질 수 있고 마음이 편해지는 건 사실이지만, 귀찮다고 대충 작성한 테스트는 오히려 찾기 힘든 버그와 사고의 원인이 될 수도 있다.


- DAO는 데이터를 어떻게 가져오고 조작할지를 다루는 곳이지 비즈니스 로직을 두는 곳이 아니다.


### 초기화하는 로직은 어디에 담는 것이 좋을까?
- UserDaoJdbc의 add() 메소드는 적합하지 않아 보인다.
    - User 오브젝트를 DB에 정보를 넣고 읽는 방법에만 관심을 가져야지 비즈니스적인 의미를 지닌 정보를 설정하는 책임을 지는 것은 바람직하지 않다.
- User 클래스에서 아예 Level 필드를 Level.Basic으로 초기화하는 것은 어떨까?
    - 나쁘지 않지만 가입할 때를 제외하면 무의미한 정보이다.
- add()를 호출하기 이전에 UserService에도 add()를 만들어두고 사용자가 등록될 때 적용할 만한 비즈니스 로직을 담당하게 하면 될 것이다.


### 코드 개선
- 코드에 중복된 부분은 없는가?
- 코드가 무엇을 하는 것인지 이해하기 불편하지 않은가?
- 코드가 자신이 있어야 할 자리에 있는가?
- 앞으로 변경이 일어난다면 어떤 것이 일을 수 있고, 그 변화에 쉽게 대응할 수 있게 작성되어 있는가?

> 먾은 if조건을 switch로 Refactoring

![No Image](/nesoy/Images/Spring/15.png)

- 객체지향적인 코드 : 데이터를 가져와서 작업하는 대신 데이터를 가진 객체에게 작업을 요청하자.
- 인터페이스를 만들어 DI로 주입받는 형태로 만들자.



```java
private void upgradeLevel(User user){
    if(user.getLevel() == Level.BASIC) user.setLevel(Level.SILVER);
    else if (user.getLevel() == Level.SILVER) user.setLevel(Level.GOLD);
    userDao.update(user);
}
```

- nextLevel()의 역할은 Level이 가지고 있어야 한다.

```java
Level nextLevel = this.level.nextLevel();
if( nextLevel == null){
    throw new IllegalStateException(this.level + " Cannot upgrade");
} else {
    this.level = nextLevel;
}
```

- 각 오브젝트와 메소드가 각각 자기 몫의 책임을 맡아 일을 하는 구조로 만들졌음을 알 수 있다.
- 객체지향 프로그래밍의 가장 기본이 되는 원리는 오브젝트에게 데이터를 요구하지 말고 작업을 요청하라는 것.
- 숫자도 중복의 해악이다.


## 5.2 트랜잭션 서비스 추상화
> 정기 사용자 레벨 관리 작업을 수행하는 도중에 네트워크가 끊기거나 서버에 장애가 생겨서 작업을 완료할 수 없다면? 그때까지 변경된 사용자의 레벨은 그대로 둘까요? 아니면 모두 초기 상태로 되돌려 놓아야 할까요?

- 데이터베이스를 넣다가 중간에 에러를 내뱉는 상황을 연출하여 테스트
    - 기존의 UserService를 상속받아 TestUserService를 작성.
    - 작업이 중단되었을 경우 어떻게 Fail-Over할지에 대한 test-case

### 생각과 느낌정리
- 현재 나는 비즈니스 로직에 집중해서 테스트를 작성했다. 데이터베이스 조회가 실패한 미래는 그리지 못했다.
- 상속을 하여 에러를 내뱉는 상황을 연출하여 테스트를 진행할 수도 있구나.. 이런 테스트가 많아지면 많아질수록 더욱 견고해지고 튼튼해지는 서비스가 나올것 같다. :D

- 하나의 SQL은 트랜잭션으로 보장받지만 Application Level에서 여러개 SQL을 실행했을 때 하나의 트랜잭션으로 보고 싶은 needs
    - 중간의 SQL이 실패할 경우 Transaction Rollback을 진행해야 한다.
    - 모든 SQL이 완료된 경우 Transaction Commit을 진행해야 한다.


![No Image](/nesoy/Images/Spring/16.png)

- DAO 메소드에서 DB 커넥션을 매번 만들기 때문에 어쩔 수 없이 나타나는 결과다.
- UserService 내에서 진행되는 여러 가지 작업을 하나의 트랜잭션으로 묶는 일이 불가능해진다.

### 그렇다면 DAO 메소드안으로 UserService 코드를 옮기면 되지 않을까?
- 하나의 트랜잭션으로 여려 명의 사용자에 대한 정보를 업데이트를 할 수 있다.
- 하지만 이 방식은 비즈니스 로직과 데이터 로직을 한데 묶어버리는 `한심한 결과.`
- UserService와 UserDAO를 그대로 둔 채로 트랜잭션을 적용하려면 UserService 쪽으로 가져와야 한다.
    - 데이터 엑세스 코드는 최대한 그대로 남겨둔 채로, UserService에는 트랜잭션 시작과 종료를 담당하는 최소한의 코드만 가져오게 만들면 어느정도 해결할 수 있다.

![No Image](/nesoy/Images/Spring/17.png)

### 이렇게 구성하면 트랜잭션은 해결되지만 결국엔..?
- DB 커넥션을 비롯한 리소스의 깔금한 처리를 가능하게 했던 JdbcTemplate를 사용하지 못함.
- DAO의 메소드와 비즈니스 로직을 담고 있는 UserService의 메소드에 Connection 파라미터가 추가돼야 한다는 점.
- 싱글톤으로 인한 동시성 문제
- Connection Parameter가 UserDao인터페이스에 추가되면 더이상 Data Access 기술에 독립적일 수가 없다는 점.
- 테스트하기 어려운 코드로 바뀌게 된다.



### 트랜잭션 동기화(Transaction synchronization)
- UserService에서 트랜잭션을 시작하기 위해 만든 Connection 오브젝트를 특별한 저장소에 보관해두고 이후에 호출되는 DAO 메소드에서는 저장된 Connection을 가져다가 사용하게 하는 것.

![No Image](/nesoy/Images/Spring/18.png)

- 트랜잭션 동기화 저장소는 작업 스레드 마다 독립적으로 Connection Object를 저장하고 관리하기 때문에 다중 사용자를 처리하는 서버의 멀티스레드 환경에서도 충돌이 날 염려는 없다.
```java
TransactionSynchronizationManager.initSynchronization(); // 트랜잭션 동기화 관리자를 이용해 동기화 작업을 초기화한다.

Connection c = DataSourceUtils.getConnection(dataSource);
c.setAutoCommit(false);

c.rollback(); 예외가 발생하면 롤백한다.

DataSourceUtils.releaseConnection(c, dataSource);
TranactionSynchronizationManager.unbindResource(this.dataSource); // 동기화 작업 종료
TranactionSynchronizationManager.clearSynchronization();    // 동기화 정리
```


### 이런 새로운 문제가 또...
- 기존은 하나의 DB에 데이터를 저장하는 방식이라서 하나의 DB가 바뀌어도 UserDAO, UserService 코드는 바뀌지 않았다.
- 그러나 이번엔 `하나의 트랜잭션에서 하나의 DB가 아닌 여러 개의 DB에 데이터`를 넣어야하는 요구사항이다.
    - 즉 Global Transaction 방식을 사용해야 한다.
    - JDBC에서 글로벌 트랜잭션 매니저를 지원하기 위해 JTA를 제공하고 있다.
- 분산 트랜잭션, 글로벌 트랜잭션을 담당하는 트랜잭션 매니저와 트랜잭션 서비스가 존재한다.

### 문제를 넘으니 문제가 또...
- 이번엔 JDBC나 JTA와는 다르게 Hibernate를 이용한 트랜잭션 관리 코드는 다르다.
- Connection을 직접 사용하지 않고 Session이라는 것을 사용하고 독자적인 트랜잭션 관리 API를 사용.

- 열심히 Data Access 기술을 모두 추상화하고 DI를 적용해서 의존도를 낮췄지만 트랜잭션 때문에 그동안의 수고가 허사가 되고 말았다.
- SQL을 이용하는 방식이라는 공통점이 있다.
    - 이 공통점을 추상화한 것이 JDBC
- 그렇다면 트랜잭션 처리 코드에도 추상화를 도입해볼 수 있지 않을까?
    - JDBC, JTA, Hibernate, JPA, JDO등을 추상화해보자.

![No Image](/nesoy/Images/Spring/19.png)

```java
PlatformTransactionManager transactionManager = new DataSourceTranactionManger(dataSource); // 추상화된 tranaction Manager
```

- 스프링 빈으로 등록하고 DI 방식으로 사용하게 해야 한다.
    - 싱글톤으로 만들어져도 괜찮은가?
    - 멀티 스레드 환경에서 안전하지 않은 클래스를 Bean으로 무작정 등록하면 심각한 문제 발생
    - 하지만 PlatformTransactionManager은 Thread-Safe하기 때문에 싱글톤으로 사용이 가능하다.


## 5.3 서비스 추상화와 단일 책임 원칙

![No Image](/nesoy/Images/Spring/20.png)

> 마치 하나의 블록이 조합되어 있는 모습이다.

- 단일 책임 원칙(SRP)은 작을 때는 특별해 보이지 않는다.
    - 클래스가 수백 개가 되고 클래스가 그만큼 많다고 생각해보자.

### 하나의 서비스에서 사용하는 DAO가 수백개라면?
- 의존관계는 매우 복잡해진다.
- DAO를 하나 수정할 경우 그에 의존하고 있는 서비스 클래스도 같이 수정해야 한다.
- 자연스럽게 모든 테스트 케이스도 수정해야하는 수고가 필요하다.

- 객체지향 설계 원칙을 잘 지켜서 만든 코드는 테스트하기도 편하다.
- 좋은 코드를 설계하고 만들려면 꾸준한 노력이 필요하다.


## 5.4 메일 서비스 추상화
- 메일 서버가 준비되어 있지 않았다면 다음과 같은 예외가 발생하면서 테스트 실패
    - 메일 서버가 운영하지 않는 Case
- 그렇다면 실제로 DB 대신하여 테스트 DB를 사용하는 방법은 어떨까?
    - 운영 중인 메일 서버에 부하를 주지 않는다는 면에서는 분명히 나은 방법

### 그렇다면 메일 발송 기능을 검증할 필요가 있을까?
- 메일 발송 테스트는 메일 발송용 서버에 별문제 없이 전달했음을 확인할 뿐
    - 실 사용자가 잘 받았는지까지는 확인하지 못한다.
- 메일 테스트를 한다고 매번 메일 수신 여부까지 일일이 확인할 필요는 없고, 테스트 가능한 메일 서버까지만 잘 전송되는지 확인하면 된다.
