# 엔티티 매핑
JPA가 지원하는 매핑 어노테이션의 4가지 분류
- 객체와 테이블 매핑 : @Entity, @Table
- 기본 키 매핑 : @Id
- 필드와 컬럼 매핑 : @Column
- 연관관계 매핑 : @ManyToOne, @JoinColumn

## 객체와 테이블 매핑

### @Entity
JPA를 사용해서 데이터베이스 테이블과 매핑할 클래스에 붙이는 어노테이션

속성
- name : 엔티티 이름 지정. 기본값은 클래스 이름

주의사항
- 기본 생성자 필수 (JPA는 엔티티 생성 시 기본 생성자 사용)
- final 클래스, enum, interface, inner 클래스에는 사용 불가능
- 저장할 필드에 final 키워드 사용 불가능

### @Table
엔티티 클래스에 붙이는 어노테이션으로, 엔티티와 매핑할 테이블을 지정. 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용

속성
- name : 매핑할 테이블 이름. 기본값은 엔티티 이름
- catalog : catalog 기능이 있는 데이터베이스에서 catalog를 매핑
- schema : shcema 기능이 있는 데이터베이스에서 schema 매핑
- uniqueConstraints(DDL) : DDL 생성 시, 유니크 제약조건 생성. 2개 이상의 복합 유니크 조건 가능. 스키마 자동 생성 기능 사용 시에만 동작.

### 데이터베이스 스키마 자동 생성
JPA 지원 기능. 클래스의 매핑 정보를 보고 어떤 테이블에 어떤 컬럼을 사용할지 확인해서 스키마 생성.

기능 설정 방법(resousrce/application.yml 또는 application.properties)

yaml
```yml
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```
property
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
- hibernate.ddl-auto : 애플리케이션 실행 시점에 테이블 자동 생성 여부 설정 가능. 옵션 종류는 아래 확인
- spring.jpa.show-sql : 콘솔에 실행되는 테이블 생성 DDL 출력

**hibernate.ddl-auto 옵션**
- create : 기존 테이블 삭제하고 새로 생성 (DROP + CREATE)
- create-drop : create 속성에 추가로 애플리케이션 종료 시 생성한 DDL을 제거 (DROP + CREATE + DROP)
- update : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경 사항만 수정
- validat : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 차이가 있으면 경고 후 애플리케이션 실행 중지.(DDL 수정 없음)
- none : 자동 생성 기능을 사용하지 않으려면 ddl-auto 속성을 제거하거나 유효하지 않은 옵션 입력. (none은 유효하지 않은 옵션)

테이블을 자동 생성해주기 때문에 개발 시점에는 편리하지만 운영 환경에서 사용할정도로 완벽하지 않기 때문에 개발 시 참고용으로만 사용<br />
**개발 환경에 따른 추천 전략**
- 개발 초기 단계는 create 또는 update
- 초기화 상태로 자동화 테스트를 진행하거나 CI 서버에서는 create 또는 create-drop
- 테스트 서버는 update 또는 validate
- 스테이징과 운영 서버는 validate 또는 none

[참고] DDL 생성 시 이름 매핑 전략 변경하는 방법
자바는 관례상 카멜 표기법을 사용하고, 데이터베이스느 관례상 언더바를 사용<br />
1. @Column(name) 속성을 명시적으로 사용해서 컬럼명 지정하는 방법
2. 이름 매핑 전략을 변경하는 방법
    - 하이버네이트의 ```hibernate.ejb.naming_strategy``` 프로퍼티 속성 사용
    - 전략 직접 구현도 가능하고, 다음 전략 사용 시 자바의 카멜 표기법을 데이터베이스에서 언더바 컬럼명으로 매핑
      ```
      spring.jpa.hibernate.naming.strategy=org.hibernate.cfg.ImprovedNamingStrategy
      ```
단, 스프링 부트는 따로 지정하지 않아도 기본적으로 카멜 케이스 <-> 언더바 변환 전략

## 기본 키 매핑
회원의 기본 키를 애플리케이션에서 할당하는 방법(직접 할당)
```JAVA
@Entity
public clas Member {

  @Id
  @Column(name = "ID")
  private String id;
}
```

데이터베이스가 자동 생성해주는 값을 기본 키로 사용하는 방법도 가능. DBMS마다 지원하는 방식이 다르기 때문에 JPA는 다양한 방법 지원<br />
JPA가 제공하는 데이터베이스 기본 키 생성 전략
- 직접 할당 : 기본 키를 애플리케이션에서 직접 할당
- 자동 생성 : 대리 키 사용 방식
  - IDENTITY : 기본 키 생성을 데이터베이스에 위임
  - SEQUENCE : 데이터베이스 시퀀스를 사용해 기본 키 할당
  - TABLE : 키 생성 테이블 사용

