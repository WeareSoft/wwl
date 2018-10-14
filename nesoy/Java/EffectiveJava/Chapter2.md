## 생성자 대신 static Factory Method 사용을 고려하자
- instance를 생성하도록 하는 일반적인 방법은 public constructor를 제공하는 것.
- 그 방법대신 static Factory Method로 전환하면 얻는 장점들이 많다.

### 장점
#### 생성자와 달리 자기 나름의 이름을 가질 수 있다는 것이다.
- Nesoy(int, int, String), Nesoy(int, int), Nesoy(int)
    - 생성자가 무슨 의미를 뜻하는 지 알 수 없다.
- Nesoy.getNesoyInfo(int, int, String)
    - 명확하게 어떤 객체를 가져오는지 알 수 있다.

#### 생성자와 달리 호출될 때마다 매번 새로운 객체를 생성할 필요가 없다는 것이다.
- immutable 클래스의 경우 이미 생성된 instance를 다시 반복 사용할 수 있다.
- Flyweight 패턴과 유사하다.
- 특히 객체 생성시 자원이나 시간이 많이 든다면 프로그램 성능을 크게 향상시킬 수 있다.
- instance-controlled 클래스라고 한다.
    - 싱글톤 또는 instance 생성 불가 클래스로 만들 수 있다.
    - immutable 클래스는 동일한 인스턴스(equals(), ==)가 생기지 않도록 해준다.
        - 이것이 보장된다면 equals()보다 비용이 저렴한 ==로 비교하여 성능을 항상 시킬 수 있다.


#### 자신의 클래스 인스턴스만 반환하는 생성자와 달리, static Factory Method는 자신이 반환하는 타입의 어떤 SubTye 객체도 반환할 수 있다는 것이다.
- 인터페이스 기반 프레임워크에 적합하다.

#### Service Provider Framework Ex : JDBC의 경우
- Service Interface
- Provider Registration
- Service Access
- Service Provider Interface

```java
//Service Interface
public interface Service {
    // 서비스 관련 메소드는 여기에
}

// 서비스 제공자 인터페이스
public interface Provider {
    Service newService();
}

// 서비스 등록과 접근을 위한 인스턴스 생성 불가능 클래스
public class Services {
    private Services() {} // 인스턴스 생성을 막는다.

    // 서비스명을 Map에 보존한다.
    private static final Map<String, Provider> providers = new ConcurrentHashMap<String, Provider>();
    public static final String DEFAULT_PROVIDER_NAME = "<def>";

    // 제공자 등록 API
    public static void registerDefaultProvider(Provider p){
        registerProvider(DEFAULT_PROVIDER_NAME, p);
    }

    public static void registerProvider(String name, Provider p){
        providers.put(name, p);
    }

    // 서비스 접근 API
    public static Service newInstance(){
        return newInstance(DEFAULT_PROVIDER_NAME);
    }
    public static Service newInstance(String name){
        Provider p = providers.get(name);
        if( p == null)
            throw new IllegalArgumentException("No Provider registered with name : " + name);
        return p.newService();
    }
}
```

- Adapter Pattern

#### 매개변수화 타입의 인스턴스를 생성하는 코드를 간결하게 해준다는 것이다.
- 타입 추론(Type inference)를 통한 간결화

```java
Map<String, List<String>> m = new HashMap<String, List<String>>();

// Type inference
public static <K, V> HashMap<K, V> newInstance(){
    return new HashMap<K, V>();
}

Map<String, List<String>> m = HashMap.newInstance();
```


### 단점
- Static 팩토리 메소드의 가장 큰 단점은, 인스턴스 생성을 위해 static 팩토리 메소드만 갖고 있으면서 public이나 Protected 생성자가 없는 클래스의 경우는 서브 클래스를 가질 수 없다는 것이다.



-------

## 생성자의 매개변수가 많을 때는 빌더(Builder)를 고려하자.

### 텔리스코핑 생성자(telescoping constructor)
```java
public Nesoy(int age, String gender, int height){
    this(age, gender, height, 50);
}
public Nesoy(int age, String gender, int height, int weight){
    this.age = age;
    this.gender = gender;
    this.height = height;
    this.weight = weight;
}
```

