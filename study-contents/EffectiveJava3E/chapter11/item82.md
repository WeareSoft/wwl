# [item 82] 스레드 안전성 수준을 문서화하라

### 사전 조사
> The synchronized keyword doesn’t block all access to an object. Other threads can still run unsynchronized methods of the object while the object is locked.

### 비공개 락 객체
- 관련된 stackoverflow 질문
  - https://stackoverflow.com/questions/25147446/why-private-lock
- [참고 문서 - [Blch05, 퍼즐 77]](https://doc.lagout.org/programmation/Java/Java%20Puzzlers_%20Traps%2C%20Pitfalls%2C%20and%20Corner%20Cases%20%5BBloch%20%26%20Gafter%202005-07-04%5D.pdf) - 185p 참고

``` java
import java.util.*;
public class Worker extends Thread {
	private volatile boolean quittingTime = false;
	public void run() {
		while (!quittingTime)
			pretendToWork();
		System.out.println("Beer is good");
	}
	private void pretendToWork() {
		try {
			Thread.sleep(300); // Sleeping on the job?
		} catch (InterruptedException ex) { }
	}
	// It’s quitting time, wait for worker - Called by good boss
	synchronized void quit() throws InterruptedException {
		quittingTime = true;
		join();
	}
	// Rescind quitting time - Called by evil boss
	synchronized void keepWorking() {
		quittingTime = false;
	}
	public static void main(String[] args)
			throws InterruptedException {
		final Worker worker = new Worker();
		worker.start();
		Timer t = new Timer(true); // Daemon thread
		t.schedule(new TimerTask() {
			public void run() { worker.keepWorking(); }
		}, 500);
		Thread.sleep(400);
		worker.quit();
	}
}
```

다음 프로그램은 우리의 예상과 달리 동작한다.
- **예상한 동작**
    - **300ms**: worker 스레드가 `volatile` 필드인 `quittingTime`을 체크하여 업무 종료 시간인지 아닌지를 알아본다. 아직 종료 시간이 아니므로 쓰레드는 다시 일하기 위해 돌아간다.
    - **400ms**: 좋은 상사를 나타내는 메인 스레드가 worker 스레드의 `quit` 메서드르 호출한다. 메인 스레드는 woker 쓰레드의 락을 획득하고, `quttingTime`을 `true`로 설정한 후 worker 스레드의 `join`을 호출한다. `join` 호출은 바로 반환되지 않고 worker 스레드가 종료되기를 기다린다. 
    - **500ms**: 나쁜 상사를 나타내는 타이머 작업이 실행된다. 이는 worker 스레드의 `keepWorking` 메서드를 호춣려 시도하지만, `keepWorking`은 동기화 메서드이고 메인 스레드가 현재 workwer 수레드의 동시화 메서드(`quit` 메서드)를 실행시키고 있으므로 호출이 블록된다.
    **600ms**: worker 스레드가 업무 종료 시간인지를 다시 체크한다. `quttingTime` 필드가 `volatile`이므로, worker 스레드는 이 필드의 새로운 값인 `true`를 볼 수 있음이 보장되며 `Beer is good`이 출력되고 프로그램 실행이 종료된다. 이는 메인 스레드의 `join` 호출이 반환되도록 하며, 메인 스레드의 실행이 종료된다. 타이머 스레드는 데몬(demon)이기 때문에 역시 실행이 종료되며 이로써 프로그램이 끝나게 된다.

결론적으로 이 프로그램은 아무것도 출력하지 않고 끝나지 않는다.
- **이유**
    - 내부적으로 `Thread.join` 호출은 조인되는 스레드를 나타내는 `Thread` 인스턴스에 있는 `Object.wait`를 호출한다. 이는 wait하고 있는 동안 객체에 대한 락을 놓아준다.
    - 근본적인 원인은 `WorkerThread` 클래스의 작성자가 `quit`과 `keepWorking` 메서드 간 상호 배제성을 보장하기 위해 인스턴스 락을 사용했지만, 이 사용 방법이 슈퍼 클래스(Thread)의 내부적인 락 사용과 충돌한다는 것이다.
- **교훈 및 결론**
    - 라이브러리 클래스가 인스턴스 혹은 클래스에 대해 무엇을 락하고, 락하지 않을지에 대해 클래스 명세에서 보장하는 것을 제외하고는 아무것도 추측하지 마라.
        - 라이브러리 호출은 `wait`, `notify`, `notifyAll` 혹은 동기화 메서드 등을 호출할 수 있다. 그리고 이러한 호출은 매플르케이션 레벨의 코드에 영향을 끼친다.
    - 만약 락을 완전히 제어할 필요가 있다면, 다른 누구도 이에 대한 접근을 획득할 수 없음을 보장하라.
- **해결책**
    - 비공개 락 객체 사용
     `Object`타입의 private lock 필드를 추가한 후 `quit`과 `keepWorking` 메서드에서 이 객체를 이용하여 동기화 하는 것이다.
    ``` java
    private final Object lock = new Object();
	// It’s quitting time, wait for worker - Called by good boss
	void quit() throws InterruptedException {
		synchronized (lock) {
			quittingTime = true;
			join();
		}
	}
	// Rescind quitting time - Called by evil boss
	void keepWorking() {
		synchronized (lock) {
			quittingTime = false;
		}
	}
    ```
    - `Thread` 대신 `Runnable`사용
    `Worker` 인스턴스와 `Thread` 인스턴스의 락 간의 결합 관계를 없애준다. 단, 이는 앞에 비해 규모가 큰 리팩토링이다.
    
## Reference
- <https://www.dummies.com/programming/java/how-to-synchronize-methods-when-using-java-threads/>

### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

