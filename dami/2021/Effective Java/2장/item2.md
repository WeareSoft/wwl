# 아이템2 - 생성자에 매개변수가 많다면 빌더를 고려하라

정적 팩토 메소드와 생성자의 공통적인 제약은 클래스의 필드가 많아질수록 인자로 넘겨줘야 할 값의 개수와 순서가 상당히 복잡해지는 문제 발생

## 대안 1. 점층적 생성자 패턴 사용
생성자를 여러 개 오버로딩해서 사용하는 방법

### 단점
- 이 방법도 결국 마찬가지로 매개변수 개수가 많아질수록 복잡도 증가

<br />

## 대안 2. 자바 빈즈 패턴 사용
매개변수가 없는 생성자로 객체를 만든 후, 세터 메소드를 호출해 원하는 매개변수의 값을 설정하는 방법

### 단점
- 객체 하나를 만들기 위해 여러 메소드를 호출해야 하고, 객체가 완전히 생성되기 전까지는 일관성이 무너져 있는 상태
- 클래스를 불변으로 생성 불가하여 스레드 안전하려면 추가 작업 필요

<br />

### 대안 3. 빌더 패턴 사용
필요한 객체를 직접 만드는 대신
1. 필수 매개변수만으로 생성자(또는 정적 팩토리 메소드)를 호출해 빌더 객체를 얻고
2. 빌더 객체가 제공하는 세터 메소드로 원하는 매개변수를 설정하고
3. 마지막으로 ```build()``` 메소드를 호출해 객체를 얻어오는 방법

#### 사용 예시
```JAVA
public class NutritionFacts {
  private final int servingSize;
  private final int servings;
  private final int calories;
  private final int fat;
  private final int sodium;
  private final int carbohydrate;

  public static class Builder {
    // 필수 매개변수
    private final int servingSize;
    private final int servings;

    // 선택 매개변수 - 기본값으로 초기화
    private final int calories = 0;
    private final int fat = 0;
    private final int sodium = 0;
    private final int carbohydrate = 0;

    public Builder(int servingSize, int servings) {
      this.servingSize = servingSize;
      this.servings = servings;
    }

    public Builder calories(int val) {
      calories = val;
      return this;
    }

    public Builder fat(int val) {
      fat = val;
      return this;
    }

    public Builder sodium(int val) {
      sodium = val;
      return this;
    }

    public Builder carbohydrate(int val) {
      carbohydrate = val;
      return this;
    }

    public NutritionFacts build() {
      return new NutritionFacts(this);
    }
  }

  private NutritionFacts(Builder builder) {
    servingSize = builder.servingSize;
    servings = builder.servings;
    calories = builder.calories;
    fat = builder.fat;
    sodium = builder.sodium;
    carbohydrate = builder.carbohydrate;
  }
}
```
- ```NutritionFacts```는 불변이며, 모든 매개변수의 기본값을 한 곳에 모아둠

#### 플루언트 API or 메소드 연쇄
- 빌더의 세터 메소드들은 자기 자신을 반환하여 연쇄적으로 호출 가능
- 메소드 호출이 흐르듯 연결된다는 의미

#### 빌더 패턴 클라이언트 코드 사용 예시
```JAVA
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                                .calories(100)
                                .sodium(35)
                                .carbohydrate(27)
                                .build();
```

빌더 패턴은 파이선과 스칼라에 있는 **명명된 선택적 매개변수**를 흉내낸 것

유효성 검증을 하려면 빌더의 생성자와 메소드에서 입력값을 검사하고 잘못된 경우 Exception 던지기

### 빌더 패턴 활용
빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰면 좋음

추상 클래스는 추상 빌더를, 구체 클래스는 구체 빌더를 갖도록 설계

#### 사용 예시
피자의 다양한 종류를 표현하는 계층 구조의 루트에 놓인 추상 클래스
```JAVA
public abstract class Pizza {
  public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
  final Set<Topping> toppings;

  // Builder<T extends Builder<T>> : 재귀적 타입 한정(아이템 30)
  abstract static class Builder<T extends Builder<T>> {
    EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);

    public T addTopping(Topping topping) {
      toppings.add(Objects.requireNonNull(topping));
      return self();
    }

    abstract Pizza build();

    // 하위 클래스는 이 메소드를 재정의하여 "this"를 반환함으로써 형변환하지 않고도 메소드 연쇄 지원 가능
    // : 시뮬레이트한 셀프 타입
    protected abstract T self();
  }

  Pizza(Builder<?> builder) {
    toppings = builder.toppings.clone(); // 아이템 50 참조
  }
}
```

피자의 하위 클래스 2개
```JAVA
public class NyPizza extends Pizza {
  public enum Size { SMALL, MEDIUM, LARGE }
  private final Size size;

  public static class Builder extends Pizza.Builder<Builder> {
    private final Size size;

    public Builder(Size size) {
      this.size = Objects.requireNonNull(size);
    }

    @Override
    public NyPizza build() {
      return new NyPizza(this);
    }

    @Override
    protected Builder self() {
      return this;
    }
  }

  private NyPizza(Builder builder) {
    super(builder);
    size = builder.size;
  }
}
```
```JAVA
public class Calzone extends Pizza {
  private final boolean sauceInside;

  public static class Builder extends Pizza.Builder<Builder> {
    private boolean sauceInside = false;

    public Builder sauceInside() {
      sauceInside = true;
      return this;
    }

    @Override
    public Calzone build() {
      return new Calzone(this);
    }

    @Override
    protected Builder self() {
      return this;
    }
  }

  private Calzone(Builder builder) {
    super(builder);
    sauceInside = builder.sauceInside;
  }
}
```
- 각 하위 클래스가 재정의한 ```build()``` 메소드는 해당 구체 하위 클래스를 반환하도록 선언하는 **공변 반환 타이핑** 사용
  - 공변 반환 타이핑을 통해 클라이언트는 형변환을 신경쓰지 않고 빌더 사용 가능

클라이언트 코드
```JAVA
NyPizza pizza = new NyPizza.Builder(SMALL)
                      .addTopping(SAUSAGE)
                      .addTopping(ONION)
                      .build();

Calzone calzone = new Calzone.Builder()
                      .addTopping(HAM)
                      .sauceInside()
                      .build();
```


#### 빌더 패턴의 장점
유연한 사용 방법
- 빌더 하나로 여러 객체를 순회하며 생성 가능
- 빌더에 넘기는 매개변수에 따라 다른 객체 생성 가능
- 객체마다 부여되는 일련번호 같은 특정 필드를 빌더가 알아서 채우게 작성 가능

#### 빌더 패턴의 단점
- 빌더가 꼭 필요하기 때문에 성능에 민감한 상황이라면 문제가 될 수 있음
- 코드가 장황하기 때문에 매개변수가 4개 이상은 되어야 쓸모
