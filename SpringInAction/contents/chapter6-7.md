# CHAPTER 6. REST 서비스 생성하기 - CHAPTER 7. REST 서비스 사용하기
## :heavy_check_mark: `ResponseEntity`
### ResponseEntity
- HttpEntity의 확장된 객체이다.
    - HttpStatus 코드를 추가하였다. 
```java
/**
 * Extension of {@link HttpEntity} that adds a {@link HttpStatus} status code.
 * Used in {@code RestTemplate} as well {@code @Controller} methods.
 *
 * <p>In {@code RestTemplate}, this class is returned by
 * {@link org.springframework.web.client.RestTemplate#getForEntity getForEntity()} and
 * {@link org.springframework.web.client.RestTemplate#exchange exchange()}:
 * <pre class="code">
 * ResponseEntity&lt;String&gt; entity = template.getForEntity("https://example.com", String.class);
 * String body = entity.getBody();
 * MediaType contentType = entity.getHeaders().getContentType();
 * HttpStatus statusCode = entity.getStatusCode();
 * </pre>
 *
 * <p>Can also be used in Spring MVC, as the return value from a @Controller method:
 * <pre class="code">
 * &#64;RequestMapping("/handle")
 * public ResponseEntity&lt;String&gt; handle() {
 *   URI location = ...;
 *   HttpHeaders responseHeaders = new HttpHeaders();
 *   responseHeaders.setLocation(location);
 *   responseHeaders.set("MyResponseHeader", "MyValue");
 *   return new ResponseEntity&lt;String&gt;("Hello World", responseHeaders, HttpStatus.CREATED);
 * }
 * </pre>
 *
 * Or, by using a builder accessible via static methods:
 * <pre class="code">
 * &#64;RequestMapping("/handle")
 * public ResponseEntity&lt;String&gt; handle() {
 *   URI location = ...;
 *   return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
 * }
 * </pre>
 *
 * @author Arjen Poutsma
 * @author Brian Clozel
 * @since 3.0.2
 * @param <T> the body type
 * @see #getStatusCode()
 * @see org.springframework.web.client.RestOperations#getForEntity(String, Class, Object...)
 * @see org.springframework.web.client.RestOperations#getForEntity(String, Class, java.util.Map)
 * @see org.springframework.web.client.RestOperations#getForEntity(URI, Class)
 * @see RequestEntity
 */
```

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)

## :heavy_check_mark: CORS, `@CrossOrigin`
### Content 1
- content

### Content 2
- content

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)


## :heavy_check_mark: consumes, produces attribute
### consumes
- @RequestMapping의 하나의 속성
- 특정 타입에 따라 소비될 수 있도록 조건을 추가할 수 있다.
- 예를 들어 특정 타입을 제외한 요청만 받고 싶은 경우 !text/plain으로 지정하면 된다.
- 아래의 모든 타입은 Content-Type으로 진행된다.

```java
/**
	 * Narrows the primary mapping by media types that can be consumed by the
	 * mapped handler. Consists of one or more media types one of which must
	 * match to the request {@code Content-Type} header. Examples:
	 * <pre class="code">
	 * consumes = "text/plain"
	 * consumes = {"text/plain", "application/*"}
	 * consumes = MediaType.TEXT_PLAIN_VALUE
	 * </pre>
	 * Expressions can be negated by using the "!" operator, as in
	 * "!text/plain", which matches all requests with a {@code Content-Type}
	 * other than "text/plain".
	 * <p><b>Supported at the type level as well as at the method level!</b>
	 * If specified at both levels, the method level consumes condition overrides
	 * the type level condition.
	 * @see org.springframework.http.MediaType
	 * @see javax.servlet.http.HttpServletRequest#getContentType()
	 */
	String[] consumes() default {};
```

### produces
- @RequestMapping의 하나의 속성
- Http의 Accept 요청 헤더와 일치하는 경우에 요청이 진행된다.
- 해당 Type으로 Response로 보내준다.

