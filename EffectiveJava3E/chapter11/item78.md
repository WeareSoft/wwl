# [item 78] 공유 중인 가변 데이터는 동기화해 사용하라
### 사전조사

#### 가시성
**한 스레드가 쓴 값을 다른 스레드가 볼 수도 있고 그렇지 않을 수도 있다.** 이를 **가시성** 문제라고 한다. 

이 문제의 원인은 다양하다. 
- 최적화를 위해 컴파일러나 CPU에서 발생하는 **코드 재배열(Reordering)** 때문에 이런 문제가 발생할 수도 있고, 
- 멀티 코어 환경에서는 **코어의 캐시 값이 메모리에 제때 쓰이지 않아** 문제가 발생할 수도 있다.

락을 사용하면 가시성의 문제가 사라질까? 그렇다. 자바에서는 스레드가 락을 획득하는 경우 그 이전에 쓰였던 값들의 가시성을 보장한다. `synchronized`가 적용된 Counter [예제]((http://happinessoncode.com/2017/10/04/java-intrinsic-lock/#재진입-가능성-Reentrancy))에서 스레드 A, 스레드 B 순서로 increase()를 호출했을 때, 스레드 B는 스레드 A가 쓴 값을 읽을 수 있다(visible 하다). 이는 고유 락 뿐만 아니라 [`ReentrantLock`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/ReentrantLock.html) 같은 명시적인 락에서도 똑같이 적용된다.

#### `Synchronized`와 `volatile`의 차이
`Synchronized`에 대하여
> 이 상황에 대한 해결책으로는 synchronized 블록을 사용할 수 있다. 이 블록은 한 시점에 오직 하나의 쓰레드만이 특정 코드 영역에 접근할 수 있도록 보장해준다. 또한 volatile 키워드처럼, <u>**이 블록 안에서 접근되는 모든 변수들은 메인 메모리로부터 읽어들여지고, 쓰레드의 실행이 이 블록을 벗어나면 블록 안에서 수정된 모든 변수들이 즉각 메인 메모리로 반영될 수 있도록 해준다**</u> - volatile 키워드가 선언되어 있든 없든.
> 
> [자바 메모리 모델 - 박철우 블로그](https://parkcheolu.tistory.com/14)
#### 재진입
> 자바의 고유 락은 재진입 가능하다. 재진입 가능하다는 것은 락의 획득이 호출 단위가 아닌 **스레드 단위**로 일어난다는 것을 의미한다. **이미 락을 획득한 스레드는 같은 락을 얻기 위해 대기할 필요 없다.** 이미 락을 갖고 있으므로 같은 락에 대한 synchronized 블록을 만났을 때 대기없이 통과한다.
>
> [Java의 고유 락(intrinsic lock)에 대해](http://happinessoncode.com/2017/10/04/java-intrinsic-lock/#재진입-가능성-Reentrancy)


### Reference
- [Java의 volatile 키워드에 대한 이해 - IT 세미 덕후](http://kwanseob.blogspot.com/2012/08/java-volatile.html)
- [자바 volatile 키워드 - 박철우 블로그](https://parkcheolu.tistory.com/16)
- [자바 메모리 모델 - 박철우 블로그](https://parkcheolu.tistory.com/14)
- [Java의 고유 락(intrinsic lock)에 대해](http://happinessoncode.com/2017/10/04/java-intrinsic-lock/#재진입-가능성-Reentrancy)