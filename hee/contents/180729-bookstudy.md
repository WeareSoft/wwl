## [JAVA 객체지향 디자인 패턴, 한빛미디어](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9788968480911&orderClick=JAj) 학습

### 데커레이터 패턴
* **객체의 결합** 을 통해 **기능을 동적으로 유연하게 확장** 할 수 있게 해주는 패턴
  * 즉, 기본 기능에 추가할 수 있는 기능의 종류가 많은 경우에 **각 추가 기능을 Decorator 클래스로 정의** 한 후 필요한 Decorator 객체를 조합함으로써 **추가 기능의 조합을 설계** 하는 방식이다.
    * Ex) 기본 도로 표시 기능에 차선 표시, 교통량 표시, 교차로 표시, 단속 카메라 표시의 4가지 추가 기능이 있을 때 추가 기능의 모든 조합은 15가지가 된다.
    * -> 데코레이터 패턴을 이용하여 필요 추가 기능의 조합을 동적으로 생성할 수 있다.
  * '구조(Structural) 패턴'의 하나
* 기본 기능에 추가할 수 있는 많은 종류의 부가 기능에서 파생되는 다양한 조합을 동적으로 구현할 수 있는 패턴이다.
* 역할이 수행하는 작업
  * Component
    * 기본 기능을 뜻하는 ConcreteComponent와 추가 기능을 뜻하는 Decorator의 공통 기능을 정의
    * 즉, 클라이언트는 Component를 통해 실제 객체를 사용함
  * ConcreteComponent
    * 기본 기능을 구현하는 클래스
  * Decorator
    * 많은 수가 존재하는 구체적인 Decorator의 공통 기능을 제공
  * ConcreteDecoratorA, ConcreteDecoratorB
    * Decorator의 하위 클래스로 기본 기능에 추가되는 개별적인 기능을 뜻함
    * ConcreteDecorator 클래스는 ConcreteComponent 객체에 대한 참조가 필요한데, 이는 Decorator 클래스에서 Component 클래스로의 '합성(composition) 관계'를 통해 표현됨

**Reference**
> - [https://gmlwjd9405.github.io/2018/07/09/decorator-pattern.html](https://gmlwjd9405.github.io/2018/07/09/decorator-pattern.html)

### 템플릿 메서드 패턴
* 템플릿 메서드 패턴이란

**Reference**
> - []()

### 팩토리 메서드 패턴
* 팩토리 메서드 패턴이란

**Reference**
> - []()

### 추상팩토리 메서드 패턴
* 추상팩토리 메서드 패턴이란

**Reference**
> - []()

### 컴퍼지트 패턴
* 컴퍼지트 패턴이란

**Reference**
> - []()


### :house: [Hee Home](https://github.com/T-WWL/WWL/tree/master/hee)