```java
/**
	 * Narrows the primary mapping by media types that can be produced by the
	 * mapped handler. Consists of one or more media types one of which must
	 * be chosen via content negotiation against the "acceptable" media types
	 * of the request. Typically those are extracted from the {@code "Accept"}
	 * header but may be derived from query parameters, or other. Examples:
	 * <pre class="code">
	 * produces = "text/plain"
	 * produces = {"text/plain", "application/*"}
	 * produces = MediaType.TEXT_PLAIN_VALUE
	 * produces = "text/plain;charset=UTF-8"
	 * </pre>
	 * <p>If a declared media type contains a parameter (e.g. "charset=UTF-8",
	 * "type=feed", "type=entry") and if a compatible media type from the request
	 * has that parameter too, then the parameter values must match. Otherwise
	 * if the media type from the request does not contain the parameter, it is
	 * assumed the client accepts any value.
	 * <p>Expressions can be negated by using the "!" operator, as in "!text/plain",
	 * which matches all requests with a {@code Accept} other than "text/plain".
	 * <p><b>Supported at the type level as well as at the method level!</b>
	 * If specified at both levels, the method level produces condition overrides
	 * the type level condition.
	 * @see org.springframework.http.MediaType
	 */
	String[] produces() default {};
```
#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)

## :heavy_check_mark: `@ResponseStatus`
### Content 1
- content

### Content 2
- content

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)

## :heavy_check_mark: `@Transactional`
<!-- @Transactional 속성, 전파에 대해서 -->
### Content 1
- content

### Content 2
- content

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)


## :heavy_check_mark: REST vs GraphQL
<!-- GraphQL에 대해서 자세히 -->
### REST
- REpresentational State Transfer
- 웹에 존재하는 모든 자원(이미지, 동영상, DB 자원)에 고유한 URI를 부여해 활용하는 것
- 자원을 정의하고 자원에 대한 주소를 지정하는 방법론
- 예시
  - Community site API
    - 글 조회/작성/수정/삭제 할 수 있고, 각 글에 댓글을 조회/작성/수정/삭제 할 수 있다.
    ```
    글 조회 = GET /posts
    글 작성 = POST /posts
    글 수정 = PUT /posts/[postid]
    글 삭제 = DELETE /posts/[postid]
    댓글 조회 = GET /posts/[postid]/comments
    댓글 작성 = POST /posts/[postid]/comments
    댓글 수정 = PUT /posts/[postid]/comments/[commentid]
    댓글 삭제 = DELETE /posts/[postid]/comments/[commentid]
    ```

### GraphQL
- Graph Query Language
  - Query Language란?
    - Query Language는 정보를 얻기 위해 보내는 질의문(Query)을 만들기 위해 사용되는 컴퓨터 언어
  - API를 통해 정보를 주고받기 위해 사용하는 Query Language
- sql은 데이터베이스 시스템에 저장된 데이터를 효율적으로 가져오는 것이 목적이고, gql은 웹 클라이언트가 데이터를 서버로부터 효율적으로 가져오는 것이 목적이다.
- 왜 생겨났을까?
  - RESTful API로는 다양한 기종에서 필요한 정보들을 일일이 구현하는 것이 힘들었다.
    - IOS와 Android에서 필요한 정보들이 조금씩 달랐고, 그 다른 부분마다 API를 구현하는 것이 힘들었다.
  - 이 때문에 정보를 사용하는 측에서 원하는 대로 정보를 가져올 수 있고,
