# IoC 컨테이너 1

## IoC란
Inversion of Control. 제어의 역전
의존성 주입(Dependency Injection)이라고도 하며, **어떤 객체가 사용하는 의존 객체를 직접 만들어 사용하는게 아니라 주입 받아 사용**하는 것

의존성 주입을 사용하지 않는다면
```JAVA
public class Example {
  public static void main(String[] args) {
    BookRepository repository = new BookRepository();
    BookService bookService = new BookService(repository);
  }
}
```
위와 같이 개발자가 필요한 객체를 직접 생성해주는 작업이 필요.
IoC를 도입함으로써 필요한 객체 생성을 개발자가 아닌 컨테이너가 해주고, 적절한 곳에 주입해주기 때문에 제어의 역전이라고 표현

#### 내가 생각하는 IoC와 DI
DI를 통해 IoC가 가능한 것. 의존성 주입이라는 개념을 통해 IoC 컨테이너와 같은 것이 사용 가능하고, 객체의 생명 주기 관리나 주입 처리가 가능

### 스프링 IoC 컨테이너
[BeanFactory](https://docs.spring.io/spring-framework/docs/5.0.8.RELEASE/javadoc-api/org/springframework/beans/factory/BeanFactory.html)
- 컨테이너의 최상위 인터페이스
- 가장 핵심적인 인터페이스
- 링크 참조해서 빈 라이프 사이클 확인

ApplicationContext
- 실질적으로 가장 많이 사용하게될 BeanFactory (BeanFactory 상속)
- BeanFactory에 비해 더 다양한 기능을 보유

Bean
- 컨테이너에 보관하는 객체
- 싱글톤으로 관리하고 싶은 객체를 빈으로 만들면 편리
- 빈만 IoC 컨테이너의 관리를 받을 수 있음(라이프 사이클 인터페이스 활용, 의존성 주입)

빈을 보관하는 컨테이너는 빈 생명주기를 관리하고, 적절히 의존성을 주입하는 역할 수행

IoC를 활용하지 않고, 개발자가 직접 의존성을 관리한다면 단위테스트할 때 매우 번거로움

# IoC Container - @Autowired

각 @Component에 맞는 적절한 상세 어노테이션을 달아주는 것이 좋음
- 이유는 특정 컴포넌트(@Service, @Repository)에게만 필요한 내용 적용 가능
- AOP에서 사용하기에도 좋음

### 의존성 주입하려는 타입의 빈이 없는 경우
빈으로 등록한 Service, 빈으로 등록하지 않은 Repository가 있을 때, Repository를 주입하려하면 오류 발생

생성자 주입
```JAVA
public class Service {
  private Repository repository;

  @Autowired
  public Service(Repository repository) {
    this.repository = repository;
  }
}
```
- 생성자 주입을 하게되면, Service 빈 생성 시 주입하려는 Repository가 빈이 아니기 때문에 오류 발생(빈 생성 불가)

세터 주입
```JAVA
public class Service {
  private Repository repository;

  @Autowired
  public setRepository(Repository repository) {
    this.repository = repository;
  }
}
```
- Setter 주입을 하면 Service 생성 시 Service 자체의 인스턴스는 만들 수 있을 것이라 예상되지만 오류 발생(빈 생성 불가)
  - 필드 주입도 마찬가지
- 이유는 @Autowired 어노테이션을 확인하고 Service 빈 생성 시 Repository 의존성 주입을 시도하기 때문
- ```@Autowired(required = false)``` 속성 사용해서 의존성 주입 옵셔널 가능
  - 의존성 주입이 되지 않은 채로 Service 빈 생성

생성자 주입 시에는 ```refquired = false``` 옵션을 사용해도 빈 객체 생성 시 생성자를 통해 주입 시도하는 것이기 때문에 적용 불가능

### @Autowired 동작 원리
@Autowired 어노테이션을 사용하면 BeanPostProcessor라는 라이프 사이클 인터페이스의 구현체인 AutowiredAnnotationBeanPostProcessor에 의해 의존성 주입이 이루어짐

BeanPostProcessor는 빈의 초기화 라이프사이클 이전/이후에 필요한 부가작업을 할 수 있는 메소드를 제공하고있으며, 구현체인 AutowiredAnnotationBeanPostProcessor가 **빈 생성 전에 @Autowired가 붙은 빈의 의존성을 주입**하게 함

### 의존성 주입하려는 타입의 빈이 여러개인 경우
세 가지 방법 추천
- @Primary
- 모든 빈 주입
- @Qualifier 사용해서 원하는 빈 객체 마킹

#### @Primary
```JAVA
public interface Repository { }

@Repository @Primary
public class MyRepository implements Repository { }

@Repository
public class YourRepository implements Repository { }
```
- 의존성 주입 시 @Primary가 붙은 MyRepository를 주입
- 타입 세이프한 방법이므로 추천

#### @Qualifier
```JAVA
@Service
public class Service {
  @Autowired @Qualifier("myRepository")
  private Repository repository;
}
```
- 빈의 이름으로 주입
  - 빈의 이름은 클래스명에서 앞자리 소문자

#### 모든 빈 주입
```JAVA
@Service
public class Service {
  @Autowired
  private List<Repository> repository;
}
```

#### 다른 방법(비권장)
```JAVA
@Service
public class Service {
  @Autowired
  private Repository myRepository;
}
```
- 변수명을 빈의 이름과 똑같이 맞춰주면 해당 빈이 주입됨
- 단, 권장되지 않는 방법
