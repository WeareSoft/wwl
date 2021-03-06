# AOP
- AOP를 바르게 이용하려면?
    - 등장배경과 스프링이 그것을 도입한 이유, 그 적용을 통해 얻을 수 있는 장점이 무엇인지에 대한 충분한 이해가 필요하다.
    - 그래야지만 AOP의 가치를 이해하고 효과적으로 사용할 방법을 찾을 수 있다.

## 6.1 트랜잭션 코드의 분리
- 트랜잭션 경계설정 코드와 비즈니스 코드가 한곳에 있으니 가독성이 떨어진다.
    - 따로 Method를 분리하는 작업을 통해 비즈니스 코드를 쉽게 볼 수 있는 장점이 있다.


- UserService를 인터페이스로 구현하여 비즈니스 로직과 트랜잭션를 두 가지의 관심사를 분리하여 구현한다.
    - 비즈니스 로직만 담당하는 UserServiceImpl
    - 트랜잭션만 관리하는 UserServiceTx

![No Image](/nesoy/Images/Spring/23.png)


## 6.2 고립된 단위 테스트
- 작은 단위의 테스트가 좋은 이유는 테스트가 실패하였을 때 그 원인을 찾기 쉽기 때문
- 비즈니스 로직은 굉장히 심플하지만?
    - 다른 오브젝트와 환경에 의존하고 있다면 작은 테스트 조차 하기 힘든 상황
    - DB가 함께 동작해야 하는 테스트는 작성하기 힘든 경우도 많다.
- 외부에 의존하는 오브젝트를 Mocking 처리한다.
    - 사용자 관리 로직을 검증하는 데 직접적으로 필요하지 않은 의존 오브젝트와 서비스를 모두 제거한 덕분

### 단위 테스트와 통합 테스트
- 단위 테스트
    - 중요한 것은 하나의 단위에 초점을 맞춘 테스트
    - 테스트 대역을 이용해 의존 오브젝트나 외부의 리소스를 사용하지 않도록 고립시켜서 테스트하는 것
- 통합 테스트
    - 두 개 이상의, 성격이나 계층이 다른 오브젝트가 연동하도록 만들어 테스트하거나, 또는 외부의 DB나 파일, 서비스등의 리소스가 참여하는 테스트

### 단위 테스트 & 통합 테스트 가이드라인
- 항상 단위 테스트를 먼저 고려한다.
- 하나의 클래스나 성격과 목적이 같은 긴밀한 몇 개를 모아서 `외부와의 의존관계를 모두 차단`하고 필요에 따라 Stub or Mock Object를 사용하여 테스트를 만든다.
- 단위 테스트는 테스트 작성도 간단하고 실행 속도도 빠르며 테스트 대상 외의 코드나 환경으로부터 테스트 결과에 영향을 받지도 않기 때문에 가장 빠른 시간에 효과적인 테스트를 작성하기에 유리하다.
- 외부 리소스를 사용해야만 가능한 테스트는 통합 테스트로 만든다.
- DAO는 DB까지 연동하는 테스트로 만드는 편이 효과적이다.
    - DB를 사용하는 테스트는 DB에 테스트 데이터를 준비하고 DB에 직접 확인을 하는 등의 부가적인 작업이 필요하다.
- 각각의 단위 테스트가 성공했더라도 여러 개의 단위를 연결해서 테스트하면 오류가 발생할 수도 있다.
    - 충분한 단위 테스트를 거친다면 통합 테스트에서 오류가 발생할 확률도 줄어들고 발생한다고 하더라도 쉽게 처리가 가능하다.
- 코드를 만들고 나서 오랜 시간이 지난 뒤에 작성하는 테스트는 테스트 대상 코드에 대한 이해가 떨어지기 때문에 불완전해지기 쉽고 작성하기도 번거롭다.
- 테스트가 없다면 코드의 품질은 점점 떨어지고 유연성과 확장성을 잃어갈지 모른다.

- <https://nesoy.github.io/articles/2018-09/Mockito>

