# 아이템10 - equals는 일반 규약을 지켜 재정의하라

### equals를 재정의해야 하는 경우
각 객체의 고유 식별이 아니라 '논리적 동치성'을 확인해야 하는데, 상위 클래스의 ```equals```가 논리적 동치성을 비교하도록 재정의되어 있지 않은 경우
> - '논리적 동치성' : 두 문장이나 수식이 논리적으로 같은 것. 두 인스턴스의 모든 필드가 가진 '값'이 서로 동일한 경우를 의미
> - '물리적 동치성' : 인스턴스 고유 식별성을 의미(실제 저장된 '주소'값)

- 주로 값 클래스들이 이 경우에 해당(```Integer```, ```String```)
- ```equals```를 재정의함으로써 값의 동치성을 비교하면 객체를 ```Map```의 키나 ```Set```의 원소로도 사용 가능

<br />

### equals를 재정의하지 않아도 되는 경우
값 클래스라해도, 값이 같은 인스턴스가 둘 이상 만들어지지 않음이 보장되는 경우 (논리적으로 동치인 인스턴스가 둘 이상 만들어지지 않는 경우, 논리적 동치성과 객체 식별성이 똑같은 의미)
- 정적 팩토리 메소드를 활용해 인스턴스 통제 클래스를 만들 때
- ```Enum```

<br />

### equals를 재정의하면 안되는 경우
재정의하기 쉬워보이지만 중간중간 함정이 있기 때문에 주의해야 하며, 재정의하지 않는 것이 최선일 때가 있음
- **각 인스턴스가 본질적으로 고유한 경우**
  - 값을 표현하는게 아니라 동작하는 객체를 표현하는 클래스일 때
  - ex. ```Thread```
- **인스턴스의 '논리적 동치성'을 검사할 필요가 없을 경우**
  - ex. ```Pattern``` : ```equals```를 재정의해서 두 ```Pattern```의 인스턴스가 같은 정규표현식을 나타내는지 검사
- **상위 클래스에서 재정의한 ```equals```가 하위 클래스에도 알맞을 경우**
  - ex. 대부분의 ```Set``` 구현체는 ```AbstractSet```의 ```equals```를 상속받아 사용(```List```, ```Map```도 동일)
- **클래스가 ```private```이거나 package-private이고, ```equals``` 메소드를 호출할 일이 없는 경우**
  - 만약 ```equals```가 실수로라도 호출되는 것을 막고 싶다면
  ```JAVA
  @Override
  public boolean equals(Object o) {
    throw new AssertionError(); // 호출 금지
  }
  ```

<br />

## equals 일반 규약
- **반사성(reflexivity)** : ```null```이 아닌 모든 참조 값 ```x```에 대해, ```x.equals(x)```는 ```true```
- **대칭성(symmetry)** : ```null```이 아닌 모든 참조 값 ```x```, ```y```에 대해, ```x.equals(y)```가 ```true```면 ```y.equals(x)```도 ```true```
- **추이성(transitivity)** : ```null```이 아닌 모든 참조 값 ```x```, ```y```, ```z```에 대해 ```x.equals(y)```가 ```true```고 ```y.equals(z)```도 ```true```면 ```x.equals(z)```도 ```true```
- **일관성(consistency)** : ```null```이 아닌 모든 참조 값 ```x```, ```y```에 대해, ```x.equals(y)```를 반복해서 호출하면 항상 ```true```를 반환하거나 항상 ```false```를 반환
- **null-아님** : ```null```이 아닌 모든 참조 값 ```x```에 대해, ```x.equals(null)```은 ```false```

규약을 어기면 프로그램이 이상하게 동작할 수 있고, 문제 원인을 찾기도 어려울 것

<br />

### 반사성
객체는 자기 자신과 같아야 한다.

일부러가 아닌 이상 어기기 어려운 특성

<br />

### 대칭성
두 객체는 서로에 대한 동치 여부에 똑같은 결과를 반환한다.

#### 위반 예시
알파벳 대소문자 구분 없이 비교하는 타입 ```CaseInsensitiveString```의 ```equals```는 대칭성을 어길 가능성이 있음
```JAVA
public final class CaseInsensitiveString {
  private final String s;

  public CaseInsensitiveString(String s) {
    this.s = Objects.requireNonNull(s);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof CaseInsensitiveString) {
      return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
    }

    // 대칭성 위배
    if (o instanceof String) {
      return s.equalsIgnoreCase((String) o);
    }

    return false;
  }
}
```
```JAVA
CaseInsensitiveString cis = new CaseInsensitiveString("Apple");
String s = "apple";
```
- ```cis.equals(s)```는 ```true```
- ```s.equals(cis)```는 ```false```
- 대칭성 위반
- 따라서 아예 다른 타입인 ```CaseInsensitiveString```에서는 ```String```의 ```equals```와 연동하려고 하지 말 것

```JAVA
// 대칭성 규약을 지키도록 변경
public boolean equals(Object o) {
  return o instanceof CaseInsensitiveString && ((CaseInsensitiveString) o).s).equalsIgnoreCase(s);
}
```

<br />

### 추이성
첫 번째 객체와 두 번째 객체가 같고, 두 번째 객체와 세 번째 객체가 같으면, 첫 번째 객체와 세 번째 객체도 같다.

#### 위반 예시
상위 클래스에 없는 새로운 필드를 하위 클래스에 추가하는 경우 (equals 비교에 영향 주는 정보가 추가되는 것)
- 단, 상위 클래스를 직접 인스턴스로 만드는게 불가능하다면 상관 없음
```JAVA
public class Point {
  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Point)) {
      return false;
    }

    Point p = (Point)o;
    return p.x == x && p.y == y;
  }
}
```
```JAVA
// Point를 상속받은 ColorPoint에 Color 필드 추가
public class ColorPoint extends Point {
  private final Color color;

  public ColorPoint(int x, int y, Color color) {
    super(x, y);
    this.color = color;
  }
}
```

