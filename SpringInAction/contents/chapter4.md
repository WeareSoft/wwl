# CHAPTER 4. 스프링 시큐리티

## :heavy_check_mark: hasRole 과 hasAuthority 의 차이 및 사용법

### 버전별 hasRole 과 hasAuthority 차이 
- 모두 특정 권한을 가지는 사용자만 접근하도록 설정하는 것이다. 
- in Spring security 3 
    - `@PreAuthorize("hasRole('ROLE_XYZ')")` == `@PreAuthorize("hasAuthority('ROLE_XYZ')")`
- in Spring security 4 
    - `@PreAuthorize("hasRole('XYZ')")` == `@PreAuthorize("hasAuthority('ROLE_XYZ')")`
    - 자동으로 ROLE_ 접두사를 붙여준다.

 
#### :link: Reference
- [difference-between-role-and-grantedauthority-in-spring-security](https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security)


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
TCP/IP 위에서 디렉토리 서비스를 조회하고 수정하는 응용 프로토콜이다.
- 디렉토리
    - 파일 시스템(X), 데이터의 집합(O)
- 디렉토리 서비스
    - 디렉토리를 관리하고 리소스에 접근하는 응용 프로그램
    - 데이터베이스와 유사하지만 복잡한 트랜잭션이나 롤백은 지원하지 않는다
    - 데이터베이스보다 읽기에 더 최적화 되어있다
- LDAP
    - 디렉토리 서비스와 디렉토리 사이의 통신을 위한 프로토콜

### *.ldif 설정 파일 사용법
 LDAP Data Interchange Format의 약자
- CN (Common Name): KilDong Hong, SaRang Lee 와 같은 일반적인 이름들 
- SN (SirName): 우리나라 성에 해당한다
- OU (Organiztion Unit): 그룹에 해당  
- DC (Domain Component): 도메인에 대한 요소
- DN (Distinguished Name): 위의 엔트리 및 기타 여러가지 엔트리들이 모여 특정한 한 사용자(혹은 물체)를 구분할 수 있는 고유의 이름
- 참고
    - https://docs.oracle.com/cd/E19957-01/805-5964/6j5kig7p0/index.html
    - https://osankkk.tistory.com/entry/LDAP-%EC%A0%95%EB%A6%AC


### LDAP의 장단점
- 중앙집중적이다
- 데이터베이스와 유사하지만 복잡한 트랜잭션이나 롤백은 지원하지 않는다
    - 하지만 가용성과 신뢰성을 개선하기 위해 쉽게 복제 가능
- 데이터베이스보다 읽기에 더 최적화 되어있다
    - 읽기에 대해서는 더 빠른 응답속도

#### :link: Reference
- https://medium.com/happyprogrammer-in-jeju/ldap-%ED%94%84%ED%86%A0%ED%86%A0%EC%BD%9C-%EB%A7%9B%EB%B3%B4%EA%B8%B0-15b53c6a6f26
- https://ldap.or.kr/ldap-%EC%9D%B4%EB%9E%80/
- https://osankkk.tistory.com/entry/LDAP-%EC%A0%95%EB%A6%AC
- https://docs.oracle.com/cd/E37933_01/html/E36690/overview-9.html
- https://wiki.gentoo.org/wiki/Centralized_authentication_using_OpenLDAP/ko
- https://docs.oracle.com/cd/E19957-01/805-5964/6j5kig7p0/index.html
- https://osankkk.tistory.com/entry/LDAP-%EC%A0%95%EB%A6%AC


## :heavy_check_mark: @Bean, @Component 의 차이 
- `@Bean`
  - 개발자가 컨트롤이 불가능한 외부 라이브러리들을 Bean으로 등록하고 싶은 경우에 사용
  - ObjectMapper의 경우 ObjectMapper Class에 `@Component`를 선언할 수 없으니 ObjectMapper의 인스턴스를 생성하는 메서드를 만들고 해당 메서드에 `@Bean`을 선언하여 Bean으로 등록한다.
- `@Component`
  - 개발자가 직접 컨트롤이 가능한 Class를 Bean으로 등록하고 싶은 경우에 사용
