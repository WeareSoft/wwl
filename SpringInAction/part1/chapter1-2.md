# CHAPTER 1. 스프링 시작하기 - CHAPTER 2. 웹 애플리케이션 개발하기
## 부트스트랩
- BIOS (Basic Input/Output System)
  - PC 부팅 과정 중 하드웨어와 관련된 작업을 담당
  - 전원이 켜짐과 동시에 프로세서가 가장 먼저 실행하는 코드
  - 각종 초기화와 테스트를 담당 (POST, Power on Self Test)
  - 부팅 옵션 설정, 시스템의 전반적인 설정 값 관리
  - 가장 중요한 단계는 부트 로더 이미지를 메모리로 복사하는 작업
  - POST 과정까지 마친 후 부트 로더를 찾는 과정 수행
- 부트 로더
	- 부트스트랩 코드라고도 불림
	- BIOS에서 처음으로 제어를 넘겨받는 부분
	- OS나 유틸리티 프로그램을 부팅하는 부트 드라이브(플로피 디스크, 하드 디스크 등)의 가장 앞에 있는 프로그램
	- 컴퓨터 전원 시작 시 CPU와 메모리, 디스크 등을 진단한 후(POST) 부트 로더 실행
	- OS에 필요한 환경설정 및 OS 이미지를 메모리에 복사
	- 이후 작업은 OS가 수행
- 부트스트랩
	- 구동, 시동
	- 컴퓨터를 시작하는 과정(부팅 과정)

## 어노테이션
- @Retention
  - 어노테이션의 Life Time입니다.
- Class
  - 바이트 코드 파일까지 어노테이션 정보를 유지한다.
  - 하지만 리플렉션을 이용해서 어노테이션 정보를 얻을 수는 없다.
- Runtime
  - 바이트 코드 파일까지 어노테이션 정보를 유지하면서 리플렉션을 이용해서 런타임시에 어노테이션 정보를 얻을 수 있다.
- Source
  - Compile 이후로 삭제되는 형태
- <https://nesoy.github.io/articles/2018-04/Java-Annotation>

## `@Component` 하위 어노테이션의 의미와 역할
###  `@Compnent`란?
해당 클래스를 `스프링 컨테이너`에 등록하기 위해서 사용된다. 이렇게 등록된 컨테이너(혹은 빈)은 스프링 컨테이너 의해 적절하게 주입되며 관리된다.

### `@Compnent`의 스테레오타입(Streotype) 어노테이션

구조상 **하위 어노테이션**이라고 표현할 수 있는 `@Compnent`의 스테레오타입 어노테이션은 `@Controller`, `@Service`, `@Repository`가 있다. 어노테이션들은 모두 `@Compenet`를 구현하고 있다.

![이미지](./resources/chapter1-2/component.jpg)

사실 주기능상 큰 차이는 없다. 스프링 컨테이너에 해당 컴포넌트를 등록하는게 주된 목적이다. 때문에 각 어노테이션 대신 `@Compnent`를 사용해도 문제가 없기는 하다.

### `@Controller`, `@Service`, `@Repository`의 차이
위에서 언급했던대로 기능상 큰 차이는 없다. 다만, 적절한 의미의 어노테이션을 붙힘으로써 각 클래스들이 도구가 처리하는데 더 적합하도록 할 수 있고 관점에 더 연관성이 있게 한 수 있다. 예를 들어 이러한 스테레오타입 어노테이션은 포인트컷에 대한 이상적인 타겟을 만든다.

이 장점이 드러나는게 `@Repository`인데, 해당 클래스(persistence 계층)에서 발생한 DB에 관련된 오류는 조금 더 적합하게 또는 구체적으로 변환되어 던져진다. 개발자는 이렇게 발생한 예외를 더 적절하게 처리할 수 있다.

또한 스프링은 차기 릴리즈 버전에서 각 스테레오타입 어노테이션에 추가적인 의미가 생길 가능성도 있다고 말한다. 어떤 기능이 추가될지 모르니 각 역할 또는 의미에 맞게 사용하길 권장하는 것 같다.

