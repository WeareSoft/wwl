# [item 84] 프로그램의 동작을 스레드 스케줄러에 기대지 말라

### 사전 조사
- [Scheduler 정책은 어떻게 확인할까?](https://stackoverflow.com/questions/13062887/how-to-get-thread-scheduling-policy-from-console)
    - [chrt 명령어](https://linux-tips.com/t/how-to-use-chrt-command/268)
    - [OS 스켸줄링 정책들](https://palpit.tistory.com/622)
- [적절한 스레드 Pool의 크기는 어떻게 정할까?](https://12bme.tistory.com/368)
    - N^cpu = CPU의 개수
    - U^cpu = 목표로 하는 CPU 활용도, U^cpu 값은 0보다 크거나 같고, 1보다 작거나 같다.
    - W/C = 작업 시간 대비 대기 시간의 비율
    - N^thread = N^cpu * U^cpu * (1 + W/C)
- [Busy Waiting이란?](https://nesoy.github.io/articles/2019-06/OS-Busy-Waiting)
- [JVM의 Thread Scheduler는 무엇일까?](https://stackoverflow.com/questions/2816011/what-is-the-jvm-scheduling-algorithm)
    - 따로 없음
    - OS의 정책을 따라가는 듯.


## Reference



### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

