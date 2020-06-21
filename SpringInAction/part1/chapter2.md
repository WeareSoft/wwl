## CHAPTER 2. 웹 애플리케이션 개발하기
### 1. 정보 보여주기
- Lombok
  - @Data
    - getter
    - setter
    - equals
    - hashCode
    - toString
  - @Slf4j
- 스프링 MVC 요청-대응 어노테이션
  - @RequestMapping
  - @GetMapping
  - @PostMapping ...
- Thymeleaf 템플릿

### 2. 폼 제출 처리하기

### 3. 폼 입력 유효성 검사하기
- Hibernate
  - @NotNull
  - @NotBlank
  - @Size
  - @Pattern
  - @Digits
  - @Valid

### 4. 뷰 컨트롤러로 작업하기
- WebMvcConfigurer Interface
  - 스프링 MVC를 구성하는 메서드를 정의
  - addViewControllers 오버라이딩

### 5. 뷰 템플릿 라이브러리 선택하기
- 라이브러리
  - FreeMarker
  - Groovy
  - JavaServer Pages(JSP)
  - Mustache
  - Thymeleaf
- 템플릿 캐싱
  - 기본적으로 템플릿은 최초 사용될 때 한 번만 파싱된다. 그리고 파싱된 결과는 향후 사용을 위해 캐시에 저장된다.
  - 매번 요청을 처리할 때마다 불필요하게 템플릿 파싱을 하지 않으므로 성능을 향상시킬 수 있다.
  - 개발할 땐 비활성화 하자.