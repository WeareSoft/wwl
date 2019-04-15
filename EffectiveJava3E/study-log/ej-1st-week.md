# [2019.04.14] Effective Java 3/E 스터디 진행
참석자: Delf, Doy, Hee, Nesoy
## 주제

- [**아이템 42.**](../chapter07/item42.md) 익명 클래스보다는 람다를 사용하라 (Hee)
- [**아이템 43.**](../chapter07/item43.md) 람다보다는 메서드 참조를 사용하라 (Doy)
- [**아이템 44.**](../chapter07/item44.md) 표준 함수형 인터페이스를 사용하라 (Delf, Nesoy)
- [**아이템 45.**](../chapter07/item45.md) 스트림은 주의해서 사용하라 (Hee, Nesoy)
- [**아이템 46.**](../chapter07/item46.md) 스트림에서는 부작용 없는 함수를 사용하라 (Hee, Nesoy)


## 아이템 분담
- Delf: 44, 46
- Doy: 43, 46
- Hee: 42, 45
- Nesoy: 44, 45

## 주요 이슈

#### item 42
- 람다와 `this` 키워드
- 람다 직렬화의 JVM 의존성

#### item 43
- 매서드 참조 유형의 종류와 용어
  - (비)한정적 인스턴스 메서드 참조, 함수 객체, 수신 객체 등

#### item 45
- 종단연산과 LazyEvaluation

#### item 46
- `Collectors.groupingBy()` 메서드의 downstream 매개변수의 용어와 개념
