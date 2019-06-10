# [item 83] 지연 초기화는 신중히 사용하라
### 초기화 순환성(initialization circularity)
[참고 문서 - [Blch05, 퍼즐 51]](https://doc.lagout.org/programmation/Java/Java%20Puzzlers_%20Traps%2C%20Pitfalls%2C%20and%20Corner%20Cases%20%5BBloch%20%26%20Gafter%202005-07-04%5D.pdf) - 115p 참고
``` java
public class ColorPoint extends Point {
    private final String color;

    ColorPoint(int x, int y, String color) {
        super(x, y); // (1) 부모 생성자 호출
        this.color = color; // (4) 이제서야 color 할당
    }

    protected synchronized String makeName() {
        return super.makeName() + ":" + color; // (3) 여기서 color 값은 아직 null.
    }

    public static void main(String[] args) {
        System.out.println(new ColorPoint(4, 2, "purple")); // 과연 무엇을 출력할까?
    }
}

class Point {
    private final int x, y;
    private final String name;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
        name = makeName(); // (2) 자식에서 재정의 된 메서드 호출
    }

    protected String makeName() {
        return "[" + x + "," + y + "]";
    }

    public final String toString() {
        return name;
    }
}
```
### 홀더 클래스(lazy initialization) 관용구
#### static 클래스와 지연 초기화
- https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    - https://openjdk.java.net/jeps/8209964
    - https://docs.oracle.com/javase/specs/jls/se8/html/jls-12.html#jls-12.4
### 이중검사(double-check) 관용구
  - 이중검사 시, 따로 지역변수를 선언하여 확인하는 이유
    > http://wonwoo.ml/index.php/post/category/effective-java
    - 나도 잘 모르겠다. [#](#스터디-요약)

### 싱글톤 패턴
홀더 클래스와 이중검사 관용구는 싱글톤 패턴에서도 곧잘 사용된다.
- https://blog.seotory.com/post/2016/03/java-singleton-pattern
- https://limkydev.tistory.com/67 

### 스터디 요약
- 이중검사 시, 따로 지역변수를 선언하여 확인하는 이유
    - 원래 변수는 `volatile`로 선언된 필드이며, 이 데이터를 읽고쓰기 위헤서는 캐시가 아닌 직접 메인 메모리에 접근해야한다.
    - 반복적으로 접근하는 변수이므로 지역변수로 따로 할당하여 io의 손실을 줄이기 때문에 더 **빠르고** **우아한** 코드가 아닐까
- 내부 전역 클래스의 초기화 시점에 대하여
