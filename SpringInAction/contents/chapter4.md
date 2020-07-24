# CHAPTER 4. 스프링 시큐리티

## :heavy_check_mark: hasRole 과 hasAuthority 의 차이 및 사용법
### hasRole

### hasAuthority
 
#### :link: Reference
- []()


## :heavy_check_mark: 암호화 알고리즘의 종류 및 장단점 
### 암호화의 종류
1. 대칭형 암호 (비밀키 암호)
    - 암호화 키와 복호화 키가 같다
2. 비대칭형 암호 (공개키 암호)
    - 암호화키와 복호화키다 다르다
3. 단방향 암호
    - 암호화는 가능하지만 복호화는 불가능하다

### 단방향 암호
패스워드에 사용되는 암호화는 단방향 암호가 많다. 책에서 소개된 Bcrypt나 PBKDF2도 단방향 암호. 

패스워드가 복호화 될 수 있다는 것 자체가 이미 노출될 가능성을 안고 있다. 따라서 원본 데이터로 복원 성질이 애초에 존재하지 않는 해시함수 비밀번호를 저장하는데 사용된다.


### Password network 전송 시 보안

위에서 이야기한 것들이 수신한 패스워드 저장 측면에서의 보안이고, 클라이언트 부터 서버까지 데이터 전송에 대한 보안도 따로 필요할 것이다. 이때 알아야하는 것이 SSL/TLS 기반의 https이다.

그리고 여기서 RSA등의 비대칭키 암호화 및 대칭키 암호화 기술을 이용한 PKI 기반으로 클라이언트/서버 사이에 전송되는 데이터가 암/복호화 된다.

#### :link: Reference
- [_JSPark - 암호화 알고리즘 종류](https://jusungpark.tistory.com/34)
- [OKKY - (어느 암호화와 질문 관련 답변)](https://okky.kr/article/524872?note=1567285)


## :heavy_check_mark: 단방향, 양방향 암호화 
### 동일한 raw String password 에 대한 양방향 암호화 결과값은 항상 동일한가?

#### :link: Reference
- []()


## :heavy_check_mark: LDAP 이란 
### LDAP 의 개념
TCP/IP 위에서 디렉터리 서비스를 조회하고 수정하는 응용 프로토콜이다.

> 디렉토리 서비스는 디렉토리 서비스 안에 관리되고 있는, 이름이 있는 객체에 대한 정보를 가지고 있는 데이터베이스를 말하는 디렉토리 그 자체와 혼동하면 안 된다. X.500 분배 디렉토리 서비스 모델의 경우, 하나 이상의 이름 공간 (객체의 트리)들이 디렉토리 서비스를 만드는 데에 쓰인다. 디렉토리 서비스는 하나 이상의 디렉토리 이름 공간 속에 포함된 자료에 접근할 수 있는 인터페이스를 제공한다. 디렉토리 서비스 인터페이스는 중심/공통 권한으로 동작하며 디렉토리 데이터를 관리하는 시스템 자원을 안전하게 인증할 수 있게 해 준다.

디렉터리는 논리, 계급 방식 속에서 조직화된, 비슷한 특성을 가진 객체들의 모임이다. 가장 일반적인 예로는 전화 번호부(telephone directory)가 있는데 가나다 순의 일련의 이름을 가지고 있고, 이름마다 전화 번호와 주소가 포함되어 있다. 이러한 기본 설계 때문에 LDAP는 인증을 위한 다른 서비스에 의해 자주 사용된다.

### *.ldif 설정 파일 사용법
- CN (Common Name): KilDong Hong, SaRang Lee 와 같은 일반적인 이름들 
- SN (SirName): 우리나라 성에 해당한다
- OU (Organiztion Unit): 그룹에 해당  
- DC (Domain Component): 도메인에 대한 요소
- DN (Distinguished Name): 위의 엔트리 및 기타 여러가지 엔트리들이 모여 특정한 한 사용자(혹은 물체)를 구분할 수 있는 고유의 이름


### LDAP의 장단점 
- 장점
    - LDAP을 사용하면 응용 프로그램별 데이터베이스를 대체하여 정보를 통합할 수 있다(관리할 개별 데이터베이스 수가 감소)
    - LDAP을 사용하면 여러 이름 지정 서비스에서 데이터를 공유할 수 있다
    - LDAP은 중앙 데이터 저장소를 제공한다
    - LDAP을 사용하면 마스터와 복제본 간에 데이터를 더 자주 동기화할 수 있다
    - LDAP은 다중 플랫폼 및 다중 공급업체와 호환된다

- 제한사항
    - LDAP 서버는 현재 자체 클라이언트가 될 수 없다
    - LDAP 이름 지정 서비스 설정과 관리는 더 복잡하며 신중한 계획이 필요하다
    - 동일한 클라이언트 시스템에서 NIS 클라이언트와 고유 LDAP 클라이언트를 함께 사용할 수 없다
#### :link: Reference
- https://medium.com/happyprogrammer-in-jeju/ldap-%ED%94%84%ED%86%A0%ED%86%A0%EC%BD%9C-%EB%A7%9B%EB%B3%B4%EA%B8%B0-15b53c6a6f26
- https://ldap.or.kr/ldap-%EC%9D%B4%EB%9E%80/
- https://osankkk.tistory.com/entry/LDAP-%EC%A0%95%EB%A6%AC
- https://docs.oracle.com/cd/E37933_01/html/E36690/overview-9.html


## :heavy_check_mark: @Bean, @Component 의 차이 

#### :link: Reference
- []()


## :heavy_check_mark: 경로 /** 표시의 의미
### /** 와 /* 의 차이 

#### :link: Reference
- []()


## :heavy_check_mark: 스프링 시큐리티와 인터셉터, 필터
### 스프링 시큐리티와 인터셉터 모두 있을 때 적용되는 순서 

### 인터셉터와 필터의 차이 

#### :link: Reference
- []()


## :heavy_check_mark: Principal, Authentication, @AuthenticationPrincipal 의 개념 및 차이 

#### :link: Reference
- []()


## :heavy_check_mark: SecurityContextHolder 란 

#### :link: Reference
- []()



---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
