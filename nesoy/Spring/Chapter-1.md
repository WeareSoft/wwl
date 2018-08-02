# 오브젝트와 의존관계
- 스프링이 가장 관심을 많이 두는 대상은 오브젝트
- Life Cycle : 생성 -> 관계를 맺고 -> 사용 -> 소멸

#### DAO(Data Access Object)
- DB를 사용해 데이터를 조회하거나 조작하는 기능을 전담하도록 만든 Object

#### JavaBean
##### Default Constructor
- 자바빈은 파라미터가 없는 Default Constructor를 갖고 있어야 한다.
- Reflection을 이용해 Object를 생성하기 때문에 필요하다.

##### Property
- 자바빈이 노출하는 이름을 가진 속성을 Property라고 한다.
- setter, getter로 수정 및 조회가 가능하다.


## 1.1 초난감 DAO
> 하나의 Method안에 모두 존재.

1. DB 연결을 위한 Connection을 가져온다
2. SQL을 담은 Statement, PreparedStatement를 만든다
3. 실행 후 결과를 ResultSet으로 받는다
4. Connect, Statement, ResultSet를 닫아준다
5. 실행 중 발생한 Exception을 잡아서 직접 처리하거나 throws로 던진다


## 1.2 DAO의 분리
### 관심사의 분리
- 객체지향의 세계에서는 모든 것이 변한다.
- 객체를 설계할 때 가장 염두에 둬야 할 사항은 바로 미래의 변화를 어떻게 대비할 것인가?
- 관심이 같은 것끼리는 모으고, 관심이 다른 것은 따로 떨어져 있게 하는것.

### UserDao의 관심사항
- 어떤 DB를 쓸까? 어떤 드라이버를 사용할까하는 관심.
- Statement를 만들고 실행하는 것는 관심.
- Statement와 Connection Object 자원을 반환하는 관심.

#### Refactoring
- 기존의 코드를 외부의 동작방식에는 변화 없이 내부 구조를 변경해서 재구성하는 작업 또는 기술.


#### Template Method Pattern
- 슈퍼클래스에 기본적인 로직의 흐름을 만들고 그 기능의 일부를 추상 메소드나 오버라이딩이 가능한 protected 메소드등으로 만든 뒤 서브클래스에서 이런 메소드를 필요에 맞게 구현해서 사용하도록 하는 방법
#### Factory method Pattern
- 오브젝트를 어떻게 생성할 것인지를 결정하는 방법

## 1.3 DAO의 확장
### 상속의 문제
- 상속을 통해 관심이 다른 기능을 분리하고, 필요에 따라 다양한 변신이 가능하도록 확장성도 줬지만 `상속관계는 긴밀한 결합을 허용`

### Interface의 도입
> 추상화란 어떤 것들의 공통적인 성격을 뽑아내어 이를 따로 분리해내는 작업.


### Object 간의 관계는 어떻게 만들어 줄까?
- 직접 생성자를 호출해서 직접 오브젝트를 만드는 방법
- 외부에서 만들어 준 것을 가져오는 방법도 있다.(Method Parameter)
- code level에서 특정 클래스를 전혀 알지 못하더라도 해당 클래스가 구현한 인터페이스를 사용했다면, 그 클래스의 오브젝트를 인터페이스 타입으로 받아서 사용할 수 있다. 바로 다형성이라는 특징이 있기 때문이다.
- 오브젝트 관계를 `런타임`에 만들어주는 방식이다.


### OCP
- 클래스나 모듈은 확장에는 열려 있어야 하며 변경에는 닫혀있어야 한다.


### 높은 응집도
- 응집도가 높다는 것은 변화가 일어날 때 해당 모듈에서 변하는 부분이 크다는 것으로 설명할 수도 있다.

### 낮은 결합도
- 하나의 오브젝트가 변경이 일어날 떄에 관계를 맺고 있는 다른 오브젝트에게 변화를 요구하는 정도

## 1.4 제어의 역전(Ioc)
- IoC(Inversion of Control)
- 제어의 흐름의 개념을 거꾸로 뒤집는 것이다.
- 오브젝트가 자신이 사용할 오브젝트를 스스로 선택하지 않는다. 당연히 생성하지도 않는다.
- 자신도 어떻게 만들어지고 어디서 사용되는지를 알 수 없다.
- 모든 제어 권한을 자신이 아닌 다른 대상에게 위임하기 때문이다.
### 라이브러리 vs 프레임워크
- 라이브러리를 사용하는 애플리케이션 코드는 애플리케이션 흐름을 직접 제어한다.
    - 단지 동작하는 중에 필요한 기능이 있을 때 능동적으로 라이브러리를 사용할 뿐이다.
- 프레임워크는 애플리케이션 코드가 프레임워크에 의해 사용된다.
    - 프레임워크가 흐름을 주도하면서 개발자가 만든 어플리케이션 코드를 사용하도록 만드는 방식


## 1.5 스프링의 IoC
### Bean
- 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트
- 모든 오브젝트가 다 Bean은 아니라는 사실.

