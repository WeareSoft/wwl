# [item 85] 자바 직렬화의 대안을 찾으라

### 사전 조사
#### 직렬화란
- 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부의 자바 시스템에서도 사용할 수 있도록 바이트(byte) 형태로 데이터 변환하는 기술과 바이트로 변환된 데이터를 다시 객체로 변환하는 기술(역직렬화)
- 시스템적으로 이야기하자면 JVM(Java Virtual Machine)의 메모리에 상주(힙 또는 스택)되어 있는 객체 데이터를 바이트 형태로 변환하는 기술과 직렬화된 바이트 형태의 데이터를 객체로 변환해서 JVM으로 상주시키는 형태를 같이 이야기한다.

#### 직렬화가 가능한 객체의 조건
- 원시타입 객체(boolean, char, byte, short, int, long, float, double)
- Serializable 인터페이스를 implements 한 오브젝트
- Serializable 인터페이스를 implements 한 클래스나 인터페이스를 상속/구현 한 오브젝트

#### 직렬화의 위험성
- 보이지 않는 생성자, 잠재적인 정확성 문제, 성능, 보안, 유지보수성 등의 위험성이 크다.
- 공격범위가 너무 넓고 지속적으로 더 넓어져 방어하기 어렵다.
    - 클래스패스 안의 모든 타입의 객체를 만들어 낼 수 있기 때문에 자바 표준 라이브러리, 써드파티 라이브러리 등 코드 전체가 공격범위가 될 수 있음
- 가젯(garget)
    - 역직렬화 과정에서 호출되어 잠재적으로 위험한 동작을 수행하는 메서드
    - 신중하게 제작한 바이트스트림만 역직렬화 해야한다.
- 역직렬화 폭탄(deserialization bomb)
    - 역직렬화에 시간이 오래 걸리는 스트림을 역직렬화 할 때 서비스 거부 공격에 쉽게 노출 될 수 있다.

#### 크로스-플랫폼 구조화된 데이터 표현(cross-platform structured data representation)
- JSON
    - 최근에 가장 많이 사용하는 포맷으로 자바스크립트(ECMAScript)에서 쉽게 사용 가능하고, 다른 데이터 포맷 방식에 비해 오버헤드가 적기 때문에 때문에 인기가 많다.
    - 자바에서 Jackson, GSON 등의 라이브러리를 이용해서 변환할 수 있다.
- CSV
    - 데이터를 표현하는 가장 많이 사용되는 방법 중 하나로 콤마(,) 기준으로 데이터를 구분한다.
    - 자바에서는 Apache Commons CSV, opencsv 등의 라이브러리 등을 이용할 수 있다.
- 프로토콜 버퍼(Protocol Buffer)
    - 구글에서 제안한 플랫폼 독립적인 데이터 직렬화 플랫폼이다.
    - 프로토콜 버퍼는 특정 언어 또는 플랫폼에 종속되지 않는 방법을 구현하기 위해 직렬화 하기 위한 데이터를 표현하기 위한 문서가 따로 있다.

> “자바 직렬화 형태의 데이터 교환은 **자바 시스템 간**의 데이터 교환을 위해서 존재한다."

## Reference
- [자바 직렬화, 그것이 알고싶다. 훑어보기편](http://woowabros.github.io/experience/2017/10/17/java-serialize.html)
- [[JAVA] Serializable 과 transient](https://hyeonstorage.tistory.com/254)
- [직렬화](https://j.mearie.org/post/122845365013/serialization)


### 스터디 요약
-
---

> :leftwards_arrow_with_hook:[EffectiveJava3E](/EffectiveJava3E/README.md)