## 6.3 다이내믹 프록시와 팩토리 빈
- 부가기능과 핵심기능 코드를 완전히 분리한다.
- 분리된 부가기능은 원래 핵심기능을 가진 클래스로 위임해줘야 한다.
- 핵심기능은 부가기능을 가진 클래스의 존재 자체를 몰라야 한다.


### Proxy Pattern
![No Image](/nesoy/Images/Spring/24.png)

- 마치 자신이 클라이언트가 사용하려고 하는 실제 대상인 것처럼 위장해서 클라이언트의 요청을 받아주는 것을 대리자
- 대리인과 같은 역할을 한다고 해서 Proxy Pattern
    - Proxy를 통해 최종적으로 요청을 위임받아 처리하는 실제 Object를 Target 또는 실체라고 부른다.
- 클라이언트가 타깃에 접근하는 방법을 제어하기 위해서다.
- 타깃에 부가적인 기능을 부여해주기 위해서다.


### Decorator Pattern
- Target에 부가적인 기능을 Runtime시 다이내믹하게 부여해주기 위해 Proxy를 사용하는 Pattern을 말한다.
- 다이내믹하게 기능을 부가한다는 의미는 컴파일 시점, 즉 코드상에서는 어떤 방법과 순서로 Proxy와 타깃이 연결되어 사용되는지 정해져 있지 않다는 뜻이다.

![No Image](/nesoy/Images/Spring/25.png)

- 각 Proxy는 인터페이스로 접근하기 때문에 자신이 최종 타깃으로 위임하는지, 아니면 다음 단계의 Decorator Proxy로 위임하는지 알지 못한다.
- `InputStream`, `OutputStream`이 대표적인 예다.


### Proxy Pattern
- 원격 Object를 실행하고 결과를 받을 때 Proxy Pattern을 사용하면 관리하기 편하다.
- 타깃에 대한 접근권한을 제어하기 위해 Proxy Pattern을 사용할 수 있다.


### Proxy VS Decorator
- 프록시는 코드에서 자신이 만들거나 접근할 타깃 클래스 정보를 알고 있는 경우가 많다.
    - 항상 그런건 아니다.
- 사용의 목적이 기능의 부가인지, 아니면 접근 제어인지를 구분해보면 각각 어떤 목적으로 Proxy가 사용됐는지 이해할 수 있다.

### Dynamic Proxy
- 항상 Proxy를 만들기에는 비용이 많이 든다.
- 그래서 java.lang.reflect를 사용하여 손쉽게 Proxy를 만들 수 있도록 지원해주는 클래스들이 있다.
- 유연한 설계

#### Reflection
- 클래스 Object를 이용하여 Meta정보를 가져오거나 Object를 조작할 수 있다.
- Method를 실행하기위해선 `invoke`를 사용하자.

#### 다이내믹 프록시를 위한 팩토리 빈
- 스프링은 내부적으로 리플렉션 API를 이용해서 빈 정의에 나오는 클래스 이름을 가지고 빈 오브젝트를 생성한다.
- 팩토리 빈
    - 스프링을 대신해어 오브젝트의 생성로직을 담당하도록 만들어진 특별한 Bean
    - <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/FactoryBean.html>
    - T	getObject()
        - Return an instance (possibly shared or independent) of the object managed by this factory.
    - java.lang.Class<?>	getObjectType()
        - Return the type of object that this FactoryBean creates, or null if not known in advance.
    - default boolean	isSingleton()
        - Is the object managed by this factory a singleton? That is, will getObject() always return the same object (a reference that can be cached)?


- ApplicationContext로 가져오게 된다면 FactoryBean을 사용하여 Bean을 얻는다.
- 실제 FactoryBean을 가져오고 싶은 경우 `&`을 사용한다면 실제 FactoryBean을 얻을 수 있다.

![No Image](/nesoy/Images/Spring/26.png)

