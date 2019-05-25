# [item 71 필요 없는 검사 예외 사용은 피하라]

### 사전 조사
- Checked Exception
    - 컴파일 레벨에서 감지 가능.
    - 단 연쇄적으로 호출한다면 불필요한 try-catch 작성.
    - 따라서 꼭 필요한 경우에만 사용하는 것을 권장.
- Client code가 할 일이 있을 때는 checked, 없을 때는 unchecked.
    - progamming error에는 unchecked exception.
    - 적절한 캡슐화. 비지니스 layer에서 SqlException을 던지지 말것.

    
## Reference
- [Java에서 Checked Exception은 언제 써야 하는가?](https://blog.benelog.net/1901121)
- <https://www.javaworld.com/article/3142626/are-checked-exceptions-good-or-bad.html>



---

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