### Java Beans
- setter
    - 생성과 설정이 분리되어 있어 객체가 일관된 상태를 유지하지 못할 수 있다.
    - immutable 클래스를 만들 수 있는 가능성을 배제하므로 Thread-Safe하려면 많은 노력이 필요하다.
```java
public void setAge(int age);
public void setGender(String gender);
public void setHeight(int height);
public void setWeight(int weight);
```

### Builder Pattern
- 가독성도 좋고 생성되는 관점에서 설정하는 장점을 얻는다.
- Python언어에서 지원하는 named 매개변수처럼 동작한다.

```java
public class Nesoy{
    private int age;

    public static class Builder{
        public Builder age(int age){
            this.age = age;
            return this;
        }
        ....
    }
}
```

#### 단점
- 어떤 객체를 생성하기 위해선 빌더를 생성해야 하는 비용이 필요하다.
    - 그러나 매개변수가 추가될 수 있다는 것을 염두에 두면 나쁘지 않는 비용이다.

> 생성자나 static 팩토리 메소드에서 많은 매개변수를 갖게 될 클래스를 설계할 때는 빌더 패턴이 좋은 선택이다.




--------

## private 생성자나 enum 타입을 사용해서 싱글톤의 특성을 유지하자.

```java
public class Nesoy {
    public static final Nesoy INSTANCE = new Nesoy();
    private Nesoy() {}
}
```

- private 생성자는 딱 한번만 호출된다.

#### Factory Method Pattern
```java
public class Nesoy {
    public static final Nesoy INSTANCE = new Nesoy();
    private Nesoy() {}
    public static Nesoy getInstance() { return INSTACE; }
}
```

- 클래스에서 반환하는 싱글톤 인스턴스의 형태를 바꿀 수 있는 유연성을 제공한다는 것
    - 예를 들어 팩토리 메소드에서는 오직 하나의 Instance만 반환하지만 Thread마다 하나씩의 인스턴스를 반환하도록 쉽게 수정이 가능하다.
    - 제네릭 타입

#### 싱글톤 클래스는 어떻게 직렬화할까?
- implements Serializable을 추가해야 한다.
- 싱글톤을 보장하기 위해 모든 필드에 transient로 선언해야 하며, `readResolve` 메소드를 추가해야 한다.
    - 그렇지 않으면 deSerializable을 할 때마다 새로운 Object를 생성한다.
- `readResolve`는 stream에서 Object를 읽을 때 사용한다.
- <https://stackoverflow.com/questions/1168348/java-serialization-readobject-vs-readresolve>



--------

## Private 생성자를 사용해서 인스턴스 생성을 못하게 하자.
- `java.lang.Math`, `java.util.Arrays`와 같은 Utility 클래스들은 인스턴스를 생성하지 못하게 설계되었다.
- 하지만 명시적으로 지정한 생성자가 없을 때는 컴파일러가 Default Constructor를 만들기 때문에 인스턴스를 생성할 수 있다.

#### 이디엄(Idiom)
- 프로그램을 작성할 때 공통적으로 흔히 발생하는 문제의 해결을 위해 만들어진 코드 형태
- 프로그래밍 언어에 종속적이다.

```java
public class UtilityClass {
    private UtilityClass() {
        throw new AssertionError();
    }
}
```

--------

## 불필요한 객체의 생성을 피하자.
- 필요할 때마다 매번 새로 생성하기보다는 하나의 객체를 재사용하는 것이 좋을 때가 많다.

```java
String s = new String("Stringette"); // 호출될때마다 객체를 생성하는 Cost가 필요하다.
String s2 = "stringette"; // 문자열 literal를 갖도록 하여 재사용한다.
```

- `Boolean(String)`보다 팩토리 메소드 `Boolean.valueOf()` 사용하는 것이 더 바람직하다.
- 객체를 생성하는 Code를 확인하자!
    - 한번 선언하고 재사용할 수 있다면 그렇게 바꾸자.
    - 재사용 : static final로 변경하기.