## `@WebMvcTest`
- @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter, Filter, WebMvcConfigurer, HandlerMethodArgumentResolver beans들만 등록이 된 상태로 테스트가 진행된다.
- 우리가 잘 알고 있는 @Component, @Service or @Repository beans들은 등록되지 않은 상태로 테스트가 진행된다.
- 아래의 Configuration이 자동등록되는 것들이다.
```
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration
org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration
org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration
org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration
org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration
org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration
org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration
org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration
org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration
org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityConfiguration
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebClientAutoConfiguration
org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebDriverAutoConfiguration
```
- [Reference](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html)
- <https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#test-auto-configuration-slices>

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
### 본래 의문을 가진 계기
Spring MVC 라고 한다면 Spring MVC Pattern(패턴), 혹은 Spring MVC Model(모델), Spring MVC Architecture(구조) 정도였다.

그렇다면 Spring MVC Framework는 무엇일까? 내가 알고있는 Spring Framework랑 동일한 수준의 개념인가? 라는 의문이 생겼다.

### 결론
딱히 그런건 아니다. Spring Framework에서 MVC 패턴을 지원하는 모듈이 존재하고 이를 이르러 Spring MVC Framework라고 표현하는 것. 작게 보면 그 안의 모듈도 프레임워크라고 할 수 있다.

### MVC
#### 무엇인가?
Model, View, Controller의 약자로 디자인 패턴 중 하나. 어떤 시스템에서 각 역할을 어떻게 나눌지에 대한 이슈가 있을텐데, MVC는 그 중에서도 일반화어 널리 쓰이는 패턴 중 하나이다.
- Model
    - 모델은 앱이 포함해야할 데이터가 무엇인지를 정의한다. 데이터의 상태가 변경되면 모델을 일반적으로 뷰에게 알리며(따라서 필요한대로 화면을 변경할 수 있다) 가끔 컨트롤러에게 알리기도 한다. (업데이트된 뷰를 제거하기 위해 다른 로직이 필요한 경우).
- View
    - 뷰는 앱의 데이터를 보여주는 방식을 정의한다.
- Controller
    - 컨트롤러는 앱의 사용자로부터의 입력에 대한 응답으로 모델 및/또는 뷰를 업데이트하는 로직을 포함한다. 단순히 데이터를 다른 형태로 나타내기 위해 뷰를 업데이트하고 싶을 수도 있다. 이런 경우에 컨트롤러는 모델을 업데이트할 필요 없이 바로 처리할 수 있다.

#### 왜 쓰나?
- 사용자가 보는 페이지, 데이터 처리, 그리고 이 2가지를 중간에서 제어하는 컨트롤, 이 3가지로 구성되는 하나의 애플리케이션을 만들면 각각 맡은바에만 집중을 할 수 있게 된다.
​- 서로 분리되어 각자의 역할에 집중할 수 있게끔하여 개발을 하고 그렇게 애플리케이션을 만든다면, 유지보수성, 애플리케이션의 확장성, 그리고 유연성이 증가하고, 중복코딩이라는 문제점 또한 사라지게 되는 것이다.

### 

### Reference
- https://developer.mozilla.org/ko/docs/Glossary/MVC
- https://spring.io/projects/spring-framework
- https://gmlwjd9405.github.io/2018/12/20/spring-mvc-framework.html

## gradle 키워드

- build.gradle 기본 구조
  ```
  plugins {
    id 'java-library'
  }

  repositories {
    mavenCentral()
  }

  dependencies {
    implementation 'org.hibernate:hibernate-core:3.6.7.Final'
    api 'com.google.guava:guava:23.0'
    testImplementation 'junit:junit:4.+'
  }
  ```
    - plugins
      - 애플리케이션 패키징과 의존성 관리, 버전 관리 가능, 선언 시 포함되는(필요한) 라이브러리 추가
    - repositories
      - 종속성 조회해올 저장소 명시 (mavenCentral 저장소에서 모든 종속성을 조회해옴)
    - dependencies
	    - 애플리케이션 동작에 필요한 모듈 의존성 추가  
    <br>
   
