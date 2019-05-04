# [item 53] 가변인수는 신중히 사용하라 
### 사전 조사 
#### 가변인수 사용 예
- 인수가 1개 이상이어야 할때
  - 디폴트 인수를 하나 설정한다.
    ``` java
    static int (int firstArg, int... reaminingArg) {
        int min = firstArg;
        for (int arg : remainingArgs) {
            if (arg < min) min = arg
            return min;
        }
    }
    ```
    

- 인수 개수가 정해져 있지 않을 때
  - 성능에 민감한 상황이라면 가변 인수는 걸림돌이 될 수 있다. 많이 호출되는 메서드들은 따로 만들고 나머지 변수를 처리할 수 있는 메서드를 만들자
    ``` java
    public void foo() {}
    public void foo(int a1) {}
    public void foo(int a1, int a2) {}
    public void foo(int a1, int a2, int a3) {}
    public void foo(int a1, int a2, int 3, int... rest) {} // 호출 빈도 적음
    ```
---

### 스터디 요약 
- 

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

