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
### Content 1
- content

### Content 2
- content

#### :link: Reference
- [](https://github.com/WeareSoft/wwl/tree/master/SpringInAction)

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
