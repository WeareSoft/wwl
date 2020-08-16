# CHAPTER 6. REST 서비스 생성하기 - CHAPTER 7. REST 서비스 사용하기
## :heavy_check_mark: `ResponseEntity`
### ResponseEntity
- HttpEntity의 확장된 객체이다.
    - HttpStatus 코드를 추가하여 반환할 수 있다.

- Controller에서 반환이 가능하다.
```java
 @RequestMapping("/handle")
 public ResponseEntity<String> handle() {
   URI location = ...;
   HttpHeaders responseHeaders = new HttpHeaders();
   responseHeaders.setLocation(location);
   responseHeaders.set("MyResponseHeader", "MyValue");
   return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
 }
```

- static으로도 생성할 수 있다.
```java
@RequestMapping("/handle")
public ResponseEntity<String> handle() {
	URI location = ...;
	return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
}
```

#### :link: Reference
- <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html>

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
 consumes = "text/plain"
 consumes = {"text/plain", "application/*"}
 consumes = MediaType.TEXT_PLAIN_VALUE
```

### produces
- @RequestMapping의 하나의 속성
- Http의 Accept 요청 헤더와 일치하는 경우에 요청이 진행된다.
- 해당 Type으로 Response로 보내준다.

```java
 produces = "text/plain"
 produces = {"text/plain", "application/*"}
 produces = MediaType.TEXT_PLAIN_VALUE
 produces = "text/plain;charset=UTF-8"
```

#### :link: Reference
- <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html>

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