- dependencies 키워드 종류
  - api
    - compile에서 변경
    - 애플리케이션 동작에 필요한 의존성 선언
    - 의존 라이브러리 수정 시 본 모듈에 의존하는 모듈까지 모두 재빌드
    - 본 모듈에 의존하는 모듈은 해당 모듈의 API 사용 가능
    - A(api) <- B <- C 일 때, C 에서 A 를 접근할 수 있음
    - A 수정시 직접 의존하는 B 와 간접 의존하는 C 모두 재빌드
  - implementation
    - 애플리케이션 동작에 필요한 의존성 선언
    - 의존 라이브러리 수정 시 본 모듈까지만 재빌드
    - 본 모듈에 의존하는 모듈은 해당 모듈의 API 사용 불가 (사용자에게 모듈 노출 방지)
    - A(implementation) <- B <- C 일 때, C 에서 A 를 접근할 수 없음
    - A 수정시 직접 의존하는 B 까지만 재빌드
  - runtime(**deprecated**)
    - 실행 시에 필요한 의존성 선언
    - 대신 runtimeOnly 사용
  - runtimeOnly
    - 실행 시에만 필요한 의존성 선언
  - annotationProcessor
    - 어노테이션 관련 의존성 선언
    - annotation processor : 소스에 주석을 달면 코드를 생성할 수 있는 매커니즘 ex. DI, Lombok
  - compileOnly
    - 컴파일 시에만 필요한 의존성, 빌드 후 빌드 결과물에는 미반영
    - 실행 중에는 필요 없는 모듈일 때 활용
  - testCompile(**deprecated**)
    - 테스트 코드 컴파일 시 필요한 의존성 선언
    - 대신 testImplementation 사용
  - testImplementation
    - 테스트코드 동작 시 필요한 의존성 선언
  - testCompileOnly
    - 테스트코드 컴파일 시에만 필요한 의존성 선언
  - testRuntimeOnly
    - 테스트코드 실행 시에만 필요한 의존성 선언
  - classpath
    - 클래스 경로에 라이브러리 직접 추가하여 의존성 선언
    - 애플리케이션 동작 시 필요  
  <br>
  
- dependencies 작성 방법
  ```
  dependencies {
    implementation '<group-id>' : '<artifact-id>' : '<version>’ // 외부 모듈 의존성
    implementation project(‘:프로젝트명’) // 다른 프로젝트 의존성
    implementation files(‘파일경로’) // 파일 의존성
    implementation('org.hibernate:hibernate:3.1') { // 일부 모듈 의존성 제외
      exclude module: 'cglib' //by artifact name
      exclude group: 'org.jmock' //by group
      exclude group: 'org.unwanted', module: 'iAmBuggy' //by name and group
    }
  }
  ```


## `@RequiredContructor`, `@Data`

## `equals`, `hashCode` 재정의

## slf4j 로깅 종류

- Logging이란?
  - 정보 제공을 위한 기록인 Log를 생성하는 작업
<br>

- 대표적인 Logging 방법과 특징
  - System.out.println();
    - 가장 간단한 방법            
    - 출력하는 로그의 양이나 로그 수준 지정 불가능            
    - 로그 파일 저장 과정 불편            
    - 낮은 성능            
  - java.util.logging        
	  - JDK 1.4부터 포함된 표준 로깅 API            
    - 기본 기능만 필요할 경우 외부 라이브러리 추가 없이 로깅 가능 (기능 부족)            
    - 다른 라이브러리에 비해 느린 속도            
    - 로그 레벨 : SEVERE(심각), WARNING(경고), INFO(정보)           
  - Apache Commons Logging
    -  구성 추상 클래스            
    -  Log : 기본 로거 인터페이스                
    -   LogFactory : Log 객체를 생성하는 추상 클래스                
    -   로그 레벨 : FATAL, ERROR, WARN, INFO, DEBUG, TRACE            
  - Log4j (Log4j2)
	  - 구성 컴포넌트
	  - logger : 로그 기록
	  - appender : 로그 기록 위치 지정(파일, 콘솔, DB ...)
	  - layout : 로그 스타일 지정
	  - 여러 종류의 appender 지원
	  - 최적화된 퍼포먼스
	  - 로그 레벨 : ALL, TRACE, DEBUG, INFO, WARN, ERROR, FATAL            
  -   Logback
	  - Log4j의 단점 개선
	  - 스트링 부트 기본 로그 객체
	  - 여러 종류의 appender 지원
	  - 로그 레벨 : ERROR, WARN, INFO, DEBUG, TRACE
