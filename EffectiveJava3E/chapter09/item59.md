# [item 59] 라이브러리를 익히고 사용하라
### 사전 조사
#### `Random` 클래스
**1. `java.util.Random`**
- 스레드로부터 안전하다.
- 그러나 스레드 전체에서 동일한 `java.util.Random` 인스턴스를 동시에 사용하면 경합이 발생하여 성능이 저하 될 수 있다. 
- 자바 1.0 이후

**2. `java.util.concurrent.ThreadLocalRandom`**
- 스레드와 격리된 난수 생성기이다.
- `ThreadLocalRandom.current()`는 하나 이상의 스레드에서 반복적으로 액세스 할 수 있다.
- `java.util.Random`과 비교하여 오버 헤드가 훨씬 적다. 
- Java 1.7 이후

**3. `java.util.SplittableRandom`**
- 매우 고성능 난수 생성기이다.
- `SplittableRandom`의 단일 인스턴스는 스레드로부터 안전하지 않다.
- 스레드간에 공유되지 않고 분할되도록 설계되었다. 
    - 예를 들어 난수를 사용하는 fork-join 스타일 계산에는 `new Subtask (SplittableRandom.split()).fork()` 형식의 구성이 포함될 수 있다.
- Java 8 스트림의 병렬 계산에 유용하다.
- Java 1.8 이후


#### Fork와 Join
- ForkJoinPool은 Fork Join Framework의 대표 클래스이다.
- ForkJoinPool은 기본적으로 스레드 풀 서비스의 일종이다.
- 큰 업무를 작은 업무 단위로 쪼개고, 그것을 각기 다른 CPU에서 병렬로 실행한 후 결과를 취합하는 방식이다. 마치 분할정복 알고리즘과 흡사하다.
- 여러 CPU들을 최대한 활용하면서 동기화와 GC를 피할수 있는 여러 기법이 사용되었기 때문에, Java 뿐 아니라 Scala에서도 널리 쓰이고 있는 병렬 처리 기법이다.
- 과정
    - 1. 큰 업무를 작은 단위의 업무로 쪼갠다.
    - 2. 부모 스레드로부터 처리로직을 복사하여 새로운 스레드에서 쪼개진 업무를 수행(Fork)시킨다.
    - 3. 2을 반복하다가, 특정 스레드에서 더 이상 Fork가 일어나지 않고 업무가 완료되면 그 결과를 부모 스레드에서 Join하여 값을 취합한다.
    - 4. 3을 반복하다가 최초에 ForkJoinPool을 생성한 스레드로 값을 리턴하여 작업을 완료한다.

#### `java.io.InputStream.transferTo`
- `InputStream`에서 모든 bytes를 읽고, `OutputStream`에 쓴다.

### Recommend reference
- [Different ways to create Random numbers in Java](https://www.logicbig.com/how-to/java-random/different-random-classes.html)
- [쓰레드풀 과 ForkJoinPool](https://okky.kr/article/345720)

---

### 스터디 요약 
- 

---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