- 개발자가 생성한 Class에 `@Bean` 선언이 가능할까?
  - 불가능하다.
  - `@Bean`과 `@Component`는 각자 선언할 수 있는 타입이 정해져있어 해당 용도외에는 컴파일 에러를 발생시킨다.
    - `@Bean`
      ```java
      @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
      @Retention(RetentionPolicy.RUNTIME)
      ...
      public @interface Bean {
        ...
      }
      ```
    - `@Component`
      ```java
      @Target(ElementType.TYPE)
      @Retention(RetentionPolicy.RUNTIME)
      ...
      public @interface Component {
        ...
      }
      ```
    - `ElementType`
      ```java
      public enum ElementType {
        /** Class, interface (including annotation type), or enum declaration */
        TYPE,

        /** Field declaration (includes enum constants) */
        FIELD,

        /** Method declaration */
        METHOD,

        /** Formal parameter declaration */
        PARAMETER,

        /** Constructor declaration */
        CONSTRUCTOR,

        /** Local variable declaration */
        LOCAL_VARIABLE,

        /** Annotation type declaration */
        ANNOTATION_TYPE,

        /** Package declaration */
        PACKAGE,

        /**
        * Type parameter declaration
        *
        * @since 1.8
        */
        TYPE_PARAMETER,

        /**
        * Use of a type
        *
        * @since 1.8
        */
        TYPE_USE,

        /**
        * Module declaration.
        *
        * @since 9
        */
        MODULE
      }
      ```
  #### :link: Reference
  - [@Bean vs @Component](https://jojoldu.tistory.com/27)

  ## :heavy_check_mark: 경로 /** 표시의 의미
  ### /** 와 /* 의 차이 

  - Ant Pattern
  - Ant Pattern의 종류
    - ? : 1개의 문자와 매칭
    - \* : 0개 이상의 문자와 매칭
    - ** : 0개 이상의 디렉토리와 파일 매칭
    ```
    antPathMatcher.match("/static/**", "/static/images/user/123.jpg"); => true
    antPathMatcher.match("/static/**", "/static/images/");             => true
    antPathMatcher.match("/static/**", "/static");                     => true
    antPathMatcher.match("/static/**", "/stat/images");                => false
    
    antPathMatcher.match("/static/*", "/static/123.jpg");              => true
    antPathMatcher.match("/static/*", "/static/images/123.jpg");       => false
    antPathMatcher.match("/static*/*", "/static/123.jpg");             => true
    antPathMatcher.match("/static*/*", "/staticABC/123.jpg");          => true
    
    antPathMatcher.match("/static*/*", "/staticABC/images/123.jpg");   => false
    antPathMatcher.match("/static*/**", "/staticABC/images/123.jpg");  => true
    
    antPathMatcher.match("/static?/**", "/staticA/images/123.jpg");    => true
    antPathMatcher.match("/static?/**", "/static/images/");            => false
    antPathMatcher.match("/static?/*", "/staticB/123.jpg");            => true
    antPathMatcher.match("/static?/???.jpg", "/staticB/123.jpg");      => true
    antPathMatcher.match("/static?/???.jpg", "/staticB/1234.jpg");     => false
    ```

  #### :link: Reference
  - [Ant style pattern 정리](https://lng1982.tistory.com/169)


  ## :heavy_check_mark: 스프링 시큐리티와 인터셉터, 필터
  ### 스프링 시큐리티와 인터셉터 모두 있을 때 적용되는 순서 

  - 스프링 시큐리티는 필터 기반이기 때문에 시큐리티가 우선 적용
  ![라이프사이클](../images/springMVClifecycle.png)
  - 요청이 들어오면 필터를 거친 후, 인터셉터 처리
  - 시큐리티를 적용하지 않을 경우 세션과 인터셉터를 이용해 권한 관리와 같은 로직을 직접 구현해야 했지만 시큐리티 적용 시 설정으로 적용 가능 

  ### 인터셉터와 필터의 차이 

  - 실행 시점
    - 요청이 들어오면 필터 -> 인터셉터
    - 응답 시 인터셉터 -> 필터
  - 등록 위치
    - 필터는 웹 애플리케이션에 등록(web.xml)
      - 인코딩 변환, XSS 방어 처리 등
      - 스프링과 무관한 자원에 대해 동작 가능
      - 등록 위치 특성상 애플리케이션에 전역적으로 적용할 기능 구현
    - 인터셉터는 스프링의 context에 등록
      - 스프링 Dispatcher servlet이 컨트롤러 호출 전, 후에 동작
      - 스프링의 모든 빈 객체에 접근 가능
  - 인터페이스
    - 필터
      ```
      public interface Filter {
        void doFilter(ServletRequest request, ServletResponse response, FilterChain chain);
      }
      ```
    - 인터셉터
      ```
      public interface HandlerInterceptor {
        boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler);
        void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav);
        void afterCompletion(HttpServletRequest request, HttpServeletResponse response, Object handler, Exception ex);
      }
    ```

#### :link: Reference
- [Authentication/Authorization 기능 구현(1) - interceptor vs spring security](https://soon-devblog.tistory.com/4?category=1026232)
- [SpringMVC Request Life Cycle에 대해 - PART 1](https://nesoy.github.io/articles/2019-02/Spring-request-lifecycle-part-1)
- [(Spring)Filter와 Interceptor의 차이](https://supawer0728.github.io/2018/04/04/spring-filter-interceptor/)
- [[Spring] Filter, Interceptor, AOP 차이 및 정리](https://goddaehee.tistory.com/154)


## :heavy_check_mark: Principal, Authentication, @AuthenticationPrincipal 의 개념 및 차이 
- authentication(인증) VS authorization(인가)
    - 인증 절차를 거친 후 인가 절차를 진행 (e.g. 로그인)
    - 인증: 해당 사용자가 본인이 맞는지 확인하는 절차
    - 인가: 인증된 사용자가 요청된 자원에 접근 가능한지를 확인하는 절차
- 스프링 시큐리티는 Credential 기반(아이디와 비밀번호 이용)의 인증 방식을 사용한다.
    - Principal: 아이디
    - Credential: 비밀번호 
- 기본 과정 
    - 아이디와 패스워드 정보를 가지고 실제 가입된 사용자인지 확인 및 인증 절차 수행
    - Principal(아이디), Credential(패스워드) 정보를 Authentication 에 넣기
    - 스프링 시큐리티에서 해당 Authentication을 SecurityContext 에 담기
    - SecurityContext 는 SecurityContextHolder 에 보관
        - `SecurityContextHolder.getContext().setAuthentication()`

### Principal  
- Principal 은 시스템을 사용하려고 하는 사용자, 디바이스 혹은 시스템을 통칭한다.

### @AuthenticationPrincipal
- 해당 annotation을 이용하여 현재 로그인한 사용자 객체를 인자에 주입한다. 
- 로그인한 사용자의 정보를 파라메터로 받고 싶을때 기존에는 다음과 같이 Principal 객체로 받아서 사용한다.
- 하지만 이 객체는 SecurityContextHolder의 Principal과는 다른 객체이다.
- @AuthenticationPrincipal 애노테이션을 사용하면 UserDetailsService에서 Return한 객체를 파라메터로 직접 받아 사용할 수 있다.


### 기타 관련 개념
#### GrantedAuthority
- **의미:** ​​현재 사용자가 가지고 있는 "권한(permission)" 또는 "권리(right)"를 의미한다.
- 이러한 권한은 `getAuthority()` 메서드와 함께 일반적으로 **문자열** 로 표현한다.
- 이 문자열을 통해 권한을 식별하고 사용자가 어디까지 접근할 수 있을지에 대한 권한을 부여할 수 있다.
- Principal 에 주어진 권한으로, GrantedAuthority 객체는 UserDetailsService 에 의해 로드된다.

#### UserDetails 인터페이스
- **역할:** Spring Security 에서 사용자의 정보를 담아두는 역할 **(VO 역할)**
- 이 인터페이스를 구현하게 되면 Spring Security에서 구현한 클래스를 사용자 정보로 인식하고 인증 작업을 한다. 

#### UserDetailsService 인터페이스 
- **역할:** DB에서 유저 정보를 가져오는 역할
    - c.f. 화면에서 사용자가 입력한 로그인 정보를 담고 있는 객체: Authentication
- 해당 인터페이스의 메서드에서 DB의 유저 정보를 가져와서 AuthenticationProvider 인터페이스로 유저 정보를 리턴하면, 그 곳에서 사용자가 입력한 정보와 DB에 있는 유저 정보를 비교한다.
```java
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserAuthDAO userAuthDAO;
 
    @Override // UserDetails 반환 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails user = userAuthDAO.getUserById(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
 
}
```

#### AuthenticationProvider 인터페이스
- **역할:** 화면에서 입력한 로그인 정보와 DB에서 가져온 사용자의 정보를 비교해주는 역할
- 해당 인터페이스에 오버라이드되는 `authenticate()` 메서드는 화면에서 사용자가 입력한 로그인 정보를 담고 있는 Authentication 객체를 가지고 있다. 
- 그리고 DB에서 사용자의 정보를 가져오는 건 UserDetailsService 인터페이스에서 loadUserByUsername() 메서드로 구현했다. 따라서 authenticate() 메서드에서 loadUserByUsernmae() 메서드를 이용해 DB에서 사용자 정보를 가져와서 Authentication 객체에서 화면에서 가져온 로그인 정보와 비교하면 된다. AuthenticationProvider 인터페이스는 인증에 성공하면 인증된 Authentication 객체를 생성하여 리턴하기 때문에 비밀번호, 계정 활성화, 잠금 모든 부분에서 확인이 되었다면 리턴해주도록 하자.

#### role 의미 
- 스프링 시큐리티는 부여된 권한(Granted Authority)을 검사하는 클래스(`RoleVoter`)를 가지고 있는데 이 검사자가 문자열이 `ROLE_`이란 접두사로 시작하는지를 검사한다. 
    - GrantedAuthorities를 반환하는 UserDetails interface를 구현한 (구현체: UserDetailsService)하여 사용한다.
    - 사용자마다 서로 다른 권한을 부여한 UserDetails 를 Security Context 에 넣어 권한을 확인하고 수행한다.

#### :link: Reference
- [spring security 파헤치기](https://sjh836.tistory.com/165)
- [인증 관련 클리스와 처리](https://flyburi.com/584)
- [UserDetailsService, UserDetails 개념](https://to-dy.tistory.com/86)
- [AuthenticationProvider](https://to-dy.tistory.com/87?category=720806)


## :heavy_check_mark: SecurityContextHolder 란 

#### :link: Reference
- []()



---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
