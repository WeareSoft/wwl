# [item 48] 스트림 병렬화는 주의해서 적용하라
### 사전 조사
- Java 동시성 프로그래밍
    - Thread, 동기화, wait/notify

#### [wait / notify](https://programmers.co.kr/learn/courses/9/lessons/278)
- wait와 notify는 동기화된 블록안에서 사용해야 한다.
- wait를 만나게 되면 해당 쓰레드는 해당 객체의 모니터링 락에 대한 권한을 가지고 있다면 모니터링 락의 권한을 놓고 대기한다.

```java
  public class ThreadB extends Thread{
       // 해당 쓰레드가 실행되면 자기 자신의 모니터링 락을 획득
       // 5번 반복하면서 0.5초씩 쉬면서 total에 값을 누적
       // 그후에 notify()메소드를 호출하여 wait하고 있는 쓰레드를 깨움
        int total;
        @Override
        public void run(){
            synchronized(this){
                for(int i=0; i<5 ; i++){
                    System.out.println(i + "를 더합니다.");
                    total += i;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                notify();
            }
        }
    }
```

```java
public class ThreadA {
        public static void main(String[] args){
            // 앞에서 만든 쓰레드 B를 만든 후 start
            // 해당 쓰레드가 실행되면, 해당 쓰레드는 run메소드 안에서 자신의 모니터링 락을 획득
            ThreadB b = new ThreadB();
            b.start();

            // b에 대하여 동기화 블럭을 설정
            // 만약 main쓰레드가 아래의 블록을 위의 Thread보다 먼저 실행되었다면 wait를 하게 되면서 모니터링 락을 놓고 대기
            synchronized(b){
                try{
                    // b.wait()메소드를 호출.
                    // 메인쓰레드는 정지
                    // ThreadB가 5번 값을 더한 후 notify를 호출하게 되면 wait에서 깨어남
                    System.out.println("b가 완료될때까지 기다립니다.");
                    b.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                //깨어난 후 결과를 출력
                System.out.println("Total is: " + b.total);
            }
        }
    }
```

#### [Java 5 - Executor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html)
- Executor 인터페이스:
    - 제공된 작업(Runnable 구현체)을 실행하는 객체가 구현해야 할 인터페이스.
    - 이 인터페이스는 작업을 제공하는 코드와 작업을 실행하는 메커니즘의 사이의 커플링을 제거해준다.
- ExecutorService 인터페이스:
    - Executor의 라이프사이클을 관리할 수 있는 기능을 정의하고 있다.
    - Runnable 뿐만 아니라 Callable을 작업으로 사용할 수 있는 메소드가 추가로 제공된다.
- 쓰레드 풀을 사용
- 무거운 쓰레드는 미리 할당 가능
- 태스크와 쓰레드를 생성하고 관리하는 것을 분리
- 쓰레드 풀안의 쓰레드는 한번해 하나씩 여러 태스크를 실행
- 태스크 큐를 이용해 태스크를 관리
- Executor Service를 더이상 필요 없으면 중지
- Executor Service가 멈추면 모든 쓰레드도 중지
- Advanced Point
    - Callable과 Runnable의 차이는?
- Reference
    - <http://hochulshin.com/java-multithreading-executor-basic/>
    - <https://gompangs.tistory.com/65>
    - <https://javacan.tistory.com/entry/134>

#### Java 7 - Fork Join
- 어떻게 Job을 분할할것인가?
    - [Spliterator를 사용.](https://doohyun.tistory.com/42)
- 남는 Job은 어떻게 다른 사람에게 뺏을것인가?
    - 각자 Job Queue를 활용하여 Push/Pop
- Reference
    - <https://okky.kr/article/345720>

#### Java 8 - parallelStream()
- 병렬 스트림이란 각각의 스레드에서 처리할 수 있도록 스트림 요소를 여러 청크로 분할한 스트림이다.
- 내부적으로 Fork-Join Pool 사용.
- 1부터 N까지 더하는 프로그램.

```java
public static long sequentialSum(long n){
    return Stream.iterate(1L, i-> i+1)
                .limit(n)
                .reduce(0L, Long::sum);
}
```

```java
public static long iterativeSum(long n){
    long result = 0;
    for(long i=1L; i<=n; i++){
        result += i;
    }
    return result;
}
```

- n이 엄청 커진다면?
    - 연산을 병렬로 처리하는 것이 좋을 것이다.
        - 결과 변수는 어떻게 동기화해야 할까?
        - 몇 개의 스레드를 사용해야 할까?
        - 숫자는 어떻게 생성할까?
        - 생성된 숫자는 누가 더할까?

```java
public static long sequentialSum(long n){
    return Stream.iterate(1L, i-> i+1)
                .limit(n)
                .parallel() // 병렬 스트림으로 변환
                .reduce(0L, Long::sum)
}
```

#### Cache Memory
- <https://ssoonidev.tistory.com/35>

#### Recommend reference
- [java8 병렬 스트림 효율적으로 사용하는 방법 - 데모버전](https://demoversion.tistory.com/34)
- [[Java8 in action] Chap7. 병렬 데이터 처리와 성능 - YonghoChoi](https://yongho1037.tistory.com/705)
---

### 스터디 요약
-

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

