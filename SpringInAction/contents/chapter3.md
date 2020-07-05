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



## :heavy_check_mark: @Component의 스테레오 타입 각각 root context와 servlet context 중 어디에 로드되는지
#### @Component의 스테레오 타입
- @Controller, @Service, @Repository
  
#### root context에서 로드되는 컴포넌트
- @Service, @Repository
- 웹 어플리케이션의 로직을 처리하는 서비스 레이어와 DB와 연결되는 레퍼지토리 레이어를 구성하는 빈 설정

#### servlet context에서 로드되는 컴포넌트
- @Controller
- dispatcher-servlet이 사용하는 URL 관련 정보(@RequestMapping URL, 메소드 정보 등)를 담는 클래스는 servlet context에서 로드(스캔)되어야 함 (ex. Controller, Interceptor)
- Controller의 매핑 설정(Handler Mapping), 요청 처리 후 뷰를 처리(view resolver)하는 빈 설정
  
#### 주의할 점
- 두 context 설정에서 component scan을 통해 빈을 등록할 때 controller, service, repository가 각 컨텍스트에 맞게 등록이 되어있어야 함
- 그렇지 않은 경우 빈이 양쪽 컨텍스트에 모두 등록되어 불필요한 빈 등록 과정이 발생할 수 있음
  
#### :link: Reference
- https://repacat.tistory.com/32
- https://nice2049.tistory.com/entry/spring-rootContext-%EA%B7%B8%EB%A6%AC%EA%B3%A0-servletContext-%EB%8C%80%ED%95%B4%EC%84%9C

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
- Pluggable Annotation Processing API
- <https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html>
- <https://www.inflearn.com/course/the-java-code-manipulation/lecture/23435>
#### :link: Reference
- <https://pluu.github.io/blog/android/2015/12/24/annotation-processing-api/>


## :heavy_check_mark: 상황에 따라 어떤 log level을 설정해야 하는지
(자주 사용하는 로깅 프레임워크인 Log4j와 Logback 예시)
#### Log4j의 로그 레벨
- FATAL : 치명적인 오류 발생 시 (의도하지 않은 오류로 인해 애플리케이션이 종료되는 수준)
- ERROR : 일반적인 오류 발생 시 (애플리케이션이 종료되지는 않지만 의도하지 않은 오류가 발생하는 수준)
- WARN : 주의가 필요할 때
- INFO : 일반 정보를 나타낼 때
- DEBUG : 일반 정보를 상세히 나타낼 때
- TRACE : 경로추적

#### Logback의 로그 레벨
- ERROR : 일반적인 오류 발생 시
- WARN : 주의가 필요할 때
- INFO : 일반 정보를 나타낼 때
- DEBUG : 일반 정보를 상세히 나타낼 때
- TRACE : 경로추적

#### 로그 레벨 작성 기준
- 개발자의 의도에 달려있음
- 명확한 목적을 가지는 레벨은 ERROR와 INFO를 주로 사용
- ERROR
  - 의도하지 않은 오류를 명시적으로 표현
  - 반드시 FATAL이 필요한 경우가 아니라면 ERROR 사용
```
    try {
        User user = userService.findById(userId);
        userRepository.save(user);
    } catch (ConnectionException e) {
        log.error(“데이터베이스 연결 실패");
    }
```

- INFO
  - 서비스의 목적을 성공적으로 달성했는지 확인할 때 사용
  - 대략적인 통계로 활용 가능
  - 만약 시나리오상 의도된 오류가 발생한다면 INFO 사용
```
    try {
        User user = userService.findById(userId);
        log.info(“유저 조회 성공");
        return user.getStatus();
    } catch (NonexistentUserException e) {
        log.info(“해당 사용자가 존재하지 않습니다.”);
        return UserStatus.NOT_REGISTERED;
    }
```

#### :link: Reference
- https://goddaehee.tistory.com/45
- https://blog.lulab.net/programmer/what-should-i-log-with-an-intention-method-and-level/



## :heavy_check_mark: @Slf4j 사용법 및 예시
#### 사용법
- 클래스에 @Slf4j 작성
  - 작성 시 로거 객체 생성