<br />

#### 위반 1.
```JAVA
ColorPoint point1 = new ColorPoint(1, 2, Color.RED);
ColorPoint point2 = new ColorPoint(1, 2, Color.BLUE);

point1.equals(point2); // true
point2.equals(point1); // true
```
- ```ColorPoint```가 ```equals```를 재정의하지 않으면 색상 정보를 무시한 채 비교

#### 위반 2. 대칭성 위배
```JAVA
// ColorPoint에서 재정의
@Override
public boolean equals(Object o) {
  if (!(o instanceof ColorPoint)) {
    return false;
  }

  return super.equals(o) && ((ColorPoint) o).color == color;
}
```
```JAVA
Point point1 = new Point(1, 2);
ColorPoint point2 = new ColorPoint(1, 2, Color.BLUE);

point1.equals(point2); // true
point2.equals(point1); // false
```
- ```Point```에는 색상 정보가 없기 때문에 ```ColorPoint```에서 재정의한 ```equals```와 비교 시 문제

#### 위반 3. 추이성 위배
```JAVA
// ColorPoint에서 재정의
@Override
public boolean equals(Object o) {
  if (!(o instanceof Point)) {
    return false;
  }

  // o가 Point면 Color는 무시하고 비교
  if (!(o instanceof ColorPoint)) {
    return o.equals(this);
  }

  // o가 ColorPoint면 모든 필드 비교
  return super.equals(o) && ((ColorPoint) o).color == color;
}
```
```JAVA
ColorPoint point1 = new ColorPoint(1, 2, Color.RED);
Point point2 = new Point(1, 2);
ColorPoint point3 = new ColorPoint(1, 2, Color.BLUE);

point1.equals(point2); // true
point2.equals(point3); // true
point1.equals(point3); // false
```

추가로 ```instanceof``` 대신 ```getClass``` 메소드로 비교한다면 해당 클래스와 일치할 때만 비교할 수 있기 때문에 **리스코프 치환 원칙**에 위배
- 리스코프 치환 원칙 : 상위 타입의 모든 메소드는 하위 타입에서도 똑같이 작동해야 한다. => ```Point```의 하위 클래스는 정의상 여전히 ```Point```이므로 어디서든 ```Poin```로 활용 가능해야 함

<br />

#### 해결 방법
상위 클래스를 상속받아 확장하면서 ```equals``` 규약을 만족시킬 방법은 없음

대신, **'상속 대신 컴포지션을 사용하라'**(아이템18)에 따르면 우회 가능
- ```ColorPoint```가 ```Point```를 상속하는 대신 ```private``` 필드로 정의하여 사용
  ```JAVA
  public class ColorPoint {
    private final Point point;
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
      point = new Point(x, y);
      this.color = Objects.requireNonNull(color);
    }

    // ColorPoint의 Point 뷰를 반환
    public Point asPoint() {
      return point;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof ColorPoint)) {
        return false;
      }

      ColorPoint cp = (ColorPoint) o;
      return cp.point.equals(point) && cp.color.equals(color);
    }
  }
  ```

<br />

### 일관성
두 객체가 같다면 어느 하나가 수정되지 않는 한 앞으로도 영원히 같아야 한다.

가변 객체는 비교 시점에 따라 서로 다를 수도 같은 수도 있고, 불변 객체는 한 번 다르면 끝까지 달라야 함

클래스가 불변이든 가변이든 ```equals``` 판단에 신뢰할 수 없는 자원이 끼어들지 못하게 제한할 것

<br />

### null-아님
null 체크를 할 때 ```instanceof```를 사용하면 null 검사까지 해주기 때문에 ```if (o == null)```과 같은 검사는 하지 않아도 됨

<br />

### equals 재정의 단계 요약
1. == 연산자를 사용해 입력이 자기 자신의 참조인지 확인
    - 비교 작업이 복잡한 상황일 때 유용
2. ```instanceof``` 연산자로 입력이 올바른 타입인지 확인
    - 같은 인터페이스를 구현한 서로 다른 클래스끼리도 비교할 수 있도록 작성(Set, List, Map 등)
3. 입력을 올바른 타입으로 형변환
4. 입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 하나씩 검사

- ```float```, ```double``` 필드 비교 시에는 정확도를 위해 ```Float.compare(float, float)```, ```Double.compare(double, double)```을 사용
- 배열 비교 시에는 ```Arrays.equals``` 메소드 사용

```JAVA
public final class PhoneNumber {
  private final short areaCode, prefix, lineNum;

  // . . .

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof PhoneNumber)) {
      return false;
    }

    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
  }
}
```

## 재정의 후 테스트
대칭성, 추이성, 일관성을 잘 지키는지 테스트 코드 작성 필요(반사성과 null-아님이 문제되는 경우는 거의 없음)

### AutoValue
구글에서 만들었으며, 저자피셜 가장 깔끔하게 재정의 코드를 생성해주는 라이브러리

또는 IDE가 제공하는 자동완성 기능을 활용하는 것도 좋은 방법

<br />

## 주의사항
- ```equals```를 재정의할 땐 반드시 ```hashCode```도 같이 재정의 (아이템11)
- 너무 복잡하게 해결하려하지 말고, 필드의 동치성만 검사해도 충분
  - 일반적으로 별칭은 검사하지 않음
  - ex. ```File``` 클래스 같은 경우, 심볼릭 링크로 동치성 검사를 하지 않음
- 재정의할 때 반드시 ```@Override```를 붙이고, 메소드 인자에 ```Object```가 아닌 다른 타입을 작성하지 말 것