### Bean Factory[Spring Container]
![No Image](/nesoy/Images/Spring/1.png)
- Bean의 생성과 관계설정 같은 제어를 담당하는 IoC 오브젝트
- Bean을 등록, 생성, 조회, 돌려주고 관리하는 기능을 담당
- `@Configuration` : Object 설정을 담당하는 클래스라고 인식하는 Annotation
- `@Bean` : 오브젝트를 만들어 주는 메소드에 사용
- `@Configuration`이 붙은 DaoFactory를 사용하기 위해선 AnnotationConfigApplicationContext를 이용하면 된다.


### Application Context
- Bean Factory를 확장한 IoC
- 스프링이 제공하는 애플리케이션 지원 기능을 모두 포함해서 이야기하는 것.


```java
ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
UserDao dao = context.getBean("userDao", UserDao.class) // Method-Name, Return-Type
```

- getBean() : Return Object Type -> Not Type-Safe
- 매번 Return하는 Object를 다시 Casting하는 비용이 있다.
- Java generic Method 방식을 사용하면 편해진다.


### Spring Container를 사용하면 무엇이 좋을까?
#### 구체적인 팩토리 클래스를 알 필요가 없다.
- 필요한 오브젝트를 가져오려면 팩토리 클래스를 알아야 하는 번거로움이 있지만, 이를 일관된 방식으로 원하는 오브젝트를 가져올 수 있다.

#### Application Context는 종합 IoC 서비스를 제공해준다.
- Object생성과 관계설정만 하는게 아니다.
    - Object가 만들어지는 방식
    - 시점과 전략을 다르게 가져갈수도 있고
    - 이에 부가적인 자동생성
    - 오브젝트에 대한 후처리
    - 정보의 조합
    - 설정 방식의 다변화
    - 인터셉팅등 다양한 기능

#### Application Context는 빈을 검색하는 다양한 방법을 제공한다.
- getBean() Method는 빈의 이름을 이용해 빈을 찾아준다.


### 1.6 싱글톤 레지스트리와 오브젝트 스코프
#### Object의 동일성(identical)과 동등성(equivalent)
- 동일성은 `==`, 동등성은 `equals()`
- Spring은 여러 번에 걸쳐 Bean을 요청하더라도 매번 동일한 오브젝트를 돌려준다는 것이다. 단순하게 getBean()을 실행할 때마다 userDao()를 호출하고 매번 new에 의해 새로운 UserDao가 만들어지지 않는다는 뜻이다.

- Spring은 기본적으로 별다른 설정을 하지 않으면 내부에서 생성하는 Bean Object를 모두 Singleton으로 만든다.
- Servlet은 대부분 Multi thread 환경에서 싱글톤으로 동작한다. Servlet 클래스당 하나의 오브젝트만 만들어두고, 사용자의 요청을 담당하는 여러 스레드에서 하나의 오브젝트를 공유해 동시에 사용한다.

#### Singleton Problem
- private Constructor를 갖고 있기 때문에 상속할 수 없다.
- 싱글톤은 테스트하기가 힘들다.
    - 테스트용 오브젝트로 대체하기가 힘들다.
- 서버환경에서는 싱글톤이 하나만 만들어지는 것을 보장하지 못한다.
    - 서버에서 클래스 로더를 어떻게 구성하고 있느냐에 따라서 싱글톤 클래스임에도 하나 이상의 오브젝트가 만들어질 수 있다.
    - 여러 개의 JVM에 분산돼서 설치가 되는 경우에도 각각 독립적으로 오브젝트가 생기기 때문에 싱글톤으로서의 가치가 떨어진다.
- 싱글톤의 사용은 전역 상태를 만들 수 있기 때문에 바람직하지 못하다.
    - global state로 사용되기 쉽다.
    - 객체지향 프로그래밍에서는 권장되지 않는 프로그래밍 모델이다.

#### Singleton Registry
- 싱글톤 형태의 오브젝트를 만들고 관리하는 기능을 제공한다.
- 평범한 자바 클래스를 싱글톤으로 활용하게 해준다는 점이다.

#### Singleton Multi Thread
- 여러 스레드가 동시에 접근해서 사용할 수 있다. 따라서 상태 관리에 주의를 기울여야 한다.
- 상태정보를 내부에 갖고 있지 않는 무상태(Stateless)방식으로 만들어져야 한다.
- 인슽턴스 변수를 수정하는 것은 매우 위험하다. Stateful방식으로 만들지 않는다.
- 단순한 읽기전용 값이라면 싱글톤에서 인스턴스 변수로 사용해도 좋다. // static final, final로 선언하는 편이 나을 것이다.


#### Bean Scope
- Default : 컨테이너 내에 한 개의 오브젝트만 만들어져서, 강제로 제거하지 않는 한 스프링 컨테이너가 존재하는 동안 계속 유지된다.
- Prototype Scope : 빈을 요청할 때마다 매번 새로운 오브젝트를 만들어준다.
- Request Scope : Http 요청이 생길때마다 생성되는 Scope
- Session Scope : 웹의 세션과 스코프가 유사하다.