```
    @Slf4j
    public class LogEx {
        ...
    }
    ↑위 코드는 아래 코드로 변환↓
    public class LogEx {
        private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.class);

        ...
    }
```
- class, Enum 단위에 사용 가능
- 애플리케이션에 추가한 slf4j의 구현체 로거 사용
#### 예시
- Logback 사용 시
```
    @Slf4j
    public class LogEx {
        public void logExMethod() {
            ...
            log.error("오류 발생");
            log.warn("경고 발생");
            log.info("정보 확인 또는 성공");
            log.debug("디버깅");
            log.trace("경로 추적");
            ...
        }
    }
```
#### :link: Reference
- https://projectlombok.org/api/lombok/extern/slf4j/Slf4j.html


## :heavy_check_mark: Logback 설정 방법(e.g. 출력포맷) 및 장점
#### 스프링부트에서 설정 방법
- 스프링부트는 Logback이 기본 로깅 프레임워크이기 때문에 별도로 추가할 라이브러리는 없음
- 구현체는 spring-boot-starter-web 내 spring-boot-starter-logging에 있음
- logback-spring.xml 파일에 작성
  - 일반적으로 logback.xml에 작성하지만 스프링부트에서는 logback.xml로 작성 시 부트가 설정하기 전에 로그백 관련 설정이 되어버림
- 로그레벨 지정 방법
  1. application.properties 설정
      - 루트 레벨 설정
        ```
        logging.level.root=info
        ```
      - 패키지별 레벨 설정
        ```
        logging.level.dami.test=info
        logging.level.dami.test.web=debug
        ```
  2. logback-spring.xml 설정
      - 로그 레벨 이름 대소문자 구분하지 않음
      - 작성 방법은 하단 appender 설정 방법 항목에 작성
- Appender 설정 방법
  - Appender는 로그의 출력 포맷 및 로그 저장 위치(콘솔, 파일)를 설정 가능
  - Appender 종류
    - ConsoleAppender : 콘솔에 로그 메시지를 출력한다.
    - FileAppender : 파일에 로그 메시지를 출력한다.
    - RollingFileAppender : 로그의 크기가 지정한 용량 이상이 되면 다른 이름의 파일을 출력한다.
    - DailyRollingFileAppender : 하루를 단위로 로그 메시지를 파일에 출력한다.
    - SMTPAppender : 로그 메시지를 이메일로 보낸다.
    - NTEventLogAppender : 윈도우의 이벤트 로그 시스템에 기록한다.
  - 출력 포맷
    - %d : 로그의 기록시간을 출력한다.
    - %p : 로깅의 레벨을 출력한다.
    - %F : 로깅이 발생한 프로그램의 파일명을 출력한다.
    - %M : 로깅이 발생한 메소드의 이름을 출력한다.
    - %l : 로깅이 발생한 호출지의 정보를 출력한다.
    - %L : 로깅이 발생한 호출지의 라인수를 출력한다.
    - %t : 로깅이 발생한 Thread명을 출력한다.
    - %c : 로깅이 발생한 카테고리를 출력한다.
    - %C : 로깅이 발생한 클래스명을 출력한다.
    - %m : 로그 메시지를 출력한다.
    - %n : 개행 문자를 출력한다.
    - %% : %를 출력
    - %r : 어플리케이션이 시작 이후부터 로깅이 발생한 시점까지의 시간을 출력한다.(ms)
    - %x : 로깅이 발생한 Thread와 관련된 NDC(nested diagnostic context)를 출력한다.
    - %X : 로깅이 발생한 Thread와 관련된 MDC(mapped diagnostic context)를 출력한다.
