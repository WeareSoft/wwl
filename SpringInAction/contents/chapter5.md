# CHAPTER 5. 구성 속성 사용하기
## :heavy_check_mark: 원천속성의 차이 및 우선순위
- JVM System Property란?
    - 시스템 프로퍼티는 JVM이 시작할 때 자동으로 설정되는 시스템 속성값
```
"file.separator"	Character that separates components of a file path. This is "/" on UNIX and "\" on Windows.
"java.class.path"	Path used to find directories and JAR archives containing class files. Elements of the class path are separated by a platform-specific character specified in the path.separator property.
"java.home"	Installation directory for Java Runtime Environment (JRE)
"java.vendor"	JRE vendor name
"java.vendor.url"	JRE vendor URL
"java.version"	JRE version number
"line.separator"	Sequence used by operating system to separate lines in text files
"os.arch"	Operating system architecture
"os.name"	Operating system name
"os.version"	Operating system version
"path.separator"	Path separator character used in java.class.path
"user.dir"	User working directory
"user.home"	User home directory
"user.name"	User account name
```

#### System.getenv도 있는데 property랑 뭐가 다르지?
- System.getenv를 통해 환경변수도 받을 수 있다.
- Property랑 환경변수랑 뭐가 다르지?
    - Property는 JVM내에서 사용하고 종료된다.
        - Application에 의존적이다.
        - 따른 Application에서 사용할 수 없다.
    - 환경변수는 OS에 적재된 변수다.
        - System.setenv 메소드가 없다.
        - OS 전체적으로 영향을 미친다.
    - 따라서 JVM에서 초기화하고 종료처리까지 할 목적이면 Property를 사용하는게 올바르다고 볼 수 있다.

#### Spring의 외부 설정 순서에 대해
- <https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/html/spring-boot-features.html#boot-features-external-config>
- ConfigurationPropertiesBindingPostProcessor
- Last Win

#### 다양한 Annotation
- @ConfigurationProperties
    - 설정들을 읽어 Bean으로 변환하는 책임을 가지고 있다.
- @PropertySource
    - 외부 설정파일들을 읽어들일 수 있다.
    - SpringBoot Level에서는 추천하지 않는 방법이다.
- @Value
    - 설정의 값을 주입할 수 있다.

#### :link: Reference
- <https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>
- <https://www.baeldung.com/java-system-get-property-vs-system-getenv>
- <https://mkyong.com/spring-boot/spring-boot-configurationproperties-example/>
- <https://kingbbode.tistory.com/39>

### Content 1
<!-- JVM 시스템 속성을 .yml에서 할 수 있는지 여부와 그 예시 -->
<!-- 질문 자체를 이해못함 -->

### JVM 시스템 속성 사용 예시
#### JVM System Property 간단한 사용방법
- JUnit5에서 사용하는 Condition관련 테스트에서 활용이 가능하다.
- <https://github.com/NESOY/junit5-example/blob/master/src/test/java/conditional/CustumConditionsTest.java>
- 따로 원하는 Property값을 넣을 수 있다.
    - `System.setProperty("wwl.spring.in.action", "2020");`


## :heavy_check_mark: 명령행 인자로 애플리케이션 실행 방법
<!-- + 기본적인 속성, 옵션들 정리 -->
- 사용법
  - 클래스 실행 : ```java [옵션] <기본 클래스> [args...]```
  - jar 실행 : ```java [옵션] -jar <jar 파일> [args...]>```
  - 모듈의 기본 클래스 실행 : ```java [옵션] -m <모듈>[/<기본 클래스>] [args...]```
  - 옵션의 종류
    - -cp or -classpath or --class-path <디렉토리 및 zip/jar 파일의 클래스 검색 경로>
      - 응용 프로그램 클래스 및 자원의 클래스패스 설정
      - 클래스 파일을 검색하기 위한 디렉토리, JAR, ZIP 파일을 : 으로 구분하여 입력
      - 예) ```-cp .:./subdir:./ex.jar```
    - -D<이름>=<값>
      - 시스템 속성을 설정
      - 예) ```-Dspring.profiles.active=dev```
    - -agentlib:<libname>=\[<options>]
      - 원시 에이전트 라이브러리를 로드 (추가 옵션은 -agentlib:jdwp=help)
      - 예) ```-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:1044```

#### :link: Reference
- []()

## :heavy_check_mark: 톰캣의 JDBC 커넥션 풀, `HikariCP`, `Commons DBCP2`의 개념과 차이
<!-- (p171) -->

#### :link: Reference
- []()

## :heavy_check_mark: HTTPS란
<!-- (p173) -->
### 통신 과정
### keystore
### SSL

#### :link: Reference
- []()

## :heavy_check_mark: `logback.xml`과 `logback-spring.xml`의 차이
- 스프링 부트 애플리케이션에서는 logback.xml이나 logback-spring.xml 파일을 프로젝트 클래스패스에 추가하여 로깅 설정 가능
- 로깅 커스터마이징을 할 경우, 스프링 부트 개발팀은 설정 파일명에 -spring 붙이는 것을 권장
  - logback.xml 사용 시 스프링 부트가 로그 초기화를 완전히 제어하지 못할 수 있기 때문
  - logging은 ApplicationContext 생성 전에 초기화되기 때문에 스프링 @Configuration 파일의 @PropertySource에서 로깅 제어 불가능

#### :link: Reference
- [Using Logback with Spring Boot](https://springframework.guru/using-logback-spring-boot/)
- [4.6 Custom Log Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-custom-log-configuration)

---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
