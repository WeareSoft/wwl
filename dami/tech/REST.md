## REST, REST API, RESTful
### 1. REST
#### REST 이론 개념
- Representational State Transfer의 약자
- 웹의 기존 기술과 HTTP 프로토콜을 그대로 활용하여 웹의 장점을 최대한 활용 가능
- WWW과 같은 분산 시스템, 마이크로서비스의 설계를 위한 아키텍처 스타일
- 네트워크 상에서 클라이언트와 서버 간 통신 방식 중 하나

#### REST 구성
- **자원(Resource)**
  - HTTP URI
  - URI로 서버에 존재하는 자원을 표현
- **행위(Verb)**
  - HTTP METHOD
  - GET, POST, PUT, DELETE
- **표현(Representations)**
  - MIME Type
  - Response HTTP Header내 Content-Type
  - 주로 JSON 또는 XML 사용
- Request 예시
  ```
  GET /100 HTTP/1.1
  Host : jeong-pro.tistory.com
  ```
- Response 예시
  ```
  HTTP/1.1 200 OK
  Content-Type : application/json-patch+json

  [{"title": "helloworld", "author": "jeong-pro"}]
  ```

#### REST 동작 개념
- 자원의 표현(URI)에 자원의 행위(HTTP METHOD)를 지정하여 자원의 상태를 주고 받거나 자원의 상태 조작을 요청 및 응답하는 과정

#### REST 특징
##### 1. Uniform (유니폼 인터페이스)
  - URI로 지정한 자원의 조작을 통일되고 한정적인 인터페이스로 수행하는 아키텍처 스타일
  - 특정 언어나 기술에 종속되지 않고, HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용 가능
  - RESTful하기 위해 개발자가 주의를 기울여야하는 부분
##### 2. Self-descriptiveness (자체 표현 구조)
  - Uniform interface와 연결되는 특징
  - REST API 메시지만 보고도 어떤 기능인지 쉽게 이해할 수 있도록 작성 필요
##### 3. Stateless (무상태성)
  - HTTP 프로토콜을 따르기 때문에 REST 역시 무상태 특징
  - 클라이언트의 context(세션, 쿠키 등) 정보를 신경쓰지 않아도 무방하여 구현이 단순
  - API 서버는 각 요청을 별개의 것으로 인식하고 들어오는 요청만을 단순히 처리
  - 서버에서 불필요한 정보를 관리하지 않음으로서 서비스의 자유도 증가
##### 4. Cacheable (캐시 기능)
  - HTTP 프로토콜을 따르기 때문에 REST 역시 캐싱 기능 적용 가능
  - HTTP 프로토콜 표준에서 사용하는 Last-Modified 태그나 E-Tag 이용하여 캐싱 구현
  - 캐시 사용을 통해 응답시간이 빨라지고, API 서버 트랜잭션이 발생하지 않기 때문에 응답시간, 성능, 서버 자원 이용률 향상 가능
##### 5. Client - Server 구조
  - Server : 자원 보유, 클라이언트에 API 제공
  - Client : 서버에 자원 요청, 사용자 인증이나 Context를 직접 관리
  - 각 역할이 구분되어있어 개발 내용이 명확해지고, 상호 의존성 감소
##### 6. 계층형 구조
  - REST 서버는 다중 계층으로 구성 가능
  - 보안, 로드밸런싱, 암호화 계층을 추가해 구조상 유연성
  - PROXY, 게이트웨이 같은 네트워크 기반 중간 매체 사용 가능

#### REST 장점
- HTTP 프로토콜을 그대로 따르기 때문에 REST API 사용을 위한 별도의 인프라 구축 불필요
- HTTP 표준 따르는 모든 플랫폼에서 사용 가능
- REST API 메시지가 의도하는 바를 명확하게 나타내어 쉽게 파악 가능
- 서버와 클랑이언트간 명확한 역할 분리

#### REST 단점
- 표준 부재
- 사용 가능한 메소드가 GET, POST, PUT, DELETE로 제한적
- 구형 브라우저가 지원하지 않는 부분 존재
  - PUT, DELETE 메소드 미지원
  - pushState 미지원