#### 프록시 팩토리 빈 방식의 장점과 한계
- 한번 부가기능을 가진 프록시를 생성하는 팩토리 빈을 만들어두면 타깃의 타입에 상관없이 재사용할 수 있기 때문
- 데코레이터 패턴이 적용된 프록시 문제점
    - 프록시를 적용할 대상이 구현하고 있는 인터페이스를 구현하는 프록시 클래스를 일일이 만들어야 한다는 번거로움
    - 부가적인 기능이 여러 메소드에 반복적으로 나타나게 돼서 코드 중복 문제 발생
- 다이내믹 프록시의 장점
    - 다양한 타깃 오브젝트에 적용이 가능
- 프록시 팩토리 빈의 한계
    - 메소드 단위로 발생
    - 한 번에 여러 개의 클래스에 공통적인 부가기능을 제공하는 일은 지금까지 살펴본 방법으론 불가능
    - 적용 대상인 서비스 클래스가 200개쯤 된다면..?
        - 매번 XML 설정이 4000라인 정도..
        - 너무 힘들고 실수가능성도 높다.
    - 이를 해결하기 위해 Spring은 어떤 해결방법을 사용했을까?

## 6.4 스프링의 프록시 팩토리 빈
- 스프링은 일관된 방법으로 프록시를 만들 수 있게 도와주는 추상 레이어를 제공.
- 생성된 프록시는 스프링의 Bean으로 등록돼야 한다.
- ProxyFactoryBean은 프록시를 생성해서 빈 오브젝트로 등록하게 해주는 팩토리 빈.
- 기존의 TxProxyFactoryBean과 달리 ProxyFactoryBean은 순수하게 프록시를 생성하는 작업만을 담당하고 프록시를 통해 제공해줄 부가기능은 별도의 빈에 둘 수 있다.
- ProxyFactoryBean이 생성하는 프록시에서 사용할 부가기능은 MethodInterceptor 인터페이스를 구현해서 만든다.

### MethodInterceptor vs InvocationHandler의 차이점
- InvocationHandler의 `invoke()` 메소드는 타깃 오브젝트에 대한 정보를 제공하지 않는다.
- 따라서 Target은 InvocationHandler를 구현한 클래스가 직접 알고 있어야 한다.
- MethodInterceptor는 ProxyFactoryBean으로부터 Target을 제공받기에 독립적으로 만들어질 수 있다.

### 어드바이스 : Target이 필요 없는 순수한 부가기능
- MethodInvocation은 일종의 Callback 오브젝트
- MethodInterceptor는 Advice Interface를 상속하고 있는 [Sub-Interface](http://aopalliance.sourceforge.net/doc/org/aopalliance/intercept/MethodInterceptor.html)

![No Image](/nesoy/Images/Spring/27.png)
- InvocationHandler가 타깃과 메소드 선정 알고리즘에 의존하고 있다는 점.
- 여러 프록시가 공유할 수 없는 문제점.
- Target 변경고하 메소드 선정 알고리즘 변경 같은 확장이 필요하면 팩토리 Bean 내의 Proxy 생성코드를 직접 변경해야 한다.


![No Image](/nesoy/Images/Spring/28.png)
- Advice : 부가기능을 제공하는 오브젝트
- 포인트컷 : 메소드 선정 알고리즘을 담은 Object
- Advisor : 포인트컷과 Advice의 조합

#### Flow
- 프록시는 클라이언트로부터 요청을 받으면 pointcut에게 부가기능을 부여할 Method인지 판별
- 판별후 MethodInteceptor 타입의 advice를 호출
    - InvocationHandler와 달리 직접 Target을 호출하지 않는다.
- 프록시로부터 전달받은 MethodInvocation 타입 Callback Object의 proceed() 메소르드를 호출해주기만 하면된다.
- 재사용 가능한 기능을 만들어두고 바뀌는 부분(콜백 오브젝트와 메소드 호출정보)만 외부에서 주입해서 이를 작업 흐름(부가기능 부여) 중에 사용하도록 하는 전형적인 템플릿 콜백 구조.

## 6.5 스프링 AOP