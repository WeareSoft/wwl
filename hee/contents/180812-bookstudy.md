## [JAVA 객체지향 디자인 패턴, 한빛미디어](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9788968480911&orderClick=JAj) 학습

### 템플릿 메서드 패턴
* 어떤 작업을 처리하는 일부분을 **서브 클래스로 캡슐화해** 전체 일을 수행하는 구조는 바꾸지 않으면서 특정 단계에서 수행하는 내역을 바꾸는 패턴
  * 즉, **전체적으로는 동일하면서 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화** 할 때 유용하다.
  * 다른 관점에서 보면 동일한 기능을 상위 클래스에서 정의하면서 확장/변화가 필요한 부분만 서브 클래스에서 구현할 수 있도록 한다.
  * 예를 들어, 전체적인 알고리즘은 상위 클래스에서 구현하면서 다른 부분은 하위 클래스에서 구현할 수 있도록 함으로써 전체적인 알고리즘 코드를 재사용하는 데 유용하도록 한다.
  * '행위(Behavioral) 패턴'의 하나
* 역할이 수행하는 작업
  * AbstractClass
    * 템플릿 메서드를 정의하는 클래스
    * 하위 클래스에 공통 알고리즘을 정의하고 하위 클래스에서 구현될 기능을 primitive 메서드 또는 hook 메서드로 정의하는 클래스
  * ConcreteClass
    * 물려받은 primitive 메서드 또는 hook 메서드를 구현하는 클래스
    * 상위 클래스에 구현된 템플릿 메서드의 일반적인 알고리즘에서 하위 클래스에 적합하게 primitive 메서드나 hook 메서드를 오버라이드하는 클래스

**Reference**
> -  [https://gmlwjd9405.github.io/2018/07/13/template-method-pattern.html](https://gmlwjd9405.github.io/2018/07/13/template-method-pattern.html)


### 팩토리 메서드 패턴
* **객체 생성 처리를 서브 클래스로 분리** 해 처리하도록 캡슐화하는 패턴
  * 즉, 객체의 생성 코드를 별도의 클래스/메서드로 분리함으로써 객체 생성의 변화에 대비하는 데 유용하다.
  * 특정 기능의 구현은 개별 클래스를 통해 제공되는 것이 바람직한 설계다.
    * 기능의 변경이나 상황에 따른 기능의 선택은 해당 객체를 생성하는 코드의 변경을 초래한다.
    * 상황에 따라 적절한 객체를 생성하는 코드는 자주 중복될 수 있다.
    * 객체 생성 방식의 변화는 해당되는 모든 코드 부분을 변경해야 하는 문제가 발생한다.
  * [스트래티지 패턴](https://gmlwjd9405.github.io/2018/07/06/strategy-pattern.html), [싱글턴 패턴](https://gmlwjd9405.github.io/2018/07/06/singleton-pattern.html), [템플릿 메서드 패턴](https://gmlwjd9405.github.io/2018/07/13/template-method-pattern.html)을 사용한다.
  * '생성(Creational) 패턴'의 하나
* 역할이 수행하는 작업
  * Product
    * 팩토리 메서드로 생성될 객체의 공통 인터페이스
  * ConcreteProduct
    * 구체적으로 객체가 생성되는 클래스
  * Creator
    * 팩토리 메서드를 갖는 클래스
  * ConcreteCreator
    * 팩토리 메서드를 구현하는 클래스로 ConcreteProduct 객체를 생성
* 팩토리 메서드 패턴의 개념과 적용 방법
  1. 객체 생성을 전담하는 별도의 **Factory 클래스 이용**
  2. **상속 이용**: 하위 클래스에서 적합한 클래스의 객체를 생성

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/07/factory-method-pattern.html](https://gmlwjd9405.github.io/2018/08/07/factory-method-pattern.html)


### 추상 팩토리 메서드 패턴
* 구제적인 클래스에 의존하지 않고 서로 연관되거나 의존적인 객체들의 조합을 만드는 인터페이스를 제공하는 패턴
  * 즉, 관련성 있는 여러 종류의 객체를 일관된 방식으로 생성하는 경우에 유용하다.
  * [싱글턴 패턴](https://gmlwjd9405.github.io/2018/07/06/singleton-pattern.html), [팩토리 메서드 패턴](https://gmlwjd9405.github.io/2018/08/07/factory-method-pattern.html)을 사용한다.
  * '생성(Creational) 패턴'의 하나
* 역할이 수행하는 작업
  * AbstractFactory
    * 실제 팩토리 클래스의 공통 인터페이스
  * ConcreteFactory
    * 구체적인 팩토리 클래스로 AbstractFactory 클래스의 추상 메서드를 오버라이드함으로써 구체적인 제품을 생성한다.
  * AbstractProduct
    * 제품의 공통 인터페이스
  * ConcreteProduct
    * 구체적인 팩토리 클래스에서 생성되는 구체적인 제품

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/08/abstract-factory-pattern.html](https://gmlwjd9405.github.io/2018/08/08/abstract-factory-pattern.html)


### 컴퍼지트 패턴
* 여러 개의 객체들로 구성된 복합 객체와 단일 객체를 클라이언트에서 구별 없이 다루게 해주는 패턴
  * 즉, **전체-부분의 관계를 갖는 객체들** 사이의 관계를 정의할 때 유용하다.
  * 또한 클라이언트는 전체와 부분을 구분하지 않고 **동일한 인터페이스** 를 사용할 수 있다.
  * '구조(Structural)'의 하나
* 역할이 수행하는 작업
  * Component
    * 구체적인 부분
    * 즉 Leaf 클래스와 전체에 해당하는 Composite 클래스에 공통 인터페이스를 정의
  * Leaf
    * 구체적인 부분 클래스
    * Composite 객체의 부품으로 설정
  * Composite
    * 전체 클래스
    * 복수 개의 Component를 갖도록 정의
    * 그러므로 복수 개의 Leaf, 심지어 복수 개의 Composite 객체를 부분으로 가질 수 있음

**Reference**
> - [https://gmlwjd9405.github.io/2018/08/10/composite-pattern.html](https://gmlwjd9405.github.io/2018/08/10/composite-pattern.html)



### :house: [Hee Home](https://github.com/T-WWL/WWL/tree/master/hee)
