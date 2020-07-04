# CHAPTER 3. 데이터로 작업하기

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
### @SpringBootTest
- 전체 응용 프로그램 컨텍스트를 시작한다.
- Web Application의 Context와 설정을 모두 불러와 실제 웹 서버와 연결하여 **서버를 시작한다.**
  - 모든 bean을 주입하기 때문에 속도가 느리다.
- 클라이언트와 통합 환경에서 테스트(**통합 테스트**)하기 좋다.

어노테이션	| 설명 |	Bean
--- | --- | ---
@SpringBootTest	| 통합 테스트 | 전체	Bean 전체
@SpringBootTest| 	통합 테스트 | 전체	Bean 전체
@WebMvcTest	| 단위 테스트, Mvc 테스트	| MVC 관련된 Bean
@DataJpaTest| 	단위 테스트, Jpa 테스트 | JPA 관련 Bean
@RestClientTest	| 단위 테스트, Rest API 테스트	| 일부 Bean
@JsonTest	| 단위 테스트, Json 테스트	| 일부 Bean

### @WebMvcTest
- 응용 프로그램의 controller layer 를 테스트(**contrller 테스트**) 하기 위한 것이다.
- **서버를 전혀 시작하지 않고** 그 아래의 계층만 테스트하는 것이다.
  - 그렇기 때문에 가볍고 빠르게 테스트 가능하다.
- Controller layer를 테스트하고 모의 객체를 사용하기 때문에 나머지 필요한 bean을 직접 세팅해줘야 한다.

### 기본 사용 예시 
- 추가 필요.

