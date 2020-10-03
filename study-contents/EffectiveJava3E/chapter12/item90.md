# [item 90] 직렬화된 인스턴스 대신 직렬화 프록시 사용을 검토하라

### 사전 조사

- [WriteReplace(), readResolve() in Java Doc](https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html)
    - 기본적으로 writeObject, readObejct가 호출되지만
        - WriteReplace, readResolve 존재하면 invoke합니다.
- Example
    - [What are the writeReplace() and readResolve() methods used for?](http://www.jguru.com/faq/view.jsp?EID=44039)
## Reference

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

