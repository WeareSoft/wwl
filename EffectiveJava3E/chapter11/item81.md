# [item 81] wait와 notify보다는 동시성 유틸리티를 애용하라.

### 사전 조사

- [java.util.concurrent](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/package-summary.html)에 다양한 Concurrent Collection

![throwable](../images/chapter11-item81-01.png)


#### [Live-lock](https://medium.com/@ahaljh/%EB%8F%99%EC%8B%9C%EC%84%B1-%EA%B4%80%EB%A0%A8-%EA%B0%9C%EB%85%90-d2f3e6a62b99)
- 서로가 서로를 기다리는 경우입니다.
- 다만 Deadlock일 경우에는 멈취서 무한정 기다린다면, Live-lock은 지속적으로 상태를 바꾸며 기다린다는 차이점이 있습니다.
- 보통 Deadlock을 회피하려고 코드를 짜다 잘못 작성할 경우 Live-lock이 발생하게 됩니다.

#### [CyclicBarrier](https://dev.re.kr/53)

#### [spurious wakeup](http://mkseo.pe.kr/blog/?p=1551)
- 하나의 쓰레드만 깨울 수 없는 이유는 POSIX 쓰레드의 근본적인 원리와 맞닿아 있는 듯 합니다

## Reference



### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