#### :link: Reference
- [공식 문서 - boot-features-testing-spring-boot-applications](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications)
- [https://spring.io/guides/gs/testing-web/](https://spring.io/guides/gs/testing-web/)
- [https://cheese10yun.github.io/spring-boot-test/](https://cheese10yun.github.io/spring-boot-test/)

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
- [[Stack Overflow] Why are circular references considered harmful?](https://stackoverflow.com/questions/1897537/why-are-circular-references-considered-harmful)

#### :link: Reference
- [[BAEKJungHo] 스프링 순환 참조(Circular Reference)](https://medium.com/webeveloper/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%88%9C%ED%99%98-%EC%B0%B8%EC%A1%B0-circular-reference-d01c6beee7e6)
- [[Hans] 순환참조 끊자.](https://joycoding.wordpress.com/2016/02/05/%EC%88%9C%ED%99%98%EC%B0%B8%EC%A1%B0-%EB%81%8A%EC%9E%90-breaking-dependency-circle/)
- [[Stack Exchange] What's wrong with circular references?](https://softwareengineering.stackexchange.com/questions/11856/whats-wrong-with-circular-references)


## :heavy_check_mark: Gradle Dependency 키워드 적용 우선순위

#### :link: Reference
- []()


## :heavy_check_mark: CGLIB와 Dynamic Proxy 의 개념 및 차이

#### :link: Reference
- []()


## :heavy_check_mark: Lombok 어노테이션이 내부적으로 언제 어떻게 적용되는지
- Pluggable Annotation Processing API
    - Java 1.6에서 추가된, 컴파일 시 Annotation을 처리하기 위한 구조.
- Lombok의 Annotation Processor
    - <https://github.com/rzwitserloot/lombok/blob/0d7540db0cc0c9a2758799522925f09eddeb7420/src/launch/lombok/launch/AnnotationProcessor.java>
- 내가 알고 있던 롬복에 대한 오해들..
    - Lombok은 Runtime에 Byte코드를 조작할 줄 알았지만. 실제로는 Java의 Compiler API를 사용해서 생성하고 있었다.
    - 그렇다면 왜 롬복은 좋지 않다고 주장하는 것일까? -> 컴파일 레벨을 지원하는데 말이다.
        - 사용하는 개발 도구에서 Annotation Processor를 지원해야 한다.
        - 가장 핵심인 문제점은.. 컴파일러에 의존적이다. 실제로 자바 스펙이 아니라 컴파일러 스펙이라 제공을 안할 수 있다.
            - JDK버전을 올려도 동작안하는 자바 코드가 발생할 수 있다. (이게 무슨.. ㅋㅋㅋ)

#### :link: Reference
- <https://pluu.github.io/blog/android/2015/12/24/annotation-processing-api/>
- <https://docs.oracle.com/en/java/javase/11/docs/api/java.compiler/javax/annotation/processing/package-summary.html>
- <https://www.inflearn.com/course/the-java-code-manipulation/lecture/23435>
- <https://stackoverflow.com/questions/4589184/what-are-the-risks-with-project-lombok>
- <https://stackoverflow.com/questions/3852091/is-it-safe-to-use-project-lombok>


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
확인 시점|Compile|Runtime
처리 여부|반드시 예외처리 해야함|예외처리 하지 않아도 됨
트랜잭션 Rollback 여부|Rollback 안됨|Rollback 진행
대표 Exception|`IOException`, `SQLException`|`NullPointException`, `IllegalArgumentException`



### Unchecked Exception
명시적인 예외 처리를 강제하지 않는 특징이 있기 때문에 Unchecked Exception이라 하며, `catch`로 잡거나 `throw`로 호출한 메서드로 예외를 던지지 않아도 상관 없다.

### Checked Exception
반드시 명시적으로 처리해야 하기 때문에 Checked Exception이라고 하며, `try` `catch`를 해서 에러를 잡든 `throws`를 통해서 호출한 메서드로 예외를 던져야 한다.

#### :link: Reference
- [[Yun Blog] Checked Exception을 대하는 자세](https://cheese10yun.github.io/checked-exception/)
- [[우아한형제들] 응? 이게 왜 롤백되는거지?](https://woowabros.github.io/experience/2019/01/29/exception-in-transaction.html)

## :heavy_check_mark: throw와 throws의 차이
### `throws`
메서드명 뒤에 예외 클래스명와 함께 사용하는 키워드로 메서드에서 해당 예외가 발생할 수 있음을 명시한다. 또한 해당 예외를 상위 메서드에 전가한다.
- 예) 
    ``` java
    public void someMethod() throws RuntimeException {
        // 어떤 내용
    }
    ```
개발자는 이 메서드 사용 시 예외를 적절하게 핸들링하는 작업을 추가할 수 있다. 만약 해당 예외가 checked exception이라면 반드시 예외처리를 해줘야한다.
- 예) 
    ``` java
    public void handlingTryCatch() {
        try {
            someMethod();
        } catch (Exception e) {
            // (방법1) 적절한 예외처리 작업
        }
    }

    public void passToUpper() throws RuntimeException { // (방법2) 다시 상위로 전가
        someMethod();
    }
    ```

### `throw`
실질적으로 예외를 발생시키(던지)는 키워드
- 예)
    ``` java
    public void someMethod() {
        // 어떤 내용
        throw new RuntimeException();
    }
    ```
요구사항에 맞지 않는 상황이 발생하거나 알고리즘적 오류가 발생했을 때 강제로 예외를 발생시킬 수 있다. 

어떤 예외를 더 구체화시킬 때 사용하기도 한다. 예를들어 checked exception이 발생했을 때, 적절한 unchecked exception으로 변환해서 상위에 던져 처리하기도 한다.
- 예)
    ``` java
    public void someMethod() {
        try {
            // 어떤 내용
        } catch (SQLException e) { // 대표적 checked exception
            throw new IllegalArgumentException("Invalid arguments");
        }
    }
    ```


## :heavy_check_mark: Data Persistance, JDBC, JPA의 개념과 차이
### 영속성(Persistence)
- 데이터를 생성한 프로그램이 종료되더라도 사라지지 않는 데이터의 특성을 말한다.
- 영속성을 갖지 않는 데이터는 단지 메모리에서만 존재하기 때문에 프로그램을 종료하면 모두 잃어버리게 된다. 때문에 파일 시스템, 관계형 테이터베이스 혹은 객체 데이터베이스 등을 활용하여 데이터를 영구하게 저장하여 영속성 부여한다.

#### Persistence Layer
- 프로그램의 아키텍처에서, 데이터에 영속성을 부여해주는 계층을 말한다.
- JDBC를 이용하여 직접 구현할 수 있지만 Persistence framework를 이용한 개발이 많이 이루어진다.

#### Persistence Framework
- JDBC 프로그래밍의 복잡함이나 번거로움 없이 간단한 작업만으로 데이터베이스와 연동되는 시스템을 빠르게 개발할 수 있으며 안정적인 구동을 보장한다.
- Persistence Framework는 SQL Mapper와 ORM으로 나눌 수 있다.
  - Ex) JPA, Hibernate, Mybatis 등
- **ORM vs SQL Mapper**
  - ORM은 데이터베이스 객체를 자바 객체로 매핑함으로써 객체 간의 관계를 바탕으로 SQL을 자동으로 생성해주지만 SQL Mapper는 SQL을 명시해줘야 한다.
  - ORM은 관계형 데이터베이스의 ‘관계’를 Object에 반영하자는 것이 목적이라면, SQL Mapper는 단순히 필드를 매핑시키는 것이 목적이라는 점에서 지향점의 차이가 있다.

### JDBC(Java Database Connectivity)
- JDBC는 DB에 접근할 수 있도록 Java에서 제공하는 API이다.
  - 모든 Java의 Data Access 기술의 근간
  - 즉, 모든 Persistence Framework는 내부적으로 JDBC API를 이용한다.
- JDBC는 데이터베이스에서 자료를 쿼리하거나 업데이트하는 방법을 제공한다.

### JPA(Java Persistent API)
- 자바 ORM 기술에 대한 API 표준 명세로, Java에서 제공하는 API이다.
  - 자바 플랫폼 SE와 자바 플랫폼 EE를 사용하는 응용프로그램에서 관계형 데이터베이스의 관리를 표현하는 자바 API이다.
  - 즉, JPA는 ORM을 사용하기 위한 표준 인터페이스를 모아둔 것이다.
  - 기존에 EJB에서 제공되던 엔터티 빈(Entity Bean)을 대체하는 기술이다.
- JPA 구성 요소 (세 가지)
    1. javax.persistance 패키지로 정의된 API 그 자체
    2. JPQL(Java Persistence Query Language)
    3. 객체/관계 메타데이터
- 사용자가 원하는 JPA 구현체를 선택해서 사용할 수 있다.
  - JPA의 대표적인 구현체로는 Hibernate, EclipseLink, DataNucleus, OpenJPA, TopLink Essentials 등이 있다.
  - 이 구현체들을 ORM Framework라고 부른다.

#### :link: Reference
- [https://gmlwjd9405.github.io/2018/12/25/difference-jdbc-jpa-mybatis.html](https://gmlwjd9405.github.io/2018/12/25/difference-jdbc-jpa-mybatis.html)


## :heavy_check_mark: JDBC Connection이 어떻게 이루어지는지

#### :link: Reference
- []()


## :heavy_check_mark: RowMapper, Jackson ObjectMapper, Gson 개념 및 차이
### RowMapper 
- SQL의 결과(record type)를 객체(object type)의 멤버 변수에 적절하게 할당하기 위한 매핑 수단을 말한다.
- 개발자는 mapRow()라는 interface method를 정의하여 결과를 처리한다.
  - 한 번만 사용하는 기능의 경우 RowMapper 인터페이스를 implements 하여 내부 익명 클래스로 작성하여 사용한다.
- 코드가 중복되는 경우 RowMapper 구현 클래스를 별도로 구현하여 코드 중복을 제거할 수 있다.

### Jackson ObjectMapper 
- Jackson 라이브러리의 주요 액터 클래스로서 기본 POJO와의 JSON, 일반 JSON 트리 모델 (JsonNode) 간의 JSON 읽기 및 쓰기 기능 및 변환 수행 관련 기능을 제공한다.
  - *"ObjectMapper는 기본 POJO(Plain Old Java Objects) 또는 범용 JSON Tree Model(JsonNode)에서 JSON을 읽고 쓰는 기능과 변환 수행을 위한 기능을 제공합니다. 또한 다양한 스타일의 JSON 컨텐츠와 함께 작동하고 다형성 및 객체 동일성과 같은 고급 객체 개념을 지원하도록 Customizing할 수 있습니다."*
- 개발 환경이 빅데이터 처리와 같이 주로 **대용량 json 처리 환경**

### Gson 란
- 자바 오브젝트 <-> JSON 변환시켜주는 아주 간단하고 심플한 자바 라이브러리이다. (구글에서 만든 오픈 소스 라이브러리)
- 마이크로 서비스와 분산 아키텍처 설정 등과 같이 **작은 용량의 많은 json 처리 환경**

#### :link: Reference
- [https://www.baeldung.com/jackson-vs-gson](https://www.baeldung.com/jackson-vs-gson)
- [라이브러리별 속도 비교](http://www.yunsobi.com/blog/entry/java-json-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC-%EB%B3%84-parser-%EC%86%8D%EB%8F%84-%EB%B9%84%EA%B5%90)
- [https://djkeh.github.io/articles/The-fastest-way-to-parse-json-data-to-java-kor/](https://djkeh.github.io/articles/The-fastest-way-to-parse-json-data-to-java-kor/)
- [https://gmlwjd9405.github.io/2018/12/19/jdbctemplate-usage.html](https://gmlwjd9405.github.io/2018/12/19/jdbctemplate-usage.html)


## :heavy_check_mark: PreparedStatementCreator, KeyHolder 클래스란
- PreparedStatementCreator
    - Spring-Jdbc에서 제공하는 PreparedStatement을 생성하는 인터페이스를 가지고 있다.
    - 사실 생성은 Connection에서 하는데 굳이.. PreparedStatementCreator을 만든 이유가 있을까?
        - Factory에서는 PreparedStatement를 생성과 동시에 Value들을 Setting을 책임을 가지고 있다.
        - Java Doc을 보면 Responsible이 명시되어있다.
            - Implementations are responsible for providing SQL and any necessary parameters.
    - 구현체
        - PreparedStatementCreatorFactory의 내부 클래스 PreparedStatementCreatorImpl
        - SimplePreparedStatementCreator
- KeyHolder
    - Interface for retrieving keys, typically used for auto-generated keys as potentially returned by JDBC insert statements.
    - 키를 조회하기 위한 인터페이스로 주로 자동생성된 키를 돌려받기 위해 사용된다.
    - 구현체
        - GeneratedKeyHolder

### JDBC에서 update 수행 시 사용
- 자바의 JDBC에서는 인터페이스만 정의한다.
    - 실제로 Update 수행시 어떻게 동작하는지는 구현체에 따라 결정이 된다.
    - Spring에서는 구현체를 조금 더 쉽게 다루기 위해 JDBCTemplate로 추상화하여 사용
- PreparedStatement
    - SQL의 정보를 담는 역할
    - An object that represents a precompiled SQL statement.
    - A SQL statement is precompiled and stored in a PreparedStatement object.
    - This object can then be used to efficiently execute this statement multiple times.
- Connection
    - Session기반으로 구성된다.
- 왜 PreparedStatement는 Connection에서 생성해야 할까?
    - Connection당 하나의 Statement를 가지고 있어서 그런게 아닐까?
    - 뭔가 찝찝한 답변 :(
    - <https://stackoverflow.com/questions/964989/why-do-i-need-a-connection-to-create-preparedstatements>

#### :link: Reference
- [KeyHolder Document](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/support/KeyHolder.html)
- [PreparedStatementCreator Document](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/PreparedStatementCreator.html)
- [PreparedStatement Document](https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html)
- [Connection Document](https://docs.oracle.com/javase/8/docs/api/java/sql/Connection.html)


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
    - Extension of CrudRepository to provide additional methods to retrieve entities using the pagination and sorting abstraction.
    - Page, Iterable로 조회할 수 있는 인터페이스가 존재
#### :link: Reference
- <https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/CrudRepository.html>
- <https://docs.spring.io/spring-data/data-commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html>
- <https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html>

## :heavy_check_mark: 직렬화(Serialize) 란
### 직렬화
- nesoy 블로그 참조
    - https://nesoy.github.io/articles/2018-04/Java-Serialize


### SUID(`serialVersionUID`) 를 사용하는 이유
- 임의의 객체를 역직렬화 할 때, 변환한 객체(클래스)와 변환되는 객체가 호환 가능함을 명시 및 확인하는 용도
- SUID가 다르다면 역직렬화 시 `java.io.InvalidClassException`예외가 발생한다.
- SUID의 기본값은 해당 객체의 HashCode이다.
    - 그래서 따로 관리해주지 않으면 클래스 맴버 변수의 추가, 삭제, (타입)변경 될 시 SUID는 변경되기 때문에 역직렬화가 제대로 이루어지지 않는다.
- 자세한 내용은 아래를 참조
    - [[우아한형제들] 자바 직렬화, 그것이 알고싶다. 실무편](https://woowabros.github.io/experience/2017/10/17/java-serialize2.html)

#### :link: Reference
- [[Nesoy Blog] Java의 직렬화(Serialize)란?](https://nesoy.github.io/articles/2018-04/Java-Serialize)
- [[OKKY] 직렬화 하는 이유가?](https://okky.kr/article/224715?note=1095481)
- [[우아한형제들] 자바 직렬화, 그것이 알고싶다. 훑어보기편](https://woowabros.github.io/experience/2017/10/17/java-serialize.html)
- [[우아한형제들] 자바 직렬화, 그것이 알고싶다. 실무편](https://woowabros.github.io/experience/2017/10/17/java-serialize2.html)
- [[OKKY] 직렬화 하는 이유가?](https://okky.kr/article/224715?note=1095481)



---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