- logback.spring.xml 작성 예시
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml" />
    <include resource="org/springframework/boot/logging/logback/console-appender.xml" />    

    <!-- 변수 지정 -->
    <property name="LOG_DIR" value="/logs" />
    <property name="LOG_PATH_NAME" value="${LOG_DIR}/data.log" />

    <!-- FILE Appender -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH_NAME}</file>
        <!-- 일자별로 로그파일 적용하기 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH_NAME}.%d{yyyyMMdd}</fileNamePattern>
            <maxHistory>60</maxHistory> <!-- 일자별 백업파일의 보관기간 -->
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%-5p] [%F]%M\(%L\) : %m%n</pattern>
        </encoder>
    </appender>

    <!-- Console Appender -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
      <layout class="ch.qos.logback.classic.PatternLayout">
        <pattern>%d{yyyy-MM-dd HH:mm:ss} [%-5p] [%F]%M\(%L\) : %m%n</pattern>
      </layout>
    </appender>

    <!-- TRACE > DEBUG > INFO > WARN > ERROR, 대소문자 구분 안함 -->
    <!-- profile 을 읽어서 appender 을 설정할수 있다.(phase별 파일을 안만들어도 되는 좋은 기능) -->
    <springProfile name="local">
      <root level="DEBUG">
        <appender-ref ref="FILE" />
        <appender-ref ref="STDOUT" />
      </root>
    </springProfile>
    <springProfile name="real">
      <root level="INFO">
        <appender-ref ref="FILE" />
        <appender-ref ref="STDOUT" />
      </root>
    </springProfile>
</configuration>
```
     

#### 장점
- Log4j에 비해 10배 빠른 수행 및 메모리 효율성 상승
- 서버 중지 없이 설정파일 변경 내용 적용 가능
- 로그 기록하는 파일 서버 장애 발생 시 서버 중지 없이 장애 시점부터 로그 복구 가능
- RollingFileAppender 사용 시 자동으로 로그파일 압축 (설정 변경해서 오래된 로그 압축 파일 삭제 가능)
- 문서화 깔끔
- 설정파일에 조건처리 가능(<if><then><else>) -> 여러 환경 타겟팅 가능


#### :link: Reference
- https://goddaehee.tistory.com/45
- http://logback.qos.ch/reasonsToSwitch.html


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
#### @SessionAttributes
- @ModelAttribute 또는 Model 클래스의 addAttribute()를 사용했을 때, 세션이 만료되거나 sessionStatus로 세션이 제거되기 전까지는 모델에 넘긴 객체를 동일한 세션에서 계속 사용 가능
- @ModelAttribute와 함께 사용해서 세션 객체에 넣고 빼는 작업을 숨겨주고 스프링 form 태그와 연동되어 폼에 값을 넣는 작업도 단순화 되는 장점
- session scope에 저장

#### @ModelAttribute
- Model 클래스의 addAttribute() 메소드와 같은 기능
- ex. @ModelAttribute(“key")
- request scope에 저장

#### 사용 예시
- 등록 폼 페이지가 여러개인 경우 이전 폼, 다음 폼 화면 이동 시 세션 유지 가능

#### 사용 방법
- 클래스에 지정한 @SessoinAttributes에 작성한 이름과 컨트롤러 메소드가 생성한 Model명을 동일하게 맞추면 세션에 저장 가능
- 메소드 인자에 @ModelAttribute가 있으면 세션에서 가져올 수 있음
- 새 오브젝트 생성 전에 @SessoinAttributes에 지정한 이름과 @ModelAttribute에 지정한 이름이 동일하면 세션에 저장된 값 사용
```
@Controller
@SessionAttributes("user")
public class UserController{
  // 수정 폼
  @RequestMapping(value="/user/{uid}", method=RequestMethod.GET)
  public String modifyForm(@PathVariable Long uid, ModelMap modelMap){

    User user = userService.getUser(uid);
    modelMap.addAttribute("user", user);

    return "user/form"
  }

  // 수정
  @RequestMapping(value="/user/{uid}", method=RequestMethod.POST)
  public String modify(@PathVariable Long uid,@ModelAttribute User user){

    userService.modify(user);

    return "user/modifySuccess"
  }
}
```
#### 주의할 점
- @SessionAttributes 사용 시 SessionStatus 클래스의 setComplete() 메소드를 호출하지 않을 경우 세션이 계속 남아있음
- 사용자가 프로세스 중간에 이탈하면 세션에 저장한 객체가 그대로 남게 됨
- 지정해준 클래스 내에서만 세션 사용 가능 (다른 컨트롤러, 인터셉터, 필터 등에서는 사용 불가)
- 다른 컨트롤러, 인터셉터, 필터에서 사용하려면 s가 붙지 않은 @SessionAttribute를 사용

#### :link: Reference
- https://offbyone.tistory.com/333
- https://joont92.github.io/spring/@SessionAttributes,-SessionStatus/



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
