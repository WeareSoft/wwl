# CHAPTER 3. 데이터로 작업하기

<<<<<<< HEAD
## :heavy_check_mark: root context와 servlet context의 개념과 차이
 이 내용은 다음 항목과도 관련이 있다

 ### Spring Context
 - Context: 어떤 객체를 핸들링하기 위한 접근 수단
 - Spring Context: 컨텍스트 정보를 포함하고 있는 설정 파일
    - 스프링 컨텍스트에는 JNDI, EJB, e-mail, internalization, validation, and scheduling functionality 같은 커다란 서비스를 포함되어 있다.
    - spring-core, spring-beans 모듈을 확장해서 국제화, 이벤트 처리, 리소스 로딩, 서블릿 컨테이너를 위한 컨텍스트 생성 등의 기능을 추가로 제공하고, ApplicationContext 인터페이스를 통해 구현한다.
    - 즉, 스프링 컨텍스트 = 주요 서비스들을 설정하는 녀석

### Root context와 Servlet context
- ![이미지](../images/context.png)
#### Root context
- Web Application 최상단에 위치하고 있는 Context
- Spring에서 ApplicationContext란 BeanFactory를 상속받고 있는 Context
- Spring에서 root-context.xml, applicationContext.xml 파일은 ApplicationContext 생성 시 필요한 설정정보를 담은 파일 (Bean 선언 등..)
- Spring에서 생성되는 Bean에 대한 IoC Container (또는 Bean Container)
- 특정 Servlet설정과 관계 없는 설정을 한다 (@Service, @Repository, @Configuration, @Component)
- 서로 다른 여러 Servlet에서 공통적으로 공유해서 사용할 수 있는 Bean을 선언한다.
- Application Context에 정의된 Bean은 Servlet Context에 정의 된 Bean을 사용할 수 없다.

#### Servlet context
- Servlet 단위로 생성되는 context
- Spring에서 servlet-context.xml 파일은 DispatcherServlet 생성 시에 필요한 설정 정보를 담은 파일 (Interceptor, Bean생성, ViewResolver등..)
- URL설정이 있는 Bean을 생성 (@Controller, Interceptor)
- Application Context를 자신의 부모 Context로 사용한다.
- Application Context와 Servlet Context에 같은 id로 된 Bean이 등록 되는 경우, Servlet Context에 선언된 Bean을 사용한다.
- Bean 찾는 순서
    - Servlet Context에서 먼저 찾는다.
    - 만약 Servlet Context에서 bean을 못찾는 경우 Application Context에 정의된 bean을 찾는다.
- Servlet Context에 정의된 Bean은 Application Context의 Bean을 사용할 수 있다.

#### :link: Reference
- https://www.moongchi.dev/?p=205
- https://m.blog.naver.com/writer0713/220701612165
- https://linked2ev.github.io/spring/2019/09/15/Spring-5-%EC%84%9C%EB%B8%94%EB%A6%BF%EA%B3%BC-%EC%8A%A4%ED%94%84%EB%A7%81%EC%97%90%EC%84%9C-Context(%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8)%EB%9E%80/
=======
## :heavy_check_mark: Context Root와 Service Root의 개념과 차이

#### :link: Reference
- []()
>>>>>>> f0c3d0ac79bcadfe839fd13f0f85cb76851b25d5


## :heavy_check_mark: @Compnent의 스테레오 타입 각각 Context Root와 Service Root 중 어디에 로드되는지

#### :link: Reference

- []()


## :heavy_check_mark: @WebMvcTest와 @MockMvcTest 의 차이

#### :link: Reference
- []()


## :heavy_check_mark: 테스트 관련 어노테이션의 개념과 종류 및 사용 예시
### SpringBoot
### Spring
### JUnit
### Mockito

#### :link: Reference
- []()


## :heavy_check_mark: @SpringBootApplicationTest와 @WebMvcTest의 개념 및 차이

#### :link: Reference
- []()


<<<<<<< HEAD
## :heavy_check_mark: 순환 참조의 개념, 예시, 문제점 
### # 순환 참조
두 모듈이 있을 때, 각 모듈이 서로에 대한 의존성을 갖고 참조하며 호출하는 것을 말한다. 또는 두 개 이상의 모듈에서 각 모듈의 참조가 계속 순환하는 것을 말한다.

### # 왜 안좋을까
- 가장 큰 이유는 상호 의존을 하게 되면 의존을 하는 클래스들끼리 **강한 결합**을 하게 되기 때문.
    - A가 변했을 때, 죄 없는 B도 같이 변해야 할 필요성이 있다는 것이다. 강한 결합은 의도하지 않은 부수효과를 발생하고 결국 전체적인 질이 하락하게 된다.
    - 한 쪽이 변경되면 다른 한 쪽도 재컴파일 되어야한다.