#### REST가 필요한 이유
- 애플리케이션의 분리 및 통합이 용이
- 다양한 클라이언트의 등장
  - 다양한 브라우저와 안드로이드, iOS와 같은 모바일 디바이스에서 통신 필요
  - 멀티 플랫폼 지원을 위해 REST 활용

### 2. REST API
#### REST API 개념
- API?
  - Application Programming Interface
  - 데이터와 기능의 집합을 제공하여 컴퓨터 프로그램간 상호작용을하고, 서로 정보를 교환 가능하게 하는 것
- REST API?
  - REST 기반으로 API를 구현한 것
  - OpenAPI, 마이크로 서비스는 대부분 REST API를 제공

#### REST API 특징
- REST 기반으로 시스템을 분산하면 확장성과 재사용성 증가 및 유지보수 편리
- HTTP 지원 가능한 프로그래밍 언어로 클라이언트, 서버 구현 가능
  - 델파이, 자바, C#, 웹 등을 이용해 클라이언트 구현 가능

#### REST API 설계 규칙
- **URI는 정보의 자원을 표현**
  - 리소스 원형
    - 도큐먼트 : 객체 인스턴스 또는 데이터베이스 레코드
    - 컬렉션 : 서버에서 관리하는 디렉터리, 도큐먼트의 집합
    - 스토어 : 클라이언트에서 관리하는 리소스 저장소, 도큐먼트의 집합
  - 행위에 대한 표현은 불필요
  - 리소스명은 동사보다는 명사, 대문자보다는 소문자 사용 (컨트롤 자원은 예외적으로 동사 허용)
  - 리소스의 도큐먼트명은 단수 명사 사용
  - 리소스의 컬렉션, 스토어명은 복수 명사 사용
- **자원에 대한 행위는 HTTP METHOD로 표현**
  - GET : 리소스 조회
  - POST : 리소스 생성
  - PUT : 리소스 수정
  - DELETE : 리소스 삭제
  ```
  GET /members/delete/1   (x)
  DELETE /members/1       (o)
  ```
  - OPTIONS : 현재 엔드포인트가 제공 가능한 API 메소드를 응답
  - HEAD : 요청에 대한 Header 정보만 응답 (Body 없음)
  - PATCH : 리소스 수정
    - PUT vs PATCH
    - PUT : 자원의 전체 데이터 수정(요청 시, 일부 데이터만 전달할 경우 나머지는 default값으로 수정)
    - PATCH : 자원의 일부 수정(요청 시, 수정할 데이터만 전달)
- URI의 슬래시(/)는 계층 관계를 나타내는데 사용
- URI의 마지막 문자에 슬래시(/)는 미포함
- URI의 가독성을 높이기 위해 하이픈(-) 사용, 밑줄(_) 자제
- URI 경로에는 소문자 사용
- URI 경로에는 파일 확장자 미포함하며, Accept 헤더 사용
- 리소스 간 연관관계가 있을 시 표현 방법
  - /리소스명/리소스-ID/관계있는-다른-리소스명
  ```
  GET : /users/{userid}/devices
  ```

### 3. RESTful
#### RESTful 개념
- 일반적으로 REST라는 아키텍처를 구현하는 웹 서비스를 나타내기 위해 사용
- REST API를 제공하는 웹 서비스는 RESTful한 것
- REST 원리를 따르는 시스템을 지칭하는 용어

#### RESTful의 목적
- 쉽게 이해하고 사용할 수 있는 API를 만드는 것
- 근본적인 목적은 성능향상이 아닌, API의 이해도 및 호환성을 높이는 것
- 성능이 중요한 상황에서는 굳이 RESTful하지 않아도 무방

#### RESTful하지 못한 경우
- REST API 설계 규칙을 지키지 않은 경우
  - CRUD 기능을 모두 POST로만 처리하는 API
  - URI 경로에 자원, id외 다른 정보가 들어가는 경우

### 참고
- [[Network] REST란? REST API란? RESTful이란?](https://gmlwjd9405.github.io/2018/09/21/rest-and-restful.html)
- [REST API 제대로 알고 사용하기](https://meetup.toast.com/posts/92)
- [RESTful에 대해서 설명해주세요.(REST, RESTful, RESTful API 개념 정리)](https://jeong-pro.tistory.com/180)
- [RESTful API 설계 가이드](http://disq.us/p/2c4zvpt)
