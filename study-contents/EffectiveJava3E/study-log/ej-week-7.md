# [2019.06.02] Effective Java 3/E 스터디 진행 - 7주차
참석자: Delf, Doy, Hee, Nesoy
## 주제
- [**아이템 76.**](../chapter10/item76.md) 가능한 한 실패 원자적으로 만들라 
- [**아이템 77.**](../chapter10/item77.md) 예외를 무시하지 말라 
- [**아이템 78.**](../chapter11/item78.md) 공유 중인 가변 데이터는 동기화해 사용하라 
- [**아이템 79.**](../chapter11/item79.md) 과도한 동기화는 피하라 
- [**아이템 80.**](../chapter11/item80.md) 스레드보다는 실행자, 태스크, 스트림을 애용하라 
- [**아이템 81.**](../chapter11/item81.md) wait와 notify보다는 동시성 유틸리티를 애용하라 

## 아이템 분담
- Delf: 79, 81
- Doy: 78, 80
- Hee: 76, 77 
- Nesoy: 79, 81 

## 주요 이슈

#### item 78
- `volatile` 키워드의 동작에 대하여
  - 스레드 변수의 가시성(visibility)
    - CPU cache와 main memory 관점에서의 스레드의 데이터 접근 범위
  - `volatile`과 `synchronized`의 차이
- `++` 연산의 비원자성

#### item 79
- `synchronized`의 동작
  - 자바의 재진입(reenterant)
    - 락 획득의 기준은 무엇인가?

#### item 80
- fork-join에 대하여