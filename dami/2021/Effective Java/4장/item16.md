# 아이템16 - public 클래스에서는 public 필드 대신 접근자 메소드를 사용하라

### public 클래스는 절대 가변 필드 직접 노출 금지
```JAVA
public class Point {
  public double x;
  public double y;
}
```
- 캡슐화의 장점 활용 불가능
- API(해당 필드를 사용한 외부 코드) 수정 없이는 내부 표현 변경 불가
- 불변식 미보장
- 외부에서 필드 접근 시 검증 같은 부수 작업 수행 불가

```JAVA
// 개선
public class Point {
  private double x;
  private double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() { return x; }
  public double getY() { return y; }

  public void setX(double x) { this.x = x; }
  public void setY(dobule y) { this.y = y; }
}
```

**만약 클래스가 package-private이나 ```private``` 중첩 클래스라면 인스턴스 필드를 ```public```으로 지정해도 무방**
- 앞서 말했듯 두 지정자의 경우 내부 구현이 되므로 패키지 바깥(또는 외부 클래스) 코드를 변경하지 않고도 내부 데이터 표현 방식 변경이 가능하기 때문

### 불변 필드라면 비교적 덜 위험하긴하지만 그래도 안심 불가능
- 불변식은 보장
- 여전히 API 변경 없이는 내부 표현 변경 불가
- 외부에서 필드 접근 시 검증 같은 부수 작업 수행 불가

```JAVA
// 예시
public final class Time {
  private static final int HOURS_PER_DAY = 24;
  private static final int MINUTES_PER_HOUR = 60;

  public final int hour;
  public final int minute;

  public Time(int hour, int minute) {
    if (hour < 0 || hour >= HOURS_PER_DAY) {
      throw new IllegalArgumentException("hour : " + hour);
    }
    if (minute < 0 || minute >= MINUTES_PER_HOUR) {
      throw new IllegalArgumentException("minute : " + minute);
    }

    this.hour = hour;
    this.minute = minute;
  }
}
```

### java.awt.package.Point / Dimension
- 자바 플랫폼 라이브러리에서 이번 아이템 규칙을 지키지 않은 사례
- 규칙을 어김으로써 발생한 심각한 성능 문제는 현재까지도 미해결
