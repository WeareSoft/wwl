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