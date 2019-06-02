# [item 79] 과도한 동기화는 피하라.

### 사전 조사
- [ExecutorService](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html)
    - `Task`를 정의한 클래스의 생성
    - Executor Service에 태스크 객체를 제공
- Executor 특징
    - 쓰레드 풀을 사용
    - 무거운 쓰레드는 미리 할당 가능
    - 태스크와 쓰레드를 생성하고 관리하는 것을 분리
    - 쓰레드 풀안의 쓰레드는 한번해 하나씩 여러 태스크를 실행
    - 태스크 큐를 이용해 태스크를 관리
    - Executor Service를 더이상 필요 없으면 중지
    - Executor Service가 멈추면 모든 쓰레드도 중지
    - <http://hochulshin.com/java-multithreading-executor-basic/>

```
If you would like to immediately block waiting for a task, you can use constructions of the form result = exec.submit(aCallable).get();
```

- 재진입LOCK
    - <https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantLock.html>
- CopyOnWriteArrayList
    - <https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CopyOnWriteArrayList.html>


## Reference


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