- 재사용의 단점.
    - 객체가 사용되지 않는다면?
        - 재사용을 위해 미리 static으로 생성하여 불필요한 생성 Cost 발생.
    - 이를 방지하게 위해 Lazy initialization이 등장.
        - 복잡하고 구현하기 어려움. 성능 개선이 좋은 편은 아님.

- AutoBoxing, UnBoxing 또한 Cost발생.
    - 불필요한 Boxing을 제거한다면 성능 개선에 효과.

```java
public static void main(String[] args){
    Long sum = 0L;
    for (long i = 0; i < Integer.MAX_VALUE; i++){
        sum += i; // Occur Boxing Cost!!
    }
    System.out.println(sum);
}
```

### 여러 객체를 만들어 놓고 재사용하면 어떨까?
- Connection Pool를 하나의 예로 들어보자.
- Pool에 유지할 객체들이 대단히 무거워서 생성 비용이 많이 드는 것이라면 고려해 볼만하다.
- 일반적으로 우리가 Pool을 만들고 유지하고 할당과 해지를 제어한다는 것은 매우 어려운 일이다.

### 그렇다면 항상 불필요한 객체를 생성을 피하는 것이 정답인가?
- 대답은 No.
- 객체를 재사용함으로써 Side Effect가 발생할 경우 Bug를 찾는 Cost가 훨씬 비싸게 든다.
- 따라서 객체의 특성에 따라 결정해야 한다.
    - `immutable한 객체`

--------

## 쓸모 없는 객체 참조를 제거하자.
### Memory Leak를 찾아보자.

```java

public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if(size == 0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }
}
```

- Memory Leak가 발생하면 성능 저하의 형태로 서서히 나타난다.
    - GC의 작업 횟수가 증가 하거나
    - 메모리 할당과 회수가 빈번하게 생기기 때문
    - Memory Leak인하여 디스크상의 Paging이 생길 수 있으며 흔하지 않지만 OutOfMemoryError로 인해 프로그램 실행이 중단될 수 있다.

### Memory Leak Point
- Stack에서 pop을 하면 element에서 더이상 사용하지 않는다.
- 사용하지 않는 사실을 프로그래머만 알고 있지만. GC는 실제로 Reference Count가 0이 아니기 때문에 사용하는 것으로 인식한다.
- 따라서 이러한 사실을 GC에게 알려주기 위해 Reference를 지워주기 위해 null값을 넣는다.

```java
public Object pop(){
        if(size == 0)
            throw new EmptyStackException();
        Object reuslt = elements[--size]; // Occur Memory Leak
        elements[size] = null; // So Remove Reference & Working GC
        return result;
    }
```

- Memory Leak가 자주 발생하는 Case
    - Cache
        - WeakHashMap을 사용하자.
        - 키로 저장된 객체가 더 이상 참조되지 않을 때 해당 항목이 자동으로 삭제될 것이다.
        - LinkedHashMap의 removeEldestEntry Method를 사용해서 후자의 방법으로 처리한다.
    - Listener, Callback이다.
        - Callback을 등록하지만 제거하지 않는다면 콜백은 지속적으로 누적될 것이다.
        - Weak Reference를 사용하여 저장 유지하는 것이다.

### 어떻게 하면 Memory Leak를 찾을까?
- Heap Profiler를 사용하여 도움받기.
- 하지만 미리 예상하고 발생을 막는 방법이 Cost가 저렴하다.

- <https://d2.naver.com/helloworld/1326256>

### 실제 Stack pop() Method 코드

![No Image](/nesoy/Images/EffectiveJava/1.png)

![No Image](/nesoy/Images/EffectiveJava/2.png)

- 가장 마지막 Line에 GC에서 Reference Count가 0임을 알려주기 위해 null값을 넣는 것을 확인할 수 있다.


> Java에서는 Memory Management를 GC가 하여 프로그래밍하기 편하지만 사용하지 않는 객체의 Reference를 반환하는 연습을 통해 Memory Leak를 예방해보자.
--------

