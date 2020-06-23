## CHAPTER 1. 스프링 시작하기
### 1. 스프링이란
- 스프링은 **스프링 애플리케이션 컨텍스트(Spring Applciation Context)** 라는 **컨테이너(Container)** 를 제공하는데, 이것은 애플리케이션 컴포넌트들을 생성하고 관리한다.
- 애플리케이션 컴포넌트 또는 **빈(Bean)** 들은 스프링 애플리케이션 컨텍스트 내부에서 서로 연결되어 완전한 애플리케이션을 만든다.
- 빈의 상호 연결은 **의존성 주입(Dependency Injection, DI)** 라고 알려진 패턴을 기반으로 수행된다.
  - 1. 생성자 주입(Constructor Injection)
  - 2. 필드 주입(Field Injection)
  - 3. 수정자 주입(Setter Injection)
- 스프링은 자동으로 컴포넌트들을 구성할 수 있는 자동-구성 기능이 있다.
  - **자동 연결(Autowiring)** 과 **컴포넌트 검색(Component Scaning)** 이라는 스프링 기법을 기반으로 한다.
- 스프링 부트
  - 생산성 향상을 제공하는 스프링 프레임워크의 확장
  - 향상된 자동-구성 기능에 의해 환경 변수인 classpath를 기준으로 어떤 컴포넌트가 구성되고 연결되어야 하는지 알 수 있다.

### 2. 스프링 애플리케이션 초기 설정하기
- Spring Tool Suite(STS)의 스프링 Initializr
- **@SpringBootApplication**
  - @SpringBootConfiguration
    - 현재 클래스를 구성 클래스로 지정한다.
    - @Configuration의 특화된 형태다.
  - @EnableAutoConfiguration
    - 스프링 부트 자동-구성을 활성화한다.
    - 필요로 하는 컴포넌트들을 자동으로 구성하도록 스프링 부트에게 알려준다.
  - @ComponentScan
    - 컴포넌트 검색을 활성화한다.
    - @Component, @Controller, @Service 등의 어노테이션과 함께 클래스를 선언할 수 있게 해준다.
    - 스프링은 자동으로 어노테이션이 붙은 클래스를 찾아 스프링 애플리케이션 컨텍스트에 컴포넌트로 등록한다.

### 3. 스프링 애플리케이션 작성하기
- @Controller, @Component, @Service, @Repository
- Thymeleaf 템플릿 엔진

### 4. 스프링 살펴보기
- 핵심 스프링 프레임워크
  - 스프링 MVC
    - HTML
    - REST API
  - 리액티브 프로그래밍
    - 스프링 WebFlux
- 스프링 부트
  - 스타터 의존성
  - 자동-구성
  - 액추에이터
  - 환경 속성의 명세
  - 핵심 프레임워크에 추가되는 테스트 지원
  - CLI 제공
- 스프링 데이터
  - JPA
- 스프링 시큐리티
- 스프링 통합과 배치
- 스프링 클라우드
  - 마이크로 서비스

---

### 의존성을 주입하는 방법 
#### 1. 생성자 주입(Constructor Injection)
- 스프링 팀에서 권장하는 방식
```java
@Component
public class Example {

    private final HelloService helloService;

    public Example(final HelloService helloService) {
        this.helloService = helloService;
    }
}
```
#### 2. 필드 주입(Field Injection)
```java
@Component
public class Example {

    @Autowired
    private HelloService helloService;
}
```
#### 3. 수정자 주입(Setter Injection)
```java
@Component
public class Example {

    private HelloService helloService;

    @Autowired
    public void setHelloService(final HelloService helloService) {
        this.helloService = helloService;
    }
}
```

#### @Autowired 를 사용하는 필드 주입이나 수정자 주입 방법보다 생성자 주입을 더 권장하는 이유
- 순환 참조를 방지할 수 있다.
  - 생성자 주입 방법은 필드 주입이나 수정자 주입과는 빈을 주입하는 순서가 다르다.
  - 수정자 주입(Setter Injection)
    - 우선 주입(inject) 받으려는 빈의 생성자를 호출하여 빈을 찾거나 빈 팩터리에 등록한다.
    - 그 후에 생성자 인자에 사용하는 빈을 찾거나 만든다.
    - 그 이후에 주입하려는 빈 객체의 수정자를 호출하여 주입한다.
  - 필드 주입(Field Injection)
    - 수정자 주입 방법과 동일하게 먼저 빈을 생성한 후에 어노테이션이 붙은 필드에 해당하는 빈을 찾아서 주입하는 방법이다.
    - 먼저 빈을 생성한 후에 필드에 대해서 주입한다.
  - 생성자 주입(Constructor Injection)
    - 생성자로 객체를 생성하는 시점에 필요한 빈을 주입한다.
    - 먼저 생성자의 인자에 사용되는 빈을 찾거나 빈 팩터리에서 만든다.
    - 그 후에 찾은 인자 빈으로 주입하려는 빈의 생성자를 호출한다.
    - 즉, 먼저 빈을 생성하지 않는다. 수정자 주입과 필드 주입과 다른 방식이다.
  - 따라서 생성자 주입을 이용하면 `BeanCurrentlyInCreationException`이 발생하며 애플리케이션이 구동조차 되지 않는다. 발생할 수 있는 오류를 사전에 알 수 있다.
