# CHAPTER 5. 구성 속성 사용하기
## :heavy_check_mark: 원천속성의 차이 및 우선순위
<!-- (p169) --> 
### Content 1
<!-- JVM 시스템 속성을 .yml에서 할 수 있는지 여부와 그 예시 --> 

### JVM 시스템 속성 사용 예시
 
#### :link: Reference
- []()


## :heavy_check_mark: 명령행 인자로 애플레케이션 실행 방법 
<!-- + 기본적인 속성, 옵션들 정리 --> 
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
