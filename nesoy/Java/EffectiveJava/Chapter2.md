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