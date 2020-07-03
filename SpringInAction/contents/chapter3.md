# CHAPTER 3. 데이터로 작업하기

## :heavy_check_mark: Context Root와 Service Root의 개념과 차이

#### :link: Reference
- []()


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
- Pluggable Annotation Processing API
- <https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html>
- <https://www.inflearn.com/course/the-java-code-manipulation/lecture/23435>
#### :link: Reference
- <https://pluu.github.io/blog/android/2015/12/24/annotation-processing-api/>


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