<br>

- SLF4J (Simple Logging Facade for Java)
	- 다양한 로깅 프레임워크에 대한 추상화 (인터페이스)
	- 로깅 프레임워크를 통일된 방식으로 사용할 수 있는 기능 제공
	- slf4j-api.jar 의존성을 추가하여 API 활성화
		- 인터페이스이기 때문에 단독으로 사용 불가능
		- 반드시 로깅 프레임워크 구현체 필요
	- 사용하는 로깅 프레임워크 종류에 따라 각각 바인딩용 jar 의존성 추가하여 SLF4J 인터페이스와 구현체 연결
![slf4j]('')
	- slf4j 사용 시, 어떤 로깅 라이브러리를 사용해도 같은 방법으로 로깅 가능
	- 추후 로깅 라이브러리 변경 시 애플리케이션 코드 변경 작업 불필요 (jar만 교체)

## jar 실행 방법, 옵션
- gradle
- maven

## `@RequestMapping` 하위 어노테이션
### 의문을 가진 계기

`@RequestMapping`의 하위 어노테이션을 보고 언젠가 한번 HTTP Method 중에서 `PUT`과 `DELETE`가 보안에 취약하다는 내용을 본적이 있고 그 진위여부 혹은 이유에 대해 의문이 생겼다.
- https://okky.kr/article/395308?note=1244284
- https://hello-nanam.tistory.com/75

예를들어 위와 같은 글들

### 결론
보안상 PUT/DELETE를 사용하지 않는다는 말은 거짓이다.

Java 언어로 된 Tomcat 서버가 있듯이 C++ 언어로 된 Micorsoft Internet Information Service (IIS)라는 서버가 있다. 그리고 WebDAV라는 프로토콜이 있는데 웹을 읽고 쓰기가 가능한 매개체로 만들어주는 HTTP의 확장된 프로토콜이다. IIS와 WebDAV 프로토콜을 같이 사용하게 될 때, 다수의 취약점이 발견되었는데 이 취약점들을 피하기 위해 임시방편으로 PUT과 DELETE 사용을 하지않게 한 것이 PUT, DELETE를 사용하면 보안상 취약하다라고 와전된 것이다.

### WebDAV(Web-based Distributed Authoring and Versioning) 프로토콜
- 웹 서버를 표준 네트워크 드라이브로 나타낼 수 있는 HTTP 프로토콜의 확장
- FTP와 곧잘 비교되며, FTP보다는 느리지만 범용성이 높고 보안성도 좋다는 장점이 있다

### 해당 취약점을 악용한 예
- https://johyungen.tistory.com/350

### Reference
 - https://blog.naver.com/lascomco/221207930956
 - https://blog.tophoon.com/2019/01/20/rest-api.html
 - http://haah.kr/2017/05/22/rest-the-beginning/

## Redirect, Forward 차이

## DevTools JVM 클래스로더

---

### Reference
- https://madplay.github.io/post/why-constructor-injection-is-better-than-field-injection
- https://jhleed.tistory.com/126
- https://seongmun-hong.github.io/springboot/Spring-boot-EnableAutoConfiguration
- [https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph)
- [https://docs.gradle.org/current/userguide/dependency_management.html](https://docs.gradle.org/current/userguide/dependency_management.html)
- [https://hack-jam.tistory.com/13](https://hack-jam.tistory.com/13)
- [https://kwonnam.pe.kr/wiki/gradle/dependencies#dokuwiki__top](https://kwonnam.pe.kr/wiki/gradle/dependencies#dokuwiki__top)
- [http://www.devkuma.com/books/pages/1074](http://www.devkuma.com/books/pages/1074)
- [http://www.slf4j.org/](http://www.slf4j.org/)
- [https://enai.tistory.com/35](https://enai.tistory.com/35)
- [https://www.fwantastic.com/2019/12/javautillogging-vs-log4j-vs-slf4j.html](https://www.fwantastic.com/2019/12/javautillogging-vs-log4j-vs-slf4j.html)
- [https://commons.apache.org/proper/commons-logging/](https://commons.apache.org/proper/commons-logging/)