보다 편하게 정보를 수정할 수 있도록 하는 표준화된 Query language를 만들게 되었다.
- [예시](https://velopert.com/2318)
  - 쿼리
  ```
  {
    user(id: 4802170) {
      id
      name
      isViewerFriend
      profilePicture(size: 50)  {
        uri
        width
        height
      }
      friendConnection(first: 5) {
        totalCount
        friends {
          id
          name
        }
      }
    }
  }
  ```
  - 결과
  ```
  {
    "data": {
      "user": {
        "id": "4802170",
        "name": "Lee Byron",
        "isViewerFriend": true,
        "profilePicture": {
          "uri": "cdn://pic/4802170/50",
          "width": 50,
          "height": 50
        },
        "friendConnection": {
          "totalCount": 14,
          "friends": [
            {
              "id": "305249",
              "name": "Stephen Schwink"
            },
            {
              "id": "3108935",
              "name": "Nathaniel Roman"
            },
            {
              "id": "9020247",
              "name": "William Sanville"
            },
            {
              "id": "13957785",
              "name": "Alex Langenfeld"
            },
            {
              "id": "37000641",
              "name": "Nick Schrock"
            }
          ]
        }
      }
    }
  }
  ```

### 차이점  
![](../images/rest-vs-graphql.png)
#### 1. API의 Endpoint
- REST
  - URL, METHOD 등을 조합하기 때문에 다양한 Endpoint가 존재한다.
- GraphQL
  - 전체 API를 위해 단 하나의 Endpoint가 존재한다.
  - 불러오는 데이터의 종류를 쿼리 조합을 통해서 결정한다.
    - 예를 들면, REST API에서는 각 Endpoint마다 데이터베이스 SQL 쿼리가 달라지는 반면, gql API는 gql 스키마의 타입마다 데이터베이스 SQL 쿼리가 달라진다.
#### 2. API 응답 구조
- REST
  - 하나의 Endpoint에서 돌려줄 수 있는 응답 구조가 정해져 있는 경우가 많다.
  - API를 작성할 때 이미 정해놓은 구조로만 응답이 온다.
- GraphQL
  - 사용자가 응답의 구조를 자신이 원하는 방식으로 바꿀 수 있다.

### 장단점
- REST
  - 장점
    - 쉬운 사용
      - HTTP 프로토콜 인프라를 그대로 사용하므로 별도의 인프라를 구축할 필요가 없다.
    - 클라이언트-서버 역할의 명확한 분리
      - 클라이언트는 REST API를 통해 서버와 정보를 주고받는다. 
      - REST의 특징인 Stateless에 따라 서버는 클라이언트의 Context를 유지할 필요가 없다.
    - 특정 데이터 표현 사용 가능
      - REST API는 헤더 부분에 URI 처리 메소드를 명시하고 필요한 실제 데이터를 `body`에 표현할 수 있도록 분리시켰다.
      - `JSON`, `XML` 등 원하는 Representation 언어로 사용 가능하다.
  - 단점
    - 메서드의 한계
      - REST는 HTTP 메서드를 이용하여 URI를 표현한다.
      - 이러한 표현은 쉬운 사용이 가능하다는 장점이 있지만 반대로 메서드 형태가 제한적인 단점이 있다.
  - 표준이 없음
    - REST는 설계 가이드 일 뿐이지 표준이 아니다. 명확한 표준이 없다.
  - Under Fetching
    - 요청에 맞게 유효한 데이터를 보여주기 위해 여러 API를 호출하게 되는 경우
      - 쇼핑몰 서비스의 경우, 로그인한 사용자의 장바구니 정보를 보여준다고 가정하면 여러 API를 호출하게 된다.
        - `/user/1/`, `/cart/`, `/notification/`, `/wish/`
  - Over Fetching
    - 리소스 낭비
      - 사용자의 데이터를 조회하는 `/user/` API가 있고, 사용자 번호 1에 해당하는 데이터를 조회한다면 아래와 같은 형태가 된다.
        ```
        GET /user/1/
        response body 
        {
        "user_no": 1,
        "user_name": "test",
        "usere_grade": "VVIP",
        "zip": "11053",
        "last_login_timestamp": "2019-08-08 12:11:44",
        ...
        }
        ```
      - 여기서 클라이언트에서는 1번에 해당하는 유저의 이름만을 사용하고자 한다고 해도 유저 이름만 반환하는 API가 없다면 위와 같은 `/user/1/` API를 호출한 다음, `user_name`을 가져와 사용해야 한다.
      - `user_grade`, `zip` 등의 데이터는 사용하지 않는 데이터도 같이 반환받는다.
- GraphQL
  - 장점
    - HTTP 요청의 횟수를 줄일 수 있다.
      - REST는 각 Resource 종류 별로 요청을 해야 하고, 따라서 요청 횟수가 필요한 Resource의 종류에 비례한다.
      - GraphQL 은 원하는 정보를 하나의 Query에 모두 담아 요청하는 것이 가능하다.
    - HTTP 응답의 크기를 줄일 수 있다.
      - REST는 응답의 형태가 정해져있고, 따라서 필요한 정보만 부분적으로 요청하는 것이 힘들다.
      - GraphQL은 원하는 대로 정보를 요청하는 것이 가능하다.
  - 예시
    - 우리가 글의 목록과 각 글에 쓰인 댓글의 목록을 가져올 수 있는 API 가 있다고 해보자.
    - **RESTful**하게 작성되었다면 글과 댓글의 목록을 가져오기 위해서 다음 중 한 가지 방법을 선택해야 할 것이다.
    - 1. 글의 목록을 가져오는 Endpoint와 댓글의 목록을 가져오는 Endpoint에 각각 요청을 여러 번 한다.
      - 글이 5 개 있다고 해보자.
      - 이 경우에는 글의 목록을 가져오는 Endpoint에 요청을 하고,
각 글마다 댓글의 목록을 가져오는 Endpoint에 요청을 5 번 해야 글과 댓글의 목록을 모두 가져올 수 있을 것이다.
    - 2. 글의 목록을 가져오는 Endpoint의 응답에 댓글의 목록을 포함한다.
      - 글이 5 개 있다고 해보자.
      - 이 경우에는 글의 목록을 가져오는 Endpoint에 요청을 한 번 하면 끝이지만,
      - 글의 목록만 가져와야 하는 경우나 몇몇 글의 댓글만 가져와야 하는 경우가 있다면 필요한 정보에 비해서 응답의 크기가 쓸데없이 큰 경우가 발생할 것이다.
    - 3. 글의 목록을 가져오는 요청에 조건을 달아서 댓글의 목록을 포함할 수도, 포함하지 않을 수도 있게 한다.
      - API에 Endpoint 가 많을 경우, API를 만드는 것이 점점 더 복잡해지고, 결국 GraphQL을 만든 이유와 비슷한 상황에 처하게 된다.
    - 반면 같은 API를 **GraphQL**로 작성하였다면
    - 1. 글의 목록만을 가져와야 할 경우에는 글의 목록만을 가져오는 Query를 작성하여 서버에 요청을 보낸다.
    - 2. 글의 목록과 댓글을 모두 가져와야 할 경우에는 글의 목록과 댓글을 모두 가져오는 Query를 작성하여 서버에 요청을 보낸다.
  - 단점
    - File 전송 등 Text만으로 하기 힘든 내용들을 처리하기 복잡하다.
    - 고정된 요청과 응답만 필요할 경우에는 Query로 인해 요청의 크기가 REST의 경우보다 더 커진다.
    - 단순한 서비스에서는 사용하기가 복잡하다.
    - 캐싱 기능의 구현이 복잡하다.
      - 대부분의 언어에서 라이브러리로 제공

### 선택 기준
- REST
  - HTTP 와 HTTPs에 의한 Caching 을 잘 사용하고 싶을 때
  - File 전송 등 단순한 Text로 처리되지 않는 요청들이 있을 때
  - 요청의 구조가 정해져 있을 때
- GraphQL
  - 서로 다른 모양의 다양한 요청들에 대해 응답할 수 있어야 할 때
  - 대부분의 요청이 CRUD에 해당할 때

#### :link: Reference
- [GraphQL과 RESTful API](https://www.holaxprogramming.com/2018/01/20/graphql-vs-restful-api/)
- [GraphQL 개념잡기](https://tech.kakao.com/2019/08/01/graphql-basic/)
- [GraphQL vs. REST](https://www.apollographql.com/blog/graphql-vs-rest-5d425123e34b/)

## :heavy_check_mark: `Webclient`, `RestTemplate` 사용법, 차이점, 설정, 주의할 점
### Content 1
- content

### Content 2
- content

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)

## :heavy_check_mark: `ParameterizedTypeReference`
<!-- (super type token)
참고: effective java -->
### Content 1
- content

### Content 2
- content

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)


---

### :house: [SpringInAction Home](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)
