# 아이템7 - 다 쓴 객체 참조를 해제하라
JAVA는 C나 C++과 달리 다 쓴 객체를 가비지 컬렉터가 알아서 수거하기 때문에 편리하지만, 숨어있는 **메모리 누수**를 예방하고 주의하는 것이 중요

## 메모리 누수의 주범
- 자기 메모리를 직접 관리하는 클래스
- 캐시
- 리스너 또는 콜백

메모리 누수 발생 이유는 **더 이상 사용하지 않는 객체를 참조 해제하지 않았기 때문**

<br />

## 자기 메모리를 직접 관리하는 클래스
```JAVA
public class Stack {
  private Object[] elements;
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 10;

  public Stack() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0) {
      throw new EmptyStackException();
    }

    return elements[--size];
  }

  private void ensureCapacity() {
    if (elements.length == size) {
      elements = Arrays.copyOf(elements, 2 * size + 1);
    }
  }
}
```
- ```pop()``` 메소드에서 메모리 누수 발생
  - 스택에서 꺼냈어도 ```elements```가 **여전히 해당 객체를 참조**하고 있기 때문에 가비지 컬렉션 대상이 아님
  - 프로그램이 더 이상 사용하지 않는 **다 쓴 참조**를 ```elements```가 계속 가지고 있는 상황
- 위 프로그램을 오래 실행하면 가비지 컬렉션 활동과 메모리 사용량이 증가하여 성능이 저하되고, 심한 경우 디스크 페이징 또는 ```OutOfMemoryError``` 발생

<br />

객체 참조 하나를 살려두면 가비지 컬렉터는 그 객체를 포함하여 객체가 참조하는 모든 객체와 그 모든 객체가 참조하는 또다른 모든 객체를 회수하기 못하기 때문에 메모리 누수를 찾기가 까다로운 편

<br />

### 메모리 누수 예방 방법 - 다 쓴 참조 해제
- 다 쓴 참조는 null 처리하는 방법
- 다 쓴 참조를 담은 변수를 scope 밖으로 밀어내는 방법

#### 1. 다 쓴 참조는 null 처리하는 방법
```JAVA
public Object pop() {
  if (size == 0) {
    throw new EmptyStackException();
  }

  Object result = elements[--size];
  elements[size] = null; // 다 쓴 참조 해제
  return result;
}
```
- null 처리 하지 않는다면 다 쓴 참조를 사용하려 할 경우 잘못된 동작이 그대로 수행되겠지만, null 처리 함으로써 예외 발생 가능
- **단, null 처리는 특정한 경우에만 수행할 것**
  - **자기 메모리를 직접 관리**하는 클래스의 객체를 다루는 경우
  - 위의 ```Stack```을 예시로 들면
    - ```Stack```은 객체 자체가 아니라 **객체 참조를 담는 ```elements``` 배열**로 저장소 풀을 만들어 원소를 관리하는 방식
    - ```push()```로 추가된 **활성 영역**과 ```pop()```으로 제거된 **비활성 영역**이 있는데, 비활성 영역 객체는 더 이상 사용하지 않는 객체이므로 제거 필요
    - 다만 활성/비활성 구분을 개발자는 알지만 가비지 컬렉터는 알지 못하기 때문에 적절한 컬렉션이 불가능하여 null 처리가 필요한 것

<br />

#### 2. 다 쓴 참조를 담은 변수를 scope 밖으로 밀어내는 방법
```JAVA
for (Element e : c) {
  // ...
}
```
- **다 쓴 참조를 해제하는 가장 좋은 방법** : 변수 범위를 최소가 되게 정의하기(아이템 57)
- ```Element```의 변수 ```e```는 반복문 한 번 범위만큼만 살아있고, 해당 반복 회차가 종료되면 더 이상 참조되지 않아 가비지 컬렉션 대상이 됨

<br />

## 캐시
캐시 또한 메모리 누수의 주범

#### 메모리 누수 예방 방법
- ```WeakHashMap``` 사용
- 백그라운드 스레드를 활용해 캐시 비움
- 캐시에 새 엔트리 추가 시 부수 작업으로 캐시 비움

#### 1. ```WeakHashMap``` 사용
- 캐시 외부에서 **캐시의 키를 참조하는 동안만 엔트리가 살아 있는 캐시**가 필요한 경우 사용하는 방법
- 더이상 캐시의 키를 참조하지 않으면 즉시 자동으로 제거
- [WeakHashMap 설명 참조](http://blog.breakingthat.com/2018/08/26/java-collection-map-weakhashmap/)
<br />

#### 2. 백그라운드 스레드를 활용해 캐시 비움
- ```ScheduledThreadPoolExecutor``` 사용
- [ScheduledThreadPoolExecutor 설명 참조](https://codechacha.com/ko/java-scheduled-thread-pool-executor/)
<br />

#### 3. 캐시에 새 엔트리 추가 시 부수 작업으로 캐시 비움
- ```LinkedHashMap```의 ```removeEldestEntry``` 메소드 사용
```JAVA
class LRU<K, V> extends LinkedHashMap<K, V> {
  private int size;

  private LRU(int size) {
    super(size, 0.75f, true);
    this.size = size;
  }

  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() > size;
  }

  public static <K,V> LRU<K,V> newInstance(int size) {
    return new LRU<K,V>(size);
  }
}
```
- 위의 ```LRU``` 객체를 사용하면 새로운 엔트리를 추가할 때마다 가장 오래된 엔트리를 제거 후 추가하는 동작 수행

<br />

## 리스너 또는 콜백
클라이언트가 콜백을 등록만 하고 해지하지 않는다면 콜백이 계속 쌓이는 문제 발생

#### 메모리 누수 예방 방법
- 콜백을 약한 참조로 저장
  - ```WeakHashMap```의 키로 저장

<br />

## 요약
- 메모리 누수는 잡아내기 까다롭기 때문에 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구로 발견하기도 함
- 예방법을 익혀두는 것이 중요
