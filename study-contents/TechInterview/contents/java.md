# 7. Java
## 📖 Contents
- java 프로그래밍이란
- Java SE와 Java EE 애플리케이션 차이
- java와 c/c++의 차이점
- java 언어의 장단점
- java의 접근 제어자의 종류와 특징
- java의 데이터 타입
- Wrapper class
- OOP의 4가지 특징과 5대 원칙(SOLID)
  * 추상화(Abstraction), 캡슐화(Encapsulation), 상속(Inheritance), 다형성(Polymorphism)
- 객체지향 프로그래밍과 절차지향 프로그래밍의 차이
- 객체지향(Object-Oriented)이란
- java의 non-static 멤버와 static 멤버의 차이
  * Q. java의 main 메서드가 static인 이유
- java의 final 키워드 (final/finally/finalize)
- java의 제네릭(Generic)과 c++의 템플릿(Template)의 차이
- java의 가비지 컬렉션(Garbage Collection) 처리 방법
- java 직렬화(Serialization)와 역직렬화(Deserialization)란 무엇인가
- 클래스, 객체, 인스턴스의 차이
- 객체(Object)란 무엇인가
- 오버로딩과 오버라이딩의 차이(Overloading vs Overriding)
- Call by Reference와 Call by Value의 차이
- 인터페이스와 추상 클래스의 차이(Interface vs Abstract Class)
- JVM 구조
- Java Collections Framework
  * java Map 인터페이스 구현체의 종류
  * java Set 인터페이스 구현체의 종류
  * java List 인터페이스 구현체의 종류
- Annotation
- String, StringBuilder, StringBuffer
- 동기화와 비동기화의 차이(Syncronous vs Asyncronous)
- java에서 '=='와 'equals()'의 차이
- java의 리플렉션(Reflection) 이란

---
## OOP, SOLID
### 객체지향 프로그래밍(Object-Oriented Programming, OOP)
#### OOP 개념
- 프로그램 설계방법론
- 현실의 물체를 객체로 표현하고, 객체 간 관계와 상호작용을 코드로 표현
  - 객체? 하나의 역할을 수행하는 메소드와 변수의 묶음

#### OOP 특징
1. 클래스 + 인스턴스(객체)
  - 클래스 : 한 기능을 수행하는 집단의 **속성**과 **행위**를 **변수**와 **메소드**로 정의한 것
  - 인스턴스(객체) : 클래스 정의를 토대로 실제 메모리에 할당된 것
2. 추상화
  - 사물의 공통 특징을 파악하여 하나의 집합으로 다루는 것
  - 공통의 속성이나 기능을 묶어 이름을 붙이는 것
3. 캡슐화(Encapsulation)
  - 메소드와 변수를 하나의 단위로 묶는 것 (데이터 번들링, 클래스 = 캡슐)
  - 목적 : 수정 없이 재활 가능한 코드를 만드는 것
  - 정보 은닉
    - 프로그램의 세부 구현이 외부에 드러나지 않도록 감추는 것
    - 모듈 내 높은 응집도, 모듈 간 낮은 결합도를 유지하여 유연함과 유지보수성 증가
    - ex. 접근제한자(public, protected, private, (default))
4. 상속(Inheritance)
  - 부모 클래스의 속성과 기능을 이어받아 사용 가능
  - 기능 일부 변경 필요 시 상속 받은 자식 클래스에서 해당 기능만 다시 수정하여 사용 가능
  - 또 다른 캡슐화
    - 자식 클래스를 외부로부터 보호
  - 클래스의 일부 기능만 재사용하고 싶을 때 위임 사용
    - 자신이 직접 기능을 실행하지 않고 다른 클래스의 객체가 실행하게 함
    - 일반화 관계는 클래스간 관계 / 위임은 객체간 관계
5. 다형성(Polymorphism)
  - 하나의 변수 또는 메소드가 상황에 따라 다른 의미로 해석될 수 있는 것
  - 오버라이딩(Overriding)
    - 부모 클래스의 메소드와 같은 메소드를 재정의
  - 오버로딩(Overloading)
    - 같은 이름의 함수 여러 개 정의하고, 매개변수의 타입과 개수 차이
    