- 테스트에 용이하다.
  - DI의 핵심은 관리되는 클래스가 DI 컨테이너에 의존성이 없어야 한다는 것이다.
    - 즉, 독립적으로 인스턴스화가 가능한 POJO(Plain Old Java Ojbect) 여야 한다는 것이다.
  - DI 컨테이너를 사용하지 않고서도 단위 테스트에서 인스턴스화할 수 있어야 한다.
- 불변성(Immutability)
  - 필드 주입과 수정자 주입은 해당 필드를 final로 선언할 수 없다.
  - 따라서 초기화 후에 빈 객체가 변경될 수 있지만 생성자 주입은 필드를 final로 선언할 수 있다. 

### Spring과 Spring Boot
#### Spring
> 스프링 프레임워크(Spring Framework)는 자바 플랫폼을 위한 오픈소스 애플리케이션 프레임워크로서 간단히 스프링(Spring)이라고도 불린다. 동적인 웹 사이트를 개발하기 위한 여러 가지 서비스를 제공하고 있다. 대한민국 공공기관의 웹 서비스 개발 시 사용을 권장하고 있는 전자정부 표준프레임워크의 기반 기술로서 쓰이고 있다. [출처: 위키백과]
- 스프링 사이트에서는 스프링 프레임워크가 인프라와 관련된 내용을 애플리케이션 레벨에서 설정하도록 해줌으로써 개발자가 코드로 대부분을 컨트롤 할 수 있게끔 지원한다고 설명한다.
- 즉, 개발자가 코드 안에 애플리케이션 동작에 대한 내용을 기술하면 스프링 프레임워크가 이를 해석해서 동작하는 것이다.
- 스프링을 사용하는 가장 일반적인 예로는 `Servlet API`가 있다.
  - 개발자는 API를 처리할 클래스를 정의하고 이것이 `Servlet API`를 위한 클래스임을 표시(어노테이션 활용)한다.
  - 그 이후 프로그램을 실행하면 스프링은 API 요청이 들어오면 해당 클래스를 이용해서 처리한다.
  - 개발자가 `Servlet`에 관련된 것을 개발하지 않아도 되며, 데이터 바인딩, 객체 생성 등 웬만한 것들은 스프링이 알아서 해준다. 이런 편한 점 때문에 많은 개발자들이 스프링 프레임워크를 사용하고 있다.

#### Spring Boot
- 스프링 프레임워크는 기능이 많은만큼 환경설정이 복잡한 편이다. 이에 어려움을 느끼는 사용자들을 위해 나온 것이 바로 스프링 부트다.
- 스프링 부트는 스프링 프레임워크를 사용하기 위한 설정의 많은 부분을 자동화하여 사용자가 정말 편하게 스프링을 활용할 수 있도록 돕는다.
  - 스프링 부트 starter 디펜던시만 추가해주면 바로 API를 정의하고, 내장된 탐캣으로 웹 애플리케이션 서버를 실행할 수 있다. 
  - 심지어 스프링 홈페이지의 이니셜라이저를 사용하면 바로 실행 가능한 코드를 만들어준다.
  - 실행환경이나 의존성 관리 등의 인프라 관련 등은 신경쓸 필요 없이 바로 코딩을 시작하면 된다.

### @SpringBootApplication
- `@SpringBootConfiguration`, `@ComponentScan`, `@EnableAutoConfiguration` 3가지의 역할을 수행한다.
- 내부적으로 2단계에 걸쳐서 빈을 등록한다.
#### `@ComponentScan`
- 현재 패키지 이하에서 `@Component` 어노테이션이 붙은 클래스를 찾아 빈으로 등록한다.
- `@Controller`, `@Configuration`의 내부를 보면 `@Component` 어노테이션이 붙어있다.
#### `@EnableAutoConfiguration`
- 스프링 부트 자동-구성을 활성화한다.
- 필요로 하는 컴포넌트들을 자동으로 구성하도록 스프링 부트에게 알려준다.
- 기본적으로 설정되어 있는 빈들은 어디에 정의되어 있을까?
  - `External Library` > `spring-boot-autoconfigure`
  - `META-INF` 디렉터리 하위의 `spring.factories` 파일에 자동으로 가져올 빈들이 정의되어 있다.
    - `org.springframework.boot.autoconfigure.EnableAutoConfiguration`라는 키값이 존재하며 하위에 많은 클래스를 가지고 있다.
    - 또한 해당 클래스들은 상단에 `@Configuration`이라는 어노테이션을 가지고 있다.
    - 이러한 키값을 통하여 명시된 많은 클래스들이 AutoConfiguration의 대상이 된다.

---

### Reference
- https://madplay.github.io/post/why-constructor-injection-is-better-than-field-injection
- https://monkey3199.github.io/develop/spring/2019/04/14/Spring-And-SpringBoot.html
- http://blog.naver.com/PostView.nhn?blogId=sthwin&logNo=221271008423
- https://jhleed.tistory.com/126
- https://seongmun-hong.github.io/springboot/Spring-boot-EnableAutoConfiguration