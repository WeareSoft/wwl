# 아이템13 - clone 재정의는 주의해서 진행하라

## Cloneable
> 믹스인 인터페이스?

```JAVA
public interface Cloneable { }
```
복제해도 되는 클래스임을 명시하는 용도의 인터페이스

## clone 메소드
- ```clone``` 메소드는 접근제한자로 ```protected``` 사용
- **재정의하려면 ```Cloneable``` 인터페이스를 구현**해야 하는데, 이 메소드는 ```Object``` 클래스에 정의되어 있음
  - ```Cloneable``` 인터페이스에는 아무 것도 정의되어 있지 않음
  - ```Cloneable``` 인터페이스를 구현하여 ```clone```의 동작 방식이 결정됨
- ```Cloneable```을 구현하지 않고 ```clone``` 메소드 호출 시 ```CloneNotSupportedException``` 발생
  - ```CloneNotSupportedException```은 Checked Exception이기 때문에 반드시 예외처리 필요

다만, ```Cloneable```을 구현하여 ```clone```을 재정의할 경우 생성자를 호출하지 않고도 객체를 생성할 수 있게 되어 다소 위험하고 모순적인 매커니즘이 탄생


> p.78 마지막 문단 ~ p.79 첫번째 문단 이해 못함

### clone 예시 1. 클래스의 모든 필드가 기본 타입이거나 불변 타입인 경우(클래스가 가변 객체를 참조하지 않는 경우)
```JAVA
public final class PhoneNumber implements Cloneable {
  private final short areaCode, prefix, lineNum;  // 기본 타입

  // . . .

  @Override
  public PhoneNumber clone() {
    try {
      return (PhoneNumber) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(); // 절대 발생하지 않는 예외
    }
  }
}
```
- ```PhoneNumber```의 객체 복사 시 모든 필드가 새로 복사되기 때문에 문제 없음
- 반환 타입을 ```PhoneNumber```로 하여 클라이언트 쪽에서 형변환을 하지 않고 사용 가능
- 단, 쓸데없는 복사를 지양해야 한다는 관점에서 보면 불필요한 코드

### clone 예시 2. 클래스에 가변 객체를 참조하는 필드가 있는 경우
```JAVA
public class Stack {
  private Object[] elements;  // 가변 객체
  private int size = 0;
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  // . . .

  @Override
  public Stack clone() {
    try {
      Stack result = (Stack) super.clone();
      result.elements = elements.clone(); // 배열의 clone 메소드 사용
      return result;

    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
```
- 예시 1과 같이 단순히 ```Stack```의 복사본을 반환한다면, 원본의 ```elements```와 복사본의 ```elements```가 같은 배열을 참조하여 문제 발생
- 따라서, 위 코드처럼 배열의 ```clone``` 메소드를 사용하여 해결

**clone 메소드는 사실상 생성자와 같은 효과. 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장하도록 구현**하는 것이 중요


##