## 파이널라이저(finalizer)의 사용을 피하자.

- C++ 프로그래머들은 finalizer가 C++ 소멸자(destructor)의 자바 버젼의 소멸자라고 생각하면 안된다.
- Java에서는 사용할 수 없는 객체와 관련된 메모리를 가비지 컬렉터가 자동으로 재활용한다.

### 단점 & 주의할 점.
- 신속하게 실행된다는 보장이 없다는 것이다.
- 예를 들어 finalizer에서 파일을 닫는 것은 심각한 실수이다.
    - 열 수 있는 파일이 제한되어 있는 상황에서 언제 닫힐지 보장이 안되기 때문에 장애가 발생할 수 있다.
- 파이널라이저가 얼마나 빨리 실행되는가는 주로 가비지 컬렉션 알고리즘에 달려있으며, 그 알고리즘은 JVM에 따라 다양하다.
    - 우리가 테스트하던 JVM에서 완벽하게 실행되는 프로그램이 고객 컴퓨터에서는 오작동이 나올 수 있다.
- 신속하게 실행된다는 보장이 없는 것은 물론이고 반드시 실행 될 것인지도 보장하지 않고 있다.
- Persistent 상태를 변경하기 위해 finalizer를 사용하면 안된다.
    - 분산 시스템 전체를 멈추게 될 수 있다.
    - System.gc, System.runFinalization 메소드들을 사용하고픈 유혹에 빠지지 말자.
- 파이널라이저 내부에서 catch 잡히지 않는 예외가 발생하게 된다면 감지 못하고 종료되는 상황이 발생.
    - 경고조차도 출력되지 않는다.
- 엄청난 성능 저하가 발생한다.

### 해결하는 방법?
- try-finally와 함께 사용하여 확실하게 실행되도록 하자.

```java
Foo foo = new Foo();
try{
    // ....
} finally {
    foo.terminate(); // 종료 메소드 호출
}
```

### 그렇다면 어떤 경우에 사용하면 좋을까?
- 생성된 객체를 갖고 있는 코드에서 그 객체의 종료 메소드 호출을 빠뜨렸을 경우에 "안정망" 역할
- 그러나 자원의 사용이 완전히 끝나지 않은 경우에는 파이널라이저에서 경고 메시지로 기록해야 한다.
    - 이것은 반드시 수정해야 할 클라이언트 코드 결함이기 때문이다.
    - 추가 비용을 들여 그런 보호 코드를 만들 필요가 있는지 충분한 시간을 갖고 신중하게 생각하자.

- FileInputStream, FileOutputStream, Timer, Connection은 파이널라이저를 갖고 있어서 자신들의 종료 메소드가 호출되지 않을 경우 안정망의 역할을 한다.

![No Image](/nesoy/Images/EffectiveJava/3.png)


- 또 다른 경우는 Native Peer 객체 사용에 있다.
    - Native Peer Object가 소멸되면 가비지 컬렉터가 알지 못하며 재활용 할 수도 없다.
    - Native Peer가 빠른 시간 내에 종결되어야 할 자원을 갖고 있다면 클래스에서는 별도의 종료 메소드를 갖고 있어야 한다.

### 안전한 파이널라이저(finalizer)를 사용하려면?
- finalizer의 연쇄 호출(chaining)은 자동으로 실행되지 않는다는 것을 유의하자.
- 연쇄 호출이 안되기에 명시적으로 호출하자.
    - `super.finalizer();`
- public이고 final이 아닌 클래스인 경우 finalizer guardian을 작성하자.

### 정리하자면?
- 사용해야되는 Case는?
    - 종료 메소드 호출을 빼먹은 경우를 대비한 안전망의 역할
    - 중요하지 않은 네이티브 자원을 종결하는 경우 외에는 파이널라이저를 사용하지 말자.
- 어쩔 수 없이 사용해야 한다면?
    - public이고 final이 아닌 클래스에 finalizer가 필요하다면?
        - finalizer guardian을 작성하자.

--------


