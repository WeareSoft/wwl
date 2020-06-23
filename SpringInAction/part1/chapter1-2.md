# CHAPTER 1. 스프링 시작하기 - CHAPTER 2. 웹 애플리케이션 개발하기
## 부트스트랩

## 어노테이션

## `@Component` 하위 어노테이션의 의미와 역할

## `@WebMvcTest`

## 의존성을 주입(DI)하는 방법 
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

## `@SpringBootApplication`
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

## Spring MVC

## gradle 키워드

## `@RequiredContructor`, `@Data`

## `equals`, `hashCode` 재정의

## slf4j 로깅 종류

## jar 실행 방법, 옵션
- gradle
- maven

## `@RequestMapping` 하위 어노테이션

## Redirect, Forward 차이

## DevTools JVM 클래스로더

---

### Reference
- https://madplay.github.io/post/why-constructor-injection-is-better-than-field-injection
- https://jhleed.tistory.com/126
- https://seongmun-hong.github.io/springboot/Spring-boot-EnableAutoConfiguration