#### OOP 장점
- 재사용성
  - 하나의 클래스로 서로 다른 상태를 가진 인스턴스를 생성하면 각각 다른 동작 가능
  - 객체지향적으로 프로그래밍을 한다면 작성해둔 코드에 대한 재사용성이 높아져 자주 사용하는 로직을 라이브러리로 만들어두면 편리
  - 상속을 통해 확장 가능
- 쉬운 유지보수
  - ??
- 대형 프로젝트에 적합
  - 클래스단위로 모듈화가 가능하기 때문에 업무 분담 용이

#### OOP 단점
- 상대적으로 느린 처리 속도
- 객체가 상태를 갖기 때문에 문제 발생
  - 변수가 존재하고 변수를 통해 객체가 예측할 수 없는 상태를 가짐


### SOLID 원칙
- **SRP, Single Responsibility Principle (단일 책임 원칙)**
  - 소프트웨어의 설계, 부품은 단 하나의 책임만 가져야 한다.
- **OCP, Open-Closed Principle (개방 폐쇄 원칙)**
  - 기존 코드를 변경하지 않고 기능을 추가/변경 할 수 있어야 한다
  - 인터페이스(변하지 않는 것)에서 구체적인 출력 매체(변하는 것)를 캡슐화해 처리
  - 변하는 것은 쉽게 변할 수 있어야 하고, 변하지 않아야 할 것은 변하는 것에 영향 받지 않아야 함
  - 자식 클래는 부모 클래스의 책임을 무시하거나 재정의하지 않고 확장만 수행
- **LSP, Liskov Substitution Principle (리스코프 치환 원칙)**
  - 자식 클래스는 최소한 부모 클래스에서 가능한 행위를 수행할 수 있어야 한다.
  - 부모 클래스와 자식 클래스 간 행위에 일관성 필요
  - 일반화 관계 == "is a kind of"로 나타낼 수 있는 관계
  - 만족하는 가장 간단한 방법은 **재정의하지 않는 것**
- **DIP, Dependency Inversion Principle (의존성 역전 원칙)**
  - 의존 관계 맺을 때, 변화하기 어려운 것(인터페이스, 추상 클래스)에 의존해야 한다.
  - 클래스 간 의존 관계? 한 클래스가 어떤 기능 수행 시 다른 클래스의 기능이 필요한 경우
  - OCP가 되려면 DIP 만족 필요
  - DIP 만족 시 변화에 유연한 시스템
  - 의존성 주입(DI) 기술로 변화를 쉽게 수용할 수 있는 코드 작성
- **ISP, Interface Segregation Principle (인터페이스 분리 원칙)**
  - 클래스는 자신이 사용하지 않는 인터페이스는 구현하지 않아야 한다.
  - 인터페이스 최대한 쪼개 사용, 클라이언트에 특화되도록 분리
  - SRP와 밀접 (단, SRP를 만족한다고 반드시 ISP를 만족하지는 않음)
  - 예시

  | ISP 불만족 | ISP 만족 |
  |:----:|:----:|
  |<img src="./resources/isp-not-apply-example.png" style="width: 300px" alt="isp불만족">|<img src="./resources/isp-apply-example.png" style="width: 300px" alt="isp불만족">|
    - fax 기능은 프린터나 복사 클라이언트에서 미사용
    - 프린터, 복사, 팩스를 인터페이스로 만들어 복합기 상속 처리

### Reference
- [객체 지향 프로그래밍](https://namu.wiki/w/%EA%B0%9D%EC%B2%B4%20%EC%A7%80%ED%96%A5%20%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D)
- [객체 지향 프로그래밍이 뭔가요?](https://jeong-pro.tistory.com/95)
- [[Java] OOP(객체지향 프로그래밍) 설계 원칙](https://gmlwjd9405.github.io/2018/07/05/oop-solid.html)
---
