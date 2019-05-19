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

#### 예외 처리 관련 팁
- 하나의 `try` 블럭 안에서 모든 exception을 `catch(Exception e)` 하나로 잡으려 하지 말고, 각각의 예외가 발생할 수 있는 상황에 대하여 `try-catch` 를 따로따로 사용하라.
  - (지금은 다중 `catch`를 지원하니, 그걸 사용하면 될 것 같다)
- 프로그램의 흐름을 제어하기 위한 인위적인 exception handling을 하지 마라.
- `throws` 절에는 `Exception`을 사용하지 말고 보다 상세한 (`FileNotFoundException` 같은) Exception의 하위 클래스를 사용하라.
- exception handling을 자주 사용하라. exception이 발생하지 않을때 exception 처리를 해서 추가되는 오버헤드는 아주 작다. exception이 발생하는 상황에서만 실행시간의 오버헤드가 생긴다.
- DB connection, file, socket connection 등의 리소스를 해제할 때에는 항상 `finally` 블럭을 사용하라. 이것은 leak을 방지해준다.
- 메소드 호출을 할때 항상 exception이 발생하는 메소드 내에서 exception을 처리하라. 특별히 필요한 경우가 아니라면 호출하는 메소드에 exception을 떠넘기지 마라. 호출하는 메소드에 exception을 넘기는 것은 더 많은 실행시간이 걸리기 때문에 로컬에서 처리하는 것이 효율적이다.
- 루프 안에서 exception handling을 하지마라. `try-catch`안에 루프를 넣는 것이 좋다.

## Reference
- [[코드리뷰] Exception 처리](https://www.slipp.net/questions/350)
- [왜?  Checked Exception을 상속해서 예외를 만드셨나요?](https://www.slipp.net/questions/259)
- [임베디드 시스템 개발에 관한 Practice](https://hl1itj.tistory.com/77)
- [스프링 예외처리 코드 보기](https://github.com/spring-projects/spring-framework/search?q=catch&unscoped_q=catch)
- [[Exception전략] Java의 Exception 처리 최적화](https://mentor75.tistory.com/entry/Exception전략-Java의-Exception-처리-최적화)

---

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