### 직접 할당
@Id로 매핑하며 적용 가능한 자바 타입은 다음 목록
- 기본 타입
- Wrapper 타입
- String
- java.util.Date
- java.sql.Date
- java.math.BigDecimal
- java.math.BigInteger

.```em.persist()```호출 전에 애플리케이션에서 직접 식별자 값 할당

### 자동 생성
자동 생성 전략 적용 방법. @GeneratedValue 어노테이션 추가
```JAVA
@Entity
public clas Member {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private String id;
}
```

#### IDENTITY
기본 키 생성을 데이터베이스에 위임하는 전략.<br />
MySQL, PostgreSQL, SQL Server, DB2에서 사용(기본 키 자동 생성 기능을 지원하는 데이터베이스)
```JAVA
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

전략 사용 시 다음과 같이 테이블 생성(MySQL)
```SQL
CREATE TABLE BOARD {
  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  DATA VARCHAR(255)
};

INSERT INTO BOARD(DATA) VALUES('A');
INSERT INTO BOARD(DATA) VALUES('B');
```
- AUTO_INCREMENT 설정 시 ```ID``` 값을 지정하지 않아도 데이터베이스가 알아서 자동 생성

**주의할 점**은, 엔티티가 영속 상태가 되려면 식별자가 반드시 필요한데 IDENTITY 전략은 엔티티를 데이터베이스에 저장해야 식별자 값 획득이 가능하기 때문에 ```em.persist()``` 호출(엔티티 영속상태로 전환) 즉시 INSERT SQL이 데이터베이스에 전달되어 트랜잭션을 지원하는 쓰기 지연 동작 불가능<br />
엔티티를 일단 먼저 데이터베이스에 저장한 후, 식별자를 조회해서 엔티티의 식별자에 할당(선저장 후조회)

#### SEQUENCE
데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트. SEQUENCE 전략은 이 시퀀스를 사용해 기본 키 생성.<br />
Oracle, PostgreSQL, DB2, H2에서 사용(시퀀스 기능 지원)
```JAVA
@Entity
@SequenceGenerator(
    name = "BOARD_SEQ_GENERATOR",
    sequenceName = "BOARD_SEQ",
    initialValeu = 1,
    allocationSize = 1)
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
                  generator = "BOARD_SEQ_GENERATOR")
  private Long id;

}
```
- @SequenceGenerator로 엔티티에 사용할 데이터베이스 시퀀스 매핑("BOARD_SEQ_GENERATOR"라는 시퀀스 생성기 등록)
- JPA는 BOARD_SEQ를 데이터베이스의 시퀀스 이름과 매핑
- @GeneratedValue에 시퀀스 생성기 등록

전략 사용 시 다음과 같이 테이블 생성(H2)
```SQL
CREATE TABLE BOARD {
  ID BIGINT NOT NULL PRIMARY KEY,
  DATA VARCHAR(255)
};

CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1;
```

시퀀스 전략은 ```em.persist()``` 호출 시 데이터베이스의 SEQUENCE를 통해 식별자를 조회해온 후 엔티티에 할당해서 영속성 컨텍스트에 추가. 이후 커밋할 때 엔티티를 데이터베이스에 저장(선부분조회 후저장)

@SequenceGenerator 속성
- name : 식별자 생성기 이름(필수)
- sequenceName : 데이터베이스에 등록된 시퀀스 이름(기본값은 hibernate_sequence)
- initialValue : DDL 생성 시에만 사용. 시퀀스 DDL 생성 시 처음 시작하는 수(기본값은 1)
- allocationSize : 시퀀스 한 번 호출마다 증가하는 수(기본값은 50)
- catalog, schema : 데이터베이스 catalog, schema 이름

** 전략 최적화 방법 찾아보기

#### TABLE
키 생성 전용 테이블을 하나 만들고, 이름과 값으로 사용할 컬럼을 만들어 데이터베이스의 시퀀스를 흉내내는 전략<br />
모든 DBMS에서 사용 가능
```JAVA
@Entity
@TableGenerator(
    name = "BOARD_SEQ_GENERATOR",
    table = "MY_SEQUENCES",
    pkColumnValue = "BOARD_SEQ",
    allocationSize = 1)
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE,
                  generator = "BOARD_SEQ_GENERATOR")
  private Long id;

}
```
- sequence_name이라는 컬럼에 BOARD_SEQ 컬럼값 생성

전략 사용 시 다음과 같이 테이블 생성(H2)
```SQL
CREATE TABLE MY_SEQUENCES {
  sequence_name varchar(255) not null,
  next_val bigint,
  primary key (sequence_name)
};

CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1;
```

생성 결과

|sequence_name|next_val|
|---|---|
|BOARD_SEQ|2|
|MEMBER_SEQ|10|
|...|...|

@TableGenerator 속성
- name : 식별자 생성기 이름(필수)
- talbe : 키 생성 테이블명(기본값 hibernate_sequence)
- pkColumnName : 시퀀스 컬럼명(기본값 sequence_name)
- valueColumnName : 시퀀스 값 컬럼명(기본값 next_val)
- pkColumnValue : 키로 사용할 값 이름(기본값 엔티티 이름)
- initialValue : 초기 값, 마지막으로 생성된 값 기준(기본값 0)
- allocationSize : 시퀀스 한 번 호출에 증가하는 수(기본값 50)
- catalog, schema : 데이터베이스 catalog, schema 이름
- uniqueConstraints : 유니크 제약 조건 지정

#### AUTO
데이터베이스 종류만큼 기본 키 생성 방법은 다양하기 때문에 AUTO 옵션으로 각 DBMS마다 위 세 가지 전략 중 알아서 적절한 전략 선택<br />
장점은 데이터베이스 변경해도 코드를 수정할 필요가 없다는 점.

#### 기본 키 선택 전략
1. null 값 미허용
2. 중복 미허용
3. 불변

위 조건에 따라, 비즈니스에 의미가 있는 키(주민등록번호, 이메일, 전화번호) 또는 대리 키(시퀀스, auto_increment)를 사용<br />
일반적으로 대리 키를 권장

## 필드와 컬럼 매핑
#### @Column
객체 필드를 테이블 컬럼에 매핑

속성

|속성|기능|기본값|
|---|---|---|
|name|필드와 매핑할 테이블 컬럼명|객체의 필드명|
|insertable(거의 사용 X)|엔티티 저장 시 해당 필드도 같이 저장. false는 읽기 전용 시 사용|true|
|updatable(거의 사용 X)|엔티티 수정 시 해당 필드도 같이 수정. false는 읽기 전용 시 사용|true|
|table(거의 사용 X)|하나의 엔티티를 두 개 이상 테이블에 매핑 시 사용|현재 클래스가 매핑된 테이블|
|nullable|DDL 옵션<br />null값 허용 여부 설정. false는 DDL 생성 시 not null 조건 추가|true|
|unique|DDL 옵션<br />unique 설정. true는 DDL 생성 시 unique 조건 추가||
|columnDefinition|데이터베이스 컬럼 정보 부여|필드의 자바 타입과 방언 정보를 사용해 적절한 컬럼 타입 생성|
|length|문자 길이 제약조건, String 타입에만 사용|255|
|precision, scale|BigDecimal/BigInteger 타입에서 사용. 각각 소수점 포함 전체 자릿수와 소수 자릿수 설정|percision=19, scale=2|

columnDefinition 예시
```JAVA
@Column(columnDefinition = "varchar(100) default 'EMPTY'")
private String data;
```

DDL 관련 속성들은 단지 DDL 자동 생성 시에만 사용되고, 애플리케이션 실행 로직에는 영향 없음

#### @Enumerated
자바의 enum 타입 매핑 시 사용

속성

|속성|기능|기본값|
|---|---|---|
|value|EnumType.ORDINAL : enum 순서를 데이터베이스에 저장<br />EnumType.STRING : enum 이름을 데이터베이스에 저장|EnumType.ORDINAL|

예시
```JAVA
enum RoleType {
  ADMIN, USER
}
```
```
@Enumerated(EnumType.STRING)
private RoleType roleType;
```

#### @Temporal
날짜 타입 매핑 시 사용
==> JAVA 8부터 LocalDateTime 나와서 변경 필요

#### @Lob
데이터베이스 BLOB, CLOB 타입과 매핑. 매핑 필드 타입이 문자면 CLOB, 그 외는 BLOB 매핑
- CLOB : String, char[], java.sql.CLOB
- BLOB : byte[], java.sql.BLOB

#### @Transient
데이터베이스에 저장하지 않고 조회하지 않는 필드. 객체에 임시로 값을 보관하고 싶을 때 사용

#### @Access
JPA가 엔티티 데이터에 접근하는 방식 지정
- 필드 접근 : AccessType.FIELD
  - 필드에 직접 접근
  - 필드 접근 권한이 private이어도 접근 가능
  - 기존에 주로 사용하던 방법
- 프로퍼티 접근 : AccessType.PROPERTY
   - Getter같은 접근자 사용

예시
```JAVA
@Entity
public class Member {

  @Id
  private String id;    // @Access(AccessType.FILED)

  @Transient
  private String firstName;

  @Transient
  private String lastName;

  @Access(AccessType.PROPERTY)
  public String getFullName() {
    return firstName + lastName;
  }
}
```
- 프로퍼티 접근 방식을 사용해서 Member 테이블의 FULLNAME 컬럼에 firstName + lastName 결과 저장

@Access 미사용 시 @Id 위치를 기준으로 접근 방식 설정

### Reference
- 김영한, 『자바 ORM 표준 JPA 프로그래밍』, 에이콘(2015)
- [Springboot jpa & Hibernate Naming Strategy(네이밍 전략)](https://mycup.tistory.com/237)