- 의존성 주입이 불가능하게 되고, 테스트의 가능성도 떨어트린다.
- 자세한 사항은 아래 링크를 참조
    - [[Stack Exchange] What's wrong with circular references?](https://softwareengineering.stackexchange.com/questions/11856/whats-wrong-with-circular-references)


### # 생성자를 통한 의존성 주입의 장점
- 생성자를 통한 의존성 주입의 장점은 객체 생성 시점에서 순환 참조가 일어나기 때문에 스프링 애플리케이션이 실행되지 않는다. 즉, 컨테이너가 빈을 생성하는 시점에서 객체생성에 사이클 관계가 생기기 때문. 반면 필드 주입이나 수정자 주입은 객체 생성 시점에 순환 참조가 일어나는지 알 방법이 없다. 수정자 주입은 스프링 애플리케이션이 구동되고 있는 과정에서 순환 참조를 하고 있는 부분에 대한 호출이 이루어질경우 `StackOverflowError`가 발생한다.
- 추가로 의존성 주입이 필요한 필드를 final 로 선언하여 Immutable 하게 사용할 수 있다. 또한 의존관계가 설정되지 않으면 객체 생성이 불가능하기 때문에 `NullPointError`를 방지할 수 있다.

### # 순환참조 해결방법
#### 재설계
사실상 재설계하는 것이 가장 바람직한 방법이다. 

Class A가 B를 참조해야 하는 이유를 골똘히 생각해보자. 왜 참조하고 있는가? 그리고 Class B가 A를 참조해야 하는 이유를 골돌히 생각해보자. 왜 참조하고 있는가?

의존성의 방향을 한쪽 방향으로 바꿔줄 필요가 있다. 의존성의 방향을 한쪽으로만 통제하면 변경에 영향을 받는 부분을 명확하게 이해할 수 있어진다.

혹은 A와 B를 모두 알고 있는 Class C를 만들어서 A와 B에서 의존적으로 하는 일을 위임받아서 하는 방법도 있다.

하지만 실제 운용할 때에는 재설계할 수 없는 상황이 허다할 것이다. (레거시와 많이 엮여있거나, 이미 테스트가 끝난 경우)

#### `@Lazy` 사용
가장 심플한 방법으로 주입하는 곳에 @Lazy 키워드를 붙이는 것으로 해결할 수 있다. 완전히 빈을 초기화하는 것 대신에 프록시가 대신 주입되는 방법이다. 실제로 해당 빈이 사용될 때 빈이 주입되는 방식이다.

#### Setter/Field Injection
가장 많이 사용되는 순환참조 회피 방법으로 Constructor 주입 방법 대신 Setter, Field 주입 방법을 사용하는 것이다.

#### `@PostConstructor` 사용
빈 주입을 @Autowired 대신 @PostConstructor 어노테이션을 사용하여 빈 주입 순서를 명확히 정해주는 방법이다.

### 항상 나쁜건 아니다?
유용할 때도 있다고 한다. 하지만 대부분의 경우 안티패턴인건 사실. 궁금하다면 아래 링크를 참조
- [[Stack Overflow]What's wrong with circular references?](https://stackoverflow.com/questions/1897537/why-are-circular-references-considered-harmful)
=======
## :heavy_check_mark: 순환 참조의 개념, 예시, 문제점
>>>>>>> f0c3d0ac79bcadfe839fd13f0f85cb76851b25d5

#### :link: Reference
- https://medium.com/webeveloper/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%88%9C%ED%99%98-%EC%B0%B8%EC%A1%B0-circular-reference-d01c6beee7e6
- https://joycoding.wordpress.com/2016/02/05/%EC%88%9C%ED%99%98%EC%B0%B8%EC%A1%B0-%EB%81%8A%EC%9E%90-breaking-dependency-circle/
- https://softwareengineering.stackexchange.com/questions/11856/whats-wrong-with-circular-references


## :heavy_check_mark: Gradle Dependency 키워드 적용 우선순위

#### :link: Reference
- []()


## :heavy_check_mark: CGLIB와 Dynamic Proxy 의 개념 및 차이

#### :link: Reference
- []()


## :heavy_check_mark: Lombok 어노테이션이 내부적으로 언제 어떻게 적용되는지

#### :link: Reference
- []()


## :heavy_check_mark: 상황에 따라 어떤 log level을 설정해야 하는지

#### :link: Reference
- []()


## :heavy_check_mark: @Slf4j 사용법 및 예시

#### :link: Reference
- []()


## :heavy_check_mark: Logback 설정 방법(e.g. 출력포맷) 및 장점

#### :link: Reference
- []()

## :heavy_check_mark: Checked Exception과 Unchecked Exception의 차이 
특징|Checked Exception|Unchecked Exception
:---|:---|:---
처리 여부|반드시 예외처리 해야함|예외처리 하지 않아도 됨
트랜잭션 Rollback 여부|Rollback 안됨|Rollback 진행
대표 Exception|`IOException`, `SQLException`|`NullPointException`, `IllegalArgumentException`

### Unchecked Exception
명시적인 예외 처리를 강제하지 않는 특징이 있기 때문에 Unchecked Exception이라 하며, `catch`로 잡거나 `throw`로 호출한 메서드로 예외를 던지지 않아도 상관 없다.

### Checked Exception
반드시 명시적으로 처리해야 하기 때문에 Checked Exception이라고 하며, `try` `catch`를 해서 에러를 잡든 `throws`를 통해서 호출한 메서드로 예외를 던져야 한다.

#### :link: Reference
- [[Yun Blog] Checked Exception을 대하는 자세](https://cheese10yun.github.io/checked-exception/)


## :heavy_check_mark: throw와 throws의 차이

#### :link: Reference
- []()


## :heavy_check_mark: Data Persistance, JDBC, JPA의 개념과 차이

#### :link: Reference
- []()


## :heavy_check_mark: JDBC Connection이 어떻게 이루어지는지

#### :link: Reference
- []()


## :heavy_check_mark: RowMapper, Jackson ObjectMapper, Gson 개념 및 차이

#### :link: Reference
- []()


## :heavy_check_mark: PreparedStatementCreator, KeyHolder 클래스란
### JDBC에서 update 수행 시 사용

#### :link: Reference
- []()


## :heavy_check_mark: @SessionAttribute, @ModelAttribute 개념 및 사용법

#### :link: Reference
- []()


## :heavy_check_mark: @GenerateValue Strategy 종류 및 차이
- AUTO(default)
    - JPA 구현체가 자동으로 생성 전략을 결정한다.
- IDENTITY
    - 기본키 생성을 데이터베이스에 위임한다. 예를 들어 MySQL의 경우 AUTO_INCREMENT를 사용하여 기본키를 생성한다.
- SEQUENCE
    - 데이터베이스의 특별한 오브젝트 시퀀스를 사용하여 기본키를 생성한다.
- TABLE
    - 데이터베이스에 키 생성 전용 테이블을 하나 만들고 이를 사용하여 기본키를 생성한다.

- 추가적으로 Id의 타입에 따라 결정된다.

#### :link: Reference
- <https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html>
- [하이버네이트 Auto Key 전략](https://www.popit.kr/%ED%95%98%EC%9D%B4%EB%B2%84%EB%84%A4%EC%9D%B4%ED%8A%B8%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9E%90%EB%8F%99-%ED%82%A4-%EC%83%9D%EC%84%B1-%EC%A0%84%EB%9E%B5%EC%9D%84-%EA%B2%B0%EC%A0%95%ED%95%98/)
- [GenerateValue 주의할점](https://semtax.tistory.com/94)
- [Spring Boot Data JPA 2.0 에서 id Auto_increment 문제 해결](https://jojoldu.tistory.com/295)


## :heavy_check_mark: @Prepersist
- Entity Listener의 Call Back Option
- @PrePersist
    - manager persist 의해 처음 호출될 때 실행됩니다.
- @PostPersist
    - manager persist 에 의해 실행되고 불립니다.
    - SQL INSERT 이후에 대응될 수 있습니다.
- @PostLoad
    - 로드 이후에 불립니다.
    - SQL SELECT 이후에 대응될 수 있습니다.
- @PreUpdate
    - SQL UPDATE 이전에 불립니다.
- @PostUpdate
    - SQL UPDATE 이후에 불립니다.
- @PreRemove
    - SQL DELETE 이전에 불립니다.
- @PostRemove
    - SQL DELETE 이후에 불립니다.
#### :link: Reference
- <https://gs.saro.me/dev?tn=514>
- <https://github.com/snack-news/Snack-BE/blob/master/src/main/java/com/snack/news/domain/base/BaseTimeEntity.java>
- <https://www.baeldung.com/jpa-entity-lifecycle-events>


## :heavy_check_mark: CrudRepository, JpaRepository 의 차이
- CrudRepository
    - 단순히 CRUD를 지원
- JpaRepository
    - CrudRepository를 상속받았다.
    - 뿐만 아니라 PagingAndSortingRepository도 존재
- PagingAndSortingRepository
    - 무슨 역할일까?
#### :link: Reference
- <https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/CrudRepository.html>
- <https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html>
- <https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html>

## :heavy_check_mark: 직렬화(Serialize) 란
### UID 를 사용하는 이유

#### :link: Reference
- []()



---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
