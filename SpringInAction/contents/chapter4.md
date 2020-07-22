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

### *.ldif 설정 파일 사용법

### LDAP의 장단점 

#### :link: Reference
- []()


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
