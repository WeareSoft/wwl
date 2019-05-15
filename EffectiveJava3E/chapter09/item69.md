# [item 69 예외는 진짜 예외 상황에만 사용하라]
### 사전 조사
- [Github 소스코드 편하게 보는 툴](https://chrome.google.com/webstore/detail/octotree/bkhaagjahfmjljalopjnoealnfndnagc/support?hl=ko)

#### 진짜 예외 상황에는 어떻게 사용해야 할까?
- [Couchbase Java Client Exception 구성](https://github.com/couchbase/couchbase-java-client/tree/master/src/main/java/com/couchbase/client/java/error)
    - [적절한 Exception으로 전환](https://github.com/couchbase/couchbase-java-client/blob/5ce08b09ec1c67b02f48e6de5e3c9f8575675969/src/main/java/com/couchbase/client/java/transcoder/LegacyTranscoder.java#L241)
    - unCheckedException으로 적절한 Layer에서 Handling하기.
        - Repository에서 Exception이 나면? -> 항상 Exception 처리를 Repository에서 처리해야 할까?
            - 상황에 따라 예외처리가 다르다면 다른 예외를 전달해야 한다.
            - 또는 항상 예외보다는 Optional이나 Empty값을 반환할 수 있다.
    - CheckedException은 정말 사용자가 Handling해야하는 상황에서만 사용하기.
    - [기본적으로 적절한 Logging도 남기면 좋다.](https://github.com/couchbase/couchbase-java-client/blob/5ce08b09ec1c67b02f48e6de5e3c9f8575675969/src/main/java/com/couchbase/client/java/transcoder/LegacyTranscoder.java#L255)

## Reference
- [[코드리뷰] Exception 처리](https://www.slipp.net/questions/350)
- [왜?  Checked Exception을 상속해서 예외를 만드셨나요?](https://www.slipp.net/questions/259)
- [임베디드 시스템 개발에 관한 Practice](https://hl1itj.tistory.com/77)
- [스프링 예외처리 코드 보기](https://github.com/spring-projects/spring-framework/search?q=catch&unscoped_q=catch)

---

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

