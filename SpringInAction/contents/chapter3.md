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

#### :link: Reference
- []()


## :heavy_check_mark: 순환 참조의 개념, 예시, 문제점 

#### :link: Reference
- []()


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

#### :link: Reference
- []()


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

#### :link: Reference
- []()


## :heavy_check_mark: @Prepersist 

#### :link: Reference
- []()


## :heavy_check_mark: CrudRepository, JpaRepository 의 차이

#### :link: Reference
- []()


## :heavy_check_mark: 직렬화(Serialize) 란
### UID 를 사용하는 이유 

#### :link: Reference
- []()



---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
