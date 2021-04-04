# 아이템17 - 변경 가능성을 최소화하라

## 불변 클래스로 만들기
자바가 제공하는 대표적인 불변 클래스
- String
- 기본 타입의 박싱 클래스들
- BigInteger
- BigDecimal

### 불변 클래스의 장점
- 가변 클래스보다 설계, 구현 및 사용하기가 용이
- 오류 발생 여지가 적고 훨씬 안전

### 불변 클래스를 만드는 규칙
- 객체의 상태를 변경하는 메소드(변경자) 미제공
- 클래스 확장 불가능하게 구현
  - 클래스를 `final`로 선언
  - 다른 방법은 뒤에서 설명
- 모든 필드를 `final` 선언
  - 새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건네도 문제없이 동작함을 보장
- 모든 필드를 `private` 선언
- 자신 외에는 내부 가변 컴포넌트에 접근하지 못하도록 제한
  - 생성자, 접근자, readObject 메소드(아이템88) 모두에서 방어적 복사 수행

단, 위 규칙이 다소 과하다고 판단될 경우 어떤 메소드도 **객체의 상태 중 외부에 노출되는 값을 변경할 수 없음**으로 완화해서 융통성있게 사용

### 예시 - 불변 복소수 클래스
```JAVA
public final class Complex {
  private final double re;
  private final double im;

  public Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public double realPart() { return re; }
  public double imaginaryPart() { return im; }

  public Complex plus(Complex c) {
    return new Complex(re + c.re, im + c.im);
  }

  public Complex minus(Complex c) {
    return new Complex(re - c.re, im - c.im);
  }

  public Complex times(Complex c) {
    return new Complex(re * c.re - im * c.im,
                       re * c.im + im * c.re);
  }

  public Complex dividedBy(Complex c) {
    double tmp = c.re * c.re + c.im * c.im;
    return new Complex((re * c.re + im * c.im) / tmp
                       (im * c.re - re * c.im) / tmp);
  }

  @Override public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Complex))
      return false;
    Complex c = (Complex) o;
    // Double은 equals 대신 compare 사용하는 것 다시 복기
    return Double.compare(c.re, re) == 0
        && Double.compare(c.im, im) == 0;
  }

  @Override public int hashCode() {
    return 31 * Double.hashCode(re) + Double.hashCode(im);
  }

  @Override public String toString() {
    return "(" + re + " + " + im + "i)";
  }
}
```
- 사칙연산 메소드는 자기 자신이 아닌 새로 생선한 인스턴스를 반환
- 피연산자에 함수를 적용해 그 결과를 반환하지만 피연산자 자체는 그대로인 프로그래밍 패턴 == **함수형 프로그래밍** (??)
  - 절차적/명령형 프로그래밍은 메소드에서 피연산자인 자신을 수정해 자신의 상태를 변경
  - 메소드 이름으로 동사(add) 대신 전치사(plus)를 사용한 점도 자기 자신을 변경시키지 않는다는 점을 강조

## 불변 객체의 특징
### 불변 객체는 단순
- 생성된 시점의 상태를 파괴될 때까지 그대로 간직
- 반면 가변 객체는 변경자 메소드가 일으키는 상태 전이를 문서로 남겨두지 않으면 믿고 사용하기 어려울 수 있음

### 불변 객체는 근본적으로 스레드 안전하여 별도 동기화가 불필요
- 여러 스레드가 동시에 사용해도 절대 훼손되지 않으며, 클래스를 스레드 안전하게 만드는 가장 쉬운 방법
- **안심하고 공유 가능**

### 한 번 만든 인스턴스를 최대한 재활용
- 자주 쓰이는 값은 **상수**로 제공
- 자주 사용하느 인스턴스를 캐싱하여 사용하게 해주는 **정적 팩토리** 제공
  - 여러 클라이언트가 인스턴스를 공유하여 메모리 사용량과 가비지 컬렉션 비용 이득
- 인스턴스를 재활용한다는 것은 방어적 복사도 불필요하다는 의미(복사해도 원본과 동일한 인스턴스)
  - 따라서 `clone()` 메소드나 복사 생성자가 불필요

### 불변 객체는 자유롭게 공유할 수 있으며, 불변 객체끼리는 내부 데이터 공유 가능
ENUM

### 객체 만들 때 다른 불변 객체를 구성요소로 사용하면 이득
- 구조가 복잡해져도 불변식을 유지하기 쉽기 때문인데 대표적으로 Map의 키와 Set의 원소로 불변 객체가 적절

### 불변 객체는 그 자체로 실패 원자성 제공 (아이템76)
- 실패 원자성이란 메소드에서 예외가 발생한 후에도 해당 객체는 이전과 똑같이 유효한 상태여야 함을 의미
- 불변 객체의 메소드는 내부 성질을 변경하지 않으니 실패 원자성을 만족

## 불변 클래스의 단점
특정 상황에서 잠재적으로 **비효율적**
- 백만 비트짜리 원본 인스턴스와 단 한 비트만 다른 또다른 백만 비트짜리 인스턴스를 생성해야 하는 경우, 꽤 높은 시간과 공간 비용 필요
- 원하는 객체를 최종 생성하기까지의 단계가 많고, 중간 단계에서 생성되는(사용하지 않을) 객체들이 많다면 성능 문제 야기
  - 대처 방법 1. 다단계 연산을 예측하여 한 단계의 기본 기능으로 제공
  - 대처 방법 2. 가변 동반 클래스 사용
    - 예 : `String`, `StringBuilder`, `StringBuffer`

## 불변 클래스 설계 방법
불변임을 보장하는 가장 간단한 방법은 `final` 클래스로 선언하는 것

더 유연한 방법은 모든 생성자를 `private` 또는 package-private으로 선언 후 **정적 팩토리 메소드를 제공**하는 방법

### 예시 - 정적 팩토리 방식으로 개선한 불변 복소수 클래스
```JAVA
public final class Complex {
  private final double re;
  private final double im;

  private Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public static Complex valueOf(double re, double im) {
    return new Complex(re, im);
  }

  // 나머지 생략
}
```

### 주의할 점
불변 객체의 중요성을 느끼지 못하고 설계된 `BigInteger`와 `BigDecimal`의 메소드는 모두 재정의가 가능한 상태이기 때문에 신뢰할 수 없는 클라이언트로부터 이 두 타입을 인자로 받는다면 주의 필요
- 신뢰할 수 없다면 가변이라고 가정하고 방어적 복사 사용
  ```JAVA
  public static BigInteger safeInstance(BigInteger val) {
    return val.getClass() == BigInteger.class ?
            val : new BigInteger(val.toByteArray());
  }
  ```

## 요약
- 클래스는 꼭 필요한 경우가 아니라면 불변으로 정의
  - 성능때문에 어쩔수 없다면 가변 동반 클래스를 제공
- 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분은 최소화
  - 객체의 상태 수 줄이기(변경해야 하는 필드 제외 `final` 선언)
  - 합당한 이유가 없다면 `private final` 선언
- 생성자는 불변식 설정이 모두 완료된(초기화가 완벽히 끝난) 상태의 객체 생성
  - 생성자와 정적 팩토리 외 초기화 메소드는 `public`으로 제공 금지

예시로 `java.util.concurrent` 패키지의 `CountDownLatch` 클래스 확인해보기